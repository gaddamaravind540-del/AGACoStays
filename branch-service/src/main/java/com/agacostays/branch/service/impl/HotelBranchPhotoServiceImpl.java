package com.agacostays.branch.service.impl;
import com.agacostays.branch.constants.BranchConstants; import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*;
import com.agacostays.branch.entity.*; import com.agacostays.branch.exception.*; import com.agacostays.branch.mapper.HotelBranchPhotoMapper;
import com.agacostays.branch.repository.*; import com.agacostays.branch.service.HotelBranchPhotoService; import com.agacostays.branch.storage.StorageService;
import com.agacostays.branch.security.FileUploadSecurityValidator;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.multipart.MultipartFile;
@Service public class HotelBranchPhotoServiceImpl implements HotelBranchPhotoService{
 private final HotelBranchRepository branches; private final HotelBranchPhotoRepository repo; private final StorageService storage; private final HotelBranchPhotoMapper mapper;
 private final FileUploadSecurityValidator fileValidator;
 public HotelBranchPhotoServiceImpl(HotelBranchRepository b,HotelBranchPhotoRepository r,StorageService s,HotelBranchPhotoMapper m,FileUploadSecurityValidator fv){branches=b;repo=r;storage=s;mapper=m;fileValidator=fv;}
 @Transactional public HotelBranchPhotoResponse upload(Long branchId,HotelBranchPhotoRequest r,MultipartFile file,Long actor){
  HotelBranch b=branches.findById(branchId).orElseThrow(()->new BranchNotFoundException("Branch not found: "+branchId));
  fileValidator.validate(file);
  String url=storage.store(BranchConstants.FILE_FOLDER,file);
  if(r.primaryPhoto()) repo.findByBranch_BranchIdOrderByPrimaryPhotoDescCreatedAtAsc(branchId).forEach(p->{p.setPrimaryPhoto(false);repo.save(p);});
  HotelBranchPhoto p=repo.save(HotelBranchPhoto.builder().branch(b).photoUrl(url).caption(r.caption()).photoType(r.photoType()).primaryPhoto(r.primaryPhoto()).uploadedBy(actor).build());
  return mapper.toResponse(p);
 }
 @Transactional(readOnly=true) public java.util.List<HotelBranchPhotoResponse> list(Long id){
  if(!branches.existsById(id))throw new BranchNotFoundException("Branch not found: "+id);
  return repo.findByBranch_BranchIdOrderByPrimaryPhotoDescCreatedAtAsc(id).stream().map(mapper::toResponse).toList();
 }
 @Transactional public void delete(Long branchId,Long photoId,Long actor){
  HotelBranchPhoto p=repo.findById(photoId).orElseThrow(()->new BranchPhotoNotFoundException("Branch photo not found: "+photoId));
  if(!p.getBranch().getBranchId().equals(branchId))throw new BranchPhotoException("Photo does not belong to branch");
  repo.delete(p);
 }
}
