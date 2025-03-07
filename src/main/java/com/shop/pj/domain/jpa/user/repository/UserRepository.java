package com.shop.pj.domain.jpa.user.repository;

import com.shop.pj.domain.jpa.user.entity.Role;
import com.shop.pj.domain.jpa.user.entity.Status;
import com.shop.pj.domain.jpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository {
    Optional<User> findByEmailAndStatus(String email, Status status);

    Optional<User> findByEmailAndRoleAndStatus(String email, Role role, Status status);

    Optional<User> findByUserIdAndStatus(Long userId, Status status);

}
