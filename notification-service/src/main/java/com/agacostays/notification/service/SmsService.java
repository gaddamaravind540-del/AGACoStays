package com.agacostays.notification.service;

import com.agacostays.notification.dto.request.SendSmsRequest;
import com.agacostays.notification.dto.response.SmsResponse;

public interface SmsService { SmsResponse send(SendSmsRequest request); }
