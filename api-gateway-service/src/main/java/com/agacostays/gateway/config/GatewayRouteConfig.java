package com.agacostays.gateway.config;

import com.agacostays.gateway.constants.RouteConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRouteLocator(
            RouteLocatorBuilder builder,
            @Value("${gateway.services.auth-url}") String authUrl,
            @Value("${gateway.services.user-url}") String userUrl,
            @Value("${gateway.services.branch-url}") String branchUrl,
            @Value("${gateway.services.room-url}") String roomUrl,
            @Value("${gateway.services.booking-url}") String bookingUrl,
            @Value("${gateway.services.restaurant-url}") String restaurantUrl,
            @Value("${gateway.services.payment-url}") String paymentUrl,
            @Value("${gateway.services.billing-url}") String billingUrl,
            @Value("${gateway.services.notification-url}") String notificationUrl,
            @Value("${gateway.services.attendance-url}") String attendanceUrl,
            @Value("${gateway.services.payroll-url}") String payrollUrl,
            @Value("${gateway.services.analytics-url}") String analyticsUrl,
            @Value("${gateway.services.support-url}") String supportUrl) {

        return builder.routes()
                // Auth and public property
                .route(RouteConstants.AUTH, r -> r.path("/api/auth/**").uri(authUrl))
                .route(RouteConstants.CITIES, r -> r.path("/api/cities", "/api/cities/**").uri(branchUrl))
                .route(RouteConstants.HOTEL_BRANCHES, r -> r.path("/api/hotel-branches", "/api/hotel-branches/**").uri(branchUrl))

                // More specific manager/root-admin routes first so domain routes win over generic families.
                .route(RouteConstants.ROOT_ADMIN_ANALYTICS,
                        r -> r.path("/api/root-admin/analytics/**").uri(analyticsUrl))
                .route(RouteConstants.ROOT_ADMIN_BRANCH,
                        r -> r.path("/api/root-admin/cities/**",
                                   "/api/root-admin/hotel-branches/**").uri(branchUrl))

                .route(RouteConstants.MANAGER_ANALYTICS,
                        r -> r.path("/api/manager/analytics/**").uri(analyticsUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-hotel",
                        r -> r.path("/api/manager/hotel-branches/*/analytics/**").uri(analyticsUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-payment",
                        r -> r.path("/api/manager/hotel-branches/*/payments/**").uri(paymentUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-billing",
                        r -> r.path("/api/manager/hotel-branches/*/bills/**").uri(billingUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-payroll",
                        r -> r.path("/api/manager/hotel-branches/*/payroll-report/**").uri(payrollUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-booking",
                        r -> r.path("/api/manager/hotel-branches/*/bookings/**").uri(bookingUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-restaurant",
                        r -> r.path("/api/manager/hotel-branches/*/restaurant/**").uri(restaurantUrl))
                .route(RouteConstants.MANAGER_ANALYTICS + "-branch",
                        r -> r.path("/api/manager/cities/**",
                                   "/api/manager/hotel-branches/**").uri(branchUrl))

                // Staff account/ops endpoints can be owned by user/branch/staff services.
                .route(RouteConstants.USER, r -> r.path("/api/users/**", "/api/customers/**").uri(userUrl))
                .route(RouteConstants.STAFF, r -> r.path("/api/staff/**").uri(userUrl))

                // Core domains
                .route(RouteConstants.BOOKING, r -> r.path("/api/bookings/**").uri(bookingUrl))
                .route(RouteConstants.BOOKING + "-manager",
                        r -> r.path("/api/hotel-branches/*/bookings/**").uri(bookingUrl))

                // These notification paths must be declared before the broad /api/restaurant/** route.
                .route(RouteConstants.NOTIFICATION + "-restaurant",
                        r -> r.path("/api/restaurant/notifications/**").uri(notificationUrl))
                .route(RouteConstants.NOTIFICATION + "-payroll",
                        r -> r.path("/api/manager/payroll/notifications/**").uri(notificationUrl))

                .route(RouteConstants.PAYMENT, r -> r.path("/api/payments/**",
                                                             "/api/refunds/**",
                                                             "/api/restaurant/payments/**",
                                                             "/api/billing/payments/**").uri(paymentUrl))

                .route(RouteConstants.BILLING, r -> r.path("/api/billing/**",
                                                             "/api/invoices/**").uri(billingUrl))

                .route(RouteConstants.RESTAURANT, r -> r.path("/api/restaurant/**").uri(restaurantUrl))

                .route(RouteConstants.ATTENDANCE, r -> r.path("/api/attendance/**").uri(attendanceUrl))
                .route(RouteConstants.PAYROLL, r -> r.path("/api/payroll/**").uri(payrollUrl))

                .route(RouteConstants.ANALYTICS_API, r -> r.path("/api/analytics/**").uri(analyticsUrl))
                // Notification endpoints are invoked by trusted services and notification UI.
                .route(RouteConstants.NOTIFICATION, r -> r.path("/api/notifications/**").uri(notificationUrl))

                .route(RouteConstants.SUPPORT, r -> r.path("/api/support/**").uri(supportUrl))

                // Catch-all manager/root-admin routes are intentionally last. They should only
                // be used for endpoints that belong to the configured default service.
                .route("root-admin-default",
                        r -> r.path("/api/root-admin/**").uri(userUrl))
                .route("manager-default",
                        r -> r.path("/api/manager/**").uri(userUrl))

                .build();
    }
}
