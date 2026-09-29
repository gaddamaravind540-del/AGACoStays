package com.agacostays.restaurant.validation;
import org.springframework.stereotype.Service;
@Service
public class RestaurantValidationService {
    public void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5");
    }
}
