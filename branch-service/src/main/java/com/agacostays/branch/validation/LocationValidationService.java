package com.agacostays.branch.validation;
import org.springframework.stereotype.Component;
@Component public class LocationValidationService { public void validate(String city,String state,String country) {
 if(city==null||city.isBlank()||state==null||state.isBlank()||country==null||country.isBlank()) throw new IllegalArgumentException("Location is incomplete");
}}
