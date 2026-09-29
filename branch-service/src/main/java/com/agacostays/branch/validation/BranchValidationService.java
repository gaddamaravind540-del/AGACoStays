package com.agacostays.branch.validation;
import com.agacostays.branch.exception.InvalidLocationException;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
@Component public class BranchValidationService{
 public void validateLocation(BigDecimal lat,BigDecimal lon){
  if(lat==null||lon==null)return;
  if(lat.doubleValue()<-90||lat.doubleValue()>90||lon.doubleValue()<-180||lon.doubleValue()>180)
   throw new InvalidLocationException("Latitude or longitude is invalid");
 }
}
