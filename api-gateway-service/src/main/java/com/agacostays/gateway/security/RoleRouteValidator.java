package com.agacostays.gateway.security;

import com.agacostays.gateway.constants.RoleConstants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RoleRouteValidator {

    private static final Set<String> STAFF_ROLES = Set.of(
            RoleConstants.RECEPTIONIST,
            RoleConstants.RESTAURANT_ADMIN,
            RoleConstants.CHEF,
            RoleConstants.SERVING_STAFF,
            RoleConstants.HOUSEKEEPING_STAFF,
            RoleConstants.MANAGER,
            RoleConstants.ROOT_ADMIN
    );

    public boolean isAllowed(String path, List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }

        Set<String> roleSet = roles.stream().map(String::toUpperCase).collect(java.util.stream.Collectors.toSet());

        if (path.startsWith("/api/root-admin/")) {
            return roleSet.contains(RoleConstants.ROOT_ADMIN);
        }
        if (path.startsWith("/api/manager/")) {
            return roleSet.contains(RoleConstants.MANAGER)
                    || roleSet.contains(RoleConstants.ROOT_ADMIN);
        }
        if (path.startsWith("/api/staff/")) {
            return roleSet.stream().anyMatch(STAFF_ROLES::contains);
        }
        if (path.startsWith("/api/bookings/")) {
            return roleSet.contains(RoleConstants.CUSTOMER) || roleSet.stream().anyMatch(STAFF_ROLES::contains);
        }
        if (path.startsWith("/api/payments/")) {
            return roleSet.contains(RoleConstants.CUSTOMER) || roleSet.stream().anyMatch(STAFF_ROLES::contains);
        }
        if (path.startsWith("/api/billing/")) {
            return roleSet.contains(RoleConstants.CUSTOMER) || roleSet.stream().anyMatch(STAFF_ROLES::contains);
        }
        if (path.startsWith("/api/restaurant/")) {
            return roleSet.contains(RoleConstants.CUSTOMER)
                    || roleSet.contains(RoleConstants.RESTAURANT_ADMIN)
                    || roleSet.contains(RoleConstants.CHEF)
                    || roleSet.contains(RoleConstants.SERVING_STAFF)
                    || roleSet.contains(RoleConstants.RECEPTIONIST)
                    || roleSet.contains(RoleConstants.MANAGER)
                    || roleSet.contains(RoleConstants.ROOT_ADMIN);
        }
        if (path.startsWith("/api/attendance/")) {
            return roleSet.contains(RoleConstants.RECEPTIONIST)
                    || roleSet.contains(RoleConstants.RESTAURANT_ADMIN)
                    || roleSet.contains(RoleConstants.CHEF)
                    || roleSet.contains(RoleConstants.SERVING_STAFF)
                    || roleSet.contains(RoleConstants.HOUSEKEEPING_STAFF)
                    || roleSet.contains(RoleConstants.MANAGER);
        }
        if (path.startsWith("/api/payroll/")) {
            return roleSet.contains(RoleConstants.MANAGER)
                    || roleSet.contains(RoleConstants.ROOT_ADMIN)
                    || roleSet.contains(RoleConstants.RECEPTIONIST)
                    || roleSet.contains(RoleConstants.RESTAURANT_ADMIN)
                    || roleSet.contains(RoleConstants.CHEF)
                    || roleSet.contains(RoleConstants.SERVING_STAFF)
                    || roleSet.contains(RoleConstants.HOUSEKEEPING_STAFF);
        }
        if (path.startsWith("/api/analytics/")) {
            return roleSet.contains(RoleConstants.MANAGER)
                    || roleSet.contains(RoleConstants.ROOT_ADMIN)
                    || roleSet.contains(RoleConstants.RESTAURANT_ADMIN);
        }
        if (path.startsWith("/api/support/")) {
            return roleSet.contains(RoleConstants.CUSTOMER) || roleSet.stream().anyMatch(STAFF_ROLES::contains);
        }

        return true;
    }

    public String requiredRoles(String path) {
        if (path.startsWith("/api/root-admin/")) return "ROOT_ADMIN";
        if (path.startsWith("/api/manager/")) return "MANAGER,ROOT_ADMIN";
        if (path.startsWith("/api/staff/")) return "AUTHORIZED_STAFF";
        if (path.startsWith("/api/bookings/")) return "CUSTOMER,AUTHORIZED_STAFF";
        if (path.startsWith("/api/payments/")) return "CUSTOMER,AUTHORIZED_STAFF";
        if (path.startsWith("/api/billing/")) return "CUSTOMER,AUTHORIZED_STAFF";
        if (path.startsWith("/api/restaurant/")) return "CUSTOMER,RESTAURANT_STAFF";
        if (path.startsWith("/api/attendance/")) return "STAFF,MANAGER";
        if (path.startsWith("/api/payroll/")) return "MANAGER,ROOT_ADMIN,STAFF";
        if (path.startsWith("/api/analytics/")) return "MANAGER,ROOT_ADMIN,RESTAURANT_ADMIN";
        if (path.startsWith("/api/support/")) return "CUSTOMER,AUTHORIZED_STAFF";
        return "PUBLIC";
    }
}
