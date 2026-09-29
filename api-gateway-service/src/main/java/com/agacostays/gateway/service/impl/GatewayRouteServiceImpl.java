package com.agacostays.gateway.service.impl;

import com.agacostays.gateway.dto.response.RouteInfoResponse;
import com.agacostays.gateway.enums.GatewayRouteType;
import com.agacostays.gateway.service.GatewayRouteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayRouteServiceImpl implements GatewayRouteService {

    @Override
    public List<RouteInfoResponse> routes() {
        return List.of(
                new RouteInfoResponse("auth-service", "/api/auth/**", "AUTH", GatewayRouteType.PUBLIC, "PUBLIC", "Authentication endpoints"),
                new RouteInfoResponse("cities-route", "/api/cities/**", "BRANCH", GatewayRouteType.PUBLIC, "PUBLIC", "Public city APIs"),
                new RouteInfoResponse("hotel-branches-route", "/api/hotel-branches/**", "BRANCH", GatewayRouteType.AUTHENTICATED, "PUBLIC/AUTHENTICATED", "Branch and public catalog APIs"),
                new RouteInfoResponse("root-admin-route", "/api/root-admin/**", "MULTIPLE", GatewayRouteType.ROLE_PROTECTED, "ROOT_ADMIN", "Protected platform administration routes"),
                new RouteInfoResponse("manager-route", "/api/manager/**", "MULTIPLE", GatewayRouteType.ROLE_PROTECTED, "MANAGER/ROOT_ADMIN", "Protected manager routes"),
                new RouteInfoResponse("staff-route", "/api/staff/**", "MULTIPLE", GatewayRouteType.ROLE_PROTECTED, "AUTHORIZED_STAFF", "Protected staff routes"),
                new RouteInfoResponse("booking-route", "/api/bookings/**", "BOOKING", GatewayRouteType.ROLE_PROTECTED, "CUSTOMER/AUTHORIZED_STAFF", "Booking APIs"),
                new RouteInfoResponse("payment-route", "/api/payments/**", "PAYMENT", GatewayRouteType.ROLE_PROTECTED, "CUSTOMER/AUTHORIZED_STAFF", "Payment APIs"),
                new RouteInfoResponse("billing-route", "/api/billing/**", "BILLING", GatewayRouteType.ROLE_PROTECTED, "CUSTOMER/AUTHORIZED_STAFF", "Billing APIs"),
                new RouteInfoResponse("restaurant-route", "/api/restaurant/**", "RESTAURANT", GatewayRouteType.ROLE_PROTECTED, "CUSTOMER/RESTAURANT_STAFF", "Restaurant APIs"),
                new RouteInfoResponse("attendance-route", "/api/attendance/**", "ATTENDANCE", GatewayRouteType.ROLE_PROTECTED, "STAFF/MANAGER", "Attendance APIs"),
                new RouteInfoResponse("payroll-route", "/api/payroll/**", "PAYROLL", GatewayRouteType.ROLE_PROTECTED, "MANAGER/ROOT_ADMIN/STAFF", "Payroll APIs"),
                new RouteInfoResponse("analytics-route", "/api/analytics/**", "ANALYTICS", GatewayRouteType.ROLE_PROTECTED, "MANAGER/ROOT_ADMIN", "Analytics APIs"),
                new RouteInfoResponse("support-route", "/api/support/**", "SUPPORT_FEEDBACK", GatewayRouteType.ROLE_PROTECTED, "CUSTOMER/AUTHORIZED_STAFF", "Support APIs")
        );
    }
}
