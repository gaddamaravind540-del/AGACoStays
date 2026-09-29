package com.agacostays.user.repository;
import com.agacostays.user.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    boolean existsByRole_RoleIdAndPermission_PermissionId(Long roleId, Long permissionId);
    List<RolePermission> findByRole_RoleId(Long roleId);
}
