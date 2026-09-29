package com.agacostays.user.repository;
import com.agacostays.user.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUser_UserId(Long userId);
}
