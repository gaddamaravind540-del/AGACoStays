package com.agacostays.user.repository;
import com.agacostays.user.entity.ManagerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ManagerProfileRepository extends JpaRepository<ManagerProfile, Long> {
    Optional<ManagerProfile> findByUser_UserId(Long userId);
}
