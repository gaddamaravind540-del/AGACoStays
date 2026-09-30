package com.agacostays.gateway.security;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record JwtClaims(
        String userId,
        String username,
        List<String> roles,
        String branchId
) {

    public static JwtClaims fromJwt(Jwt jwt) {

        String userId = firstNonBlank(
                jwt.getClaimAsString("userId"),
                jwt.getSubject()
        );

        String username = firstNonBlank(
                jwt.getClaimAsString("username"),
                jwt.getClaimAsString("email"),
                jwt.getSubject()
        );

        List<String> roles = new ArrayList<>();

        Object rolesClaim = jwt.getClaims().get("roles");

        if (rolesClaim instanceof Collection<?> collection) {
            collection.forEach(
                    value -> roles.add(
                            String.valueOf(value).toUpperCase()
                    )
            );
        } else if (rolesClaim != null) {
            roles.add(
                    String.valueOf(rolesClaim).toUpperCase()
            );
        }

        String singleRole = jwt.getClaimAsString("role");

        if (singleRole != null && !singleRole.isBlank()) {
            roles.add(singleRole.toUpperCase());
        }

        List<String> uniqueRoles = roles.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        return new JwtClaims(
                userId,
                username,
                uniqueRoles,
                jwt.getClaimAsString("branchId")
        );
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
