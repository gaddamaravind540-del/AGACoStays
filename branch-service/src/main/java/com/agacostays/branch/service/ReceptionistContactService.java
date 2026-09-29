package com.agacostays.branch.service;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*; import java.util.*;
public interface ReceptionistContactService{
 ReceptionistContactResponse add(Long branchId,ReceptionistContactRequest r,Long actor);
 List<ReceptionistContactResponse> list(Long branchId);
}
