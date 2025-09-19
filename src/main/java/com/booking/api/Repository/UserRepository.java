package com.booking.api.Repository;

import com.booking.api.entity.User;
import com.booking.api.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(Role role);
    boolean existsByUsername(String username);
}