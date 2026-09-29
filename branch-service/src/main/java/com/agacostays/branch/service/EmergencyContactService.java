package com.agacostays.branch.service;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*; import java.util.*;
public interface EmergencyContactService{
 EmergencyContactResponse add(Long branchId,EmergencyContactRequest r,Long actor);
 List<EmergencyContactResponse> list(Long branchId);
}
