package com.agacostays.branch.service.impl;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*;
import com.agacostays.branch.entity.*; import com.agacostays.branch.enums.BranchStatus;
import com.agacostays.branch.exception.*; import com.agacostays.branch.mapper.*; import com.agacostays.branch.repository.*;
import com.agacostays.branch.service.*; import com.agacostays.branch.validation.BranchValidationService;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class HotelBranchServiceImpl implements HotelBranchService{
 private final HotelBranchRepository repo; private final CityRepository cities; private final HotelBranchMapper mapper;
 private final HotelBranchPhotoRepository photos; private final ReceptionistContactRepository receptions; private final EmergencyContactRepository emergencies;
 private final HotelBranchPhotoMapper photoMapper; private final ReceptionistContactMapper receptionistMapper; private final EmergencyContactMapper emergencyMapper;
 private final BranchValidationService validator;
 public HotelBranchServiceImpl(HotelBranchRepository r,CityRepository c,HotelBranchMapper m,HotelBranchPhotoRepository p,
  ReceptionistContactRepository rc,EmergencyContactRepository ec,HotelBranchPhotoMapper pm,ReceptionistContactMapper rm,EmergencyContactMapper em,BranchValidationService v){
  repo=r;cities=c;mapper=m;photos=p;receptions=rc;emergencies=ec;photoMapper=pm;receptionistMapper=rm;emergencyMapper=em;validator=v;
 }
 @Transactional public HotelBranchResponse create(HotelBranchRequest r,Long actor){
  if(repo.findByBranchNameIgnoreCase(r.branchName().trim()).isPresent())throw new DuplicateBranchException("Branch already exists");
  validator.validateLocation(r.latitude(),r.longitude());
  City city=cities.findById(r.cityId()).orElseThrow(()->new CityNotFoundException("City not found: "+r.cityId()));
  HotelBranch b=repo.save(HotelBranch.builder().city(city).branchName(r.branchName().trim()).address(r.address().trim()).landmark(r.landmark())
    .latitude(r.latitude()).longitude(r.longitude()).phone(r.phone()).email(r.email()).description(r.description()).status(BranchStatus.ACTIVE).createdBy(actor).build());
  return mapper.toResponse(b);
 }
 @Transactional(readOnly=true) public List<HotelBranchSummaryResponse> activeBranches(){return repo.findByStatusOrderByBranchNameAsc(BranchStatus.ACTIVE).stream().map(mapper::toSummary).toList();}
 @Transactional(readOnly=true) public HotelBranchResponse get(Long id){return mapper.toResponse(find(id));}
 @Transactional(readOnly=true) public List<HotelBranchSummaryResponse> byCity(Long cityId){
  if(!cities.existsById(cityId))throw new CityNotFoundException("City not found: "+cityId);
  return repo.findByCity_CityIdAndStatusOrderByBranchNameAsc(cityId,BranchStatus.ACTIVE).stream().map(mapper::toSummary).toList();
 }
 @Transactional public HotelBranchResponse update(Long id,UpdateHotelBranchRequest r,Long actor){
  HotelBranch b=find(id);validator.validateLocation(r.latitude(),r.longitude());
  City c=cities.findById(r.cityId()).orElseThrow(()->new CityNotFoundException("City not found: "+r.cityId()));
  b.setCity(c);b.setBranchName(r.branchName().trim());b.setAddress(r.address().trim());b.setLandmark(r.landmark());b.setLatitude(r.latitude());b.setLongitude(r.longitude());
  b.setPhone(r.phone());b.setEmail(r.email());b.setDescription(r.description());return mapper.toResponse(repo.save(b));
 }
 @Transactional public HotelBranchResponse status(Long id,UpdateBranchStatusRequest r,Long actor){HotelBranch b=find(id);b.setStatus(r.status());return mapper.toResponse(repo.save(b));}
 @Transactional public void deactivate(Long id,Long actor){HotelBranch b=find(id);b.setStatus(BranchStatus.INACTIVE);repo.save(b);}
 @Transactional(readOnly=true) public BranchPublicResponse publicDetails(Long id){
  HotelBranch b=find(id);
  return new BranchPublicResponse(mapper.toResponse(b),
    photos.findByBranch_BranchIdOrderByPrimaryPhotoDescCreatedAtAsc(id).stream().map(photoMapper::toResponse).toList(),
    receptions.findByBranch_BranchIdOrderByShiftAsc(id).stream().map(receptionistMapper::toResponse).toList(),
    emergencies.findByBranch_BranchIdOrderByPurposeAsc(id).stream().map(emergencyMapper::toResponse).toList());
 }
 @Transactional(readOnly=true) public List<HotelBranchSummaryResponse> rootAdminAll(){return repo.findAll().stream().map(mapper::toSummary).toList();}
 private HotelBranch find(Long id){return repo.findById(id).orElseThrow(()->new BranchNotFoundException("Branch not found: "+id));}
}
