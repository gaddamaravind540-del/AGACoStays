package com.agacostays.user.service;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*; public interface RootAdminService {
 ManagerResponse createManager(CreateManagerRequest r,Long actor);
 PageResponse<ManagerResponse> listManagers(int page,int size);
 ManagerResponse getManager(Long id);
 ManagerResponse updateManager(Long id,UpdateManagerRequest r,Long actor);
 UserStatusResponse updateManagerStatus(Long id,boolean active,Long actor);
 void deleteManager(Long id,Long actor);
}