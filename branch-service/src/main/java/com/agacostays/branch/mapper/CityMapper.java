package com.agacostays.branch.mapper;
import com.agacostays.branch.dto.response.CityResponse;
import com.agacostays.branch.entity.City;
import org.springframework.stereotype.Component;
@Component public class CityMapper {
 public CityResponse toResponse(City c){return new CityResponse(c.getCityId(),c.getCityName(),c.getState(),c.getCountry(),c.getStatus().name());}
}
