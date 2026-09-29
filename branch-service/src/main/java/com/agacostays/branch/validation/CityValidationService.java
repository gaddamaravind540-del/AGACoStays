package com.agacostays.branch.validation;
import org.springframework.stereotype.Component;
@Component public class CityValidationService{
 public String normalized(String s){return s==null?null:s.trim().replaceAll("\\s+"," ");}
}
