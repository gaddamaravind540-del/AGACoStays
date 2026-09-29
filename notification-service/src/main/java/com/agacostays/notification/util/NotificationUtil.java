package com.agacostays.notification.util;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class NotificationUtil {
    public String render(String template, Map<String,Object> variables){
        if(template==null || variables==null) return template;
        var result=template;
        for(var e:variables.entrySet()) result=result.replace("{{"+e.getKey()+"}}",String.valueOf(e.getValue()));
        return result;
    }
}
