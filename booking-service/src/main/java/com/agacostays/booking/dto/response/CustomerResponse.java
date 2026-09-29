package com.agacostays.booking.dto.response;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CustomerResponse {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
}
