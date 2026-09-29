package com.agacostays.support.validation;

import com.agacostays.support.exception.BusinessRuleException;
import org.springframework.stereotype.Service;

@Service
public class SupportFeedbackValidationService {
    public void rating(Integer rating, String field) {
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new BusinessRuleException(field + " must be between 1 and 5");
        }
    }
}
