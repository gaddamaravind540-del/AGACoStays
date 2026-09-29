package com.agacostays.payment.security;

import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
 public long userId(){return parseLong(detail("userId","id",null), subject());}
 public long customerId(){return parseLong(detail("customerId","userId",null), subject());}
 public Long branchId(){String v=detail("branchId", "branch", ""); return v==null||v.isBlank()?null:parseLong(v, null);}
 public String role(){Authentication a=auth(); return a==null?"":a.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("").replace("ROLE_","");}
 public boolean hasRole(String r){return auth()!=null && auth().getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_"+r) || a.getAuthority().equals(r));}
 private Authentication auth(){return SecurityContextHolder.getContext().getAuthentication();}
 private String subject(){Authentication a=auth(); return a==null?null:a.getName();}
 private String detail(String a,String b,String d){Authentication x=auth(); if(x==null||x.getDetails()==null)return d; if(x.getDetails() instanceof java.util.Map<?,?> m){Object v=m.get(a); if(v==null)v=m.get(b); return v==null?d:String.valueOf(v);} return d;}
 private long parseLong(String v, String fallback){try{return Long.parseLong(v);}catch(Exception e){return Long.parseLong(fallback==null?"0":fallback);}}
}
