package com.agacostays.user.repository;
import com.agacostays.user.entity.User;
import com.agacostays.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    long countByStatus(UserStatus status);
}
