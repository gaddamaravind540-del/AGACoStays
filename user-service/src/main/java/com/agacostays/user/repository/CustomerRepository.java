package com.agacostays.user.repository;
import com.agacostays.user.entity.Customer;
import com.agacostays.user.enums.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser_UserId(Long userId);
    long countByStatus(CustomerStatus status);
}
