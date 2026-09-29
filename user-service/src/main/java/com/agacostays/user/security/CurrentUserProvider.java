package com.agacostays.user.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public Long userId(){
        Authentication a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null || a.getPrincipal() == null) throw new IllegalStateException("Authenticated identity missing");
        Object v=a.getPrincipal();
        if(v instanceof UserPrincipal p) return p.userId();
        return Long.valueOf(String.valueOf(v));
    }
    public String role(){
        Authentication a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null) return null;
        return a.getAuthorities().stream().findFirst().map(x->x.getAuthority().replace("ROLE_","")).orElse(null);
    }
    public record UserPrincipal(Long userId,String email,String role) {}
}
