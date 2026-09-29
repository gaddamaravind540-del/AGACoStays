package com.agacostays.support.service;
import com.agacostays.support.dto.request.FeedbackRequest;
import com.agacostays.support.dto.response.FeedbackResponse;
import java.util.List;

public interface FeedbackService {
    FeedbackResponse create(FeedbackRequest request);
    List<FeedbackResponse> myFeedback();
    List<FeedbackResponse> byBranch(Long branchId);
    FeedbackResponse update(Long feedbackId, FeedbackRequest request);
    void delete(Long feedbackId);
}
