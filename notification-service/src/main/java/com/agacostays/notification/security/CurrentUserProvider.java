package com.agacostays.notification.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public Long userId(){return numberClaim("userId");}
    public Long customerId(){return numberClaim("customerId");}
    public String role(){
        Authentication a=SecurityContextHolder.getContext().getAuthentication();
        return a==null?null:a.getAuthorities().stream().map(x->x.getAuthority()).findFirst().orElse(null);
    }
    private Long numberClaim(String key){
        Authentication a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null || !(a.getPrincipal() instanceof JwtAuthenticationFilter.JwtPrincipal p)) return null;
        var value=p.claims().get(key);
        if(value==null) return null;
        try{return Long.valueOf(String.valueOf(value));}catch(Exception e){return null;}
    }
}
