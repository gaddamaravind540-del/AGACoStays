package com.agacostays.user.service.impl;
import com.agacostays.user.dto.request.UpdateProfileRequest; import com.agacostays.user.dto.response.UserProfileResponse;
import com.agacostays.user.entity.User; import com.agacostays.user.exception.UserNotFoundException;
import com.agacostays.user.repository.*; import com.agacostays.user.service.ProfileService;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service
public class ProfileServiceImpl implements ProfileService {
 private final UserRepository users; private final CustomerRepository customers;
 public ProfileServiceImpl(UserRepository u,CustomerRepository c){users=u;customers=c;}
 @Transactional(readOnly=true) public UserProfileResponse getMe(Long userId){
  User u=users.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
  String address=customers.findByUser_UserId(userId).map(x->x.getAddress()).orElse(null);
  return new UserProfileResponse(u.getUserId(),u.getFullName(),u.getEmail(),u.getPhone(),u.getRole().getRoleName(),u.getUserType().name(),u.getStatus().name(),address);
 }
 @Transactional public UserProfileResponse updateMe(Long userId,UpdateProfileRequest r){
  User u=users.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
  u.setFullName(r.fullName());u.setPhone(r.phone());users.save(u);
  var customer=customers.findByUser_UserId(userId);
  customer.ifPresent(c->{c.setAddress(r.address());customers.save(c);});
  return getMe(userId);
 }
}
