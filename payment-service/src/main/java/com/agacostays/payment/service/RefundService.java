package com.agacostays.payment.service; import com.agacostays.payment.dto.request.RefundRequest; import com.agacostays.payment.dto.response.*; import java.util.*;
public interface RefundService { RefundResponse refund(Long paymentId,RefundRequest request); List<RefundResponse> refunds(Long paymentId); RefundResponse getRefund(Long refundId); }
