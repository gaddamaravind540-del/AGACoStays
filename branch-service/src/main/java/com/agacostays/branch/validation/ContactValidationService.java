package com.agacostays.branch.validation;
import org.springframework.stereotype.Component;
@Component public class ContactValidationService { public void validatePhone(String phone){ if(phone==null||phone.isBlank()) throw new IllegalArgumentException("Phone is required"); } }
