package com.agacostays.notification.validation;

import com.agacostays.notification.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

@Component
public class NotificationValidationService {
    public void validateEmail(String to){ if(to==null || !to.contains("@")) throw new BusinessRuleException("A valid email address is required"); }
    public void validatePhone(String phone){ if(phone==null || phone.isBlank()) throw new BusinessRuleException("A phone number is required"); }
}
