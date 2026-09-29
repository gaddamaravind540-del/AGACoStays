package com.agacostays.user.repository;
import com.agacostays.user.entity.RootAdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RootAdminProfileRepository extends JpaRepository<RootAdminProfile, Long> {
    Optional<RootAdminProfile> findByUser_UserId(Long userId);
}
