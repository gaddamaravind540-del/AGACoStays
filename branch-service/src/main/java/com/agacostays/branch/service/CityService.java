package com.agacostays.branch.service;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*; import java.util.*;
public interface CityService{
 CityResponse create(CreateCityRequest r,Long actor);
 List<CityResponse> activeCities();
 CityResponse get(Long id);
 CityResponse update(Long id,UpdateCityRequest r,Long actor);
 void deactivate(Long id,Long actor);
}
