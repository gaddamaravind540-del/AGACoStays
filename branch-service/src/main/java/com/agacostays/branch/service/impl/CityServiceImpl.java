package com.agacostays.branch.service.impl;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*;
import com.agacostays.branch.entity.City; import com.agacostays.branch.enums.CityStatus;
import com.agacostays.branch.exception.*; import com.agacostays.branch.mapper.CityMapper; import com.agacostays.branch.repository.CityRepository;
import com.agacostays.branch.service.CityService; import com.agacostays.branch.validation.CityValidationService;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class CityServiceImpl implements CityService{
 private final CityRepository repo; private final CityMapper mapper; private final CityValidationService validator;
 public CityServiceImpl(CityRepository r,CityMapper m,CityValidationService v){repo=r;mapper=m;validator=v;}
 @Transactional public CityResponse create(CreateCityRequest r,Long actor){
  String name=validator.normalized(r.cityName());
  if(repo.findByCityNameIgnoreCase(name).isPresent())throw new DuplicateCityException("City already exists");
  City c=repo.save(City.builder().cityName(name).state(r.state().trim()).country(r.country().trim()).status(CityStatus.ACTIVE).build());
  return mapper.toResponse(c);
 }
 @Transactional(readOnly=true) public List<CityResponse> activeCities(){return repo.findByStatusOrderByCityNameAsc(CityStatus.ACTIVE).stream().map(mapper::toResponse).toList();}
 @Transactional(readOnly=true) public CityResponse get(Long id){return mapper.toResponse(repo.findById(id).orElseThrow(()->new CityNotFoundException("City not found: "+id)));}
 @Transactional public CityResponse update(Long id,UpdateCityRequest r,Long actor){
  City c=repo.findById(id).orElseThrow(()->new CityNotFoundException("City not found: "+id));
  c.setCityName(validator.normalized(r.cityName()));c.setState(r.state().trim());c.setCountry(r.country().trim());c.setStatus(r.status());
  return mapper.toResponse(repo.save(c));
 }
 @Transactional public void deactivate(Long id,Long actor){City c=repo.findById(id).orElseThrow(()->new CityNotFoundException("City not found: "+id));c.setStatus(CityStatus.INACTIVE);repo.save(c);}
}
