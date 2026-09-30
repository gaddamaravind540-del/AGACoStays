package com.agacostays.analytics.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AttendanceServiceClient {
	private final RestClient client;

	public AttendanceServiceClient(@Value("${service.attendance.url}") String baseUrl, RestClient.Builder b) {
		client = b.baseUrl(baseUrl).build();
	}

	public String healthReference(Long id) {
		try {
			return client.get().uri("/api/attendance/staff/" + id).retrieve().toString();
		} catch (Exception e) {
			return null;
		}
	}
}
