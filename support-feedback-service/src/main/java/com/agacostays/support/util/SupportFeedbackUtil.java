package com.agacostays.support.util;
public final class SupportFeedbackUtil {
    private SupportFeedbackUtil(){}
    public static boolean validRating(Integer rating){ return rating!=null && rating>=1 && rating<=5; }
}
