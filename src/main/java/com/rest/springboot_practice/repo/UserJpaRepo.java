package com.rest.springboot_practice.repo;

import com.rest.springboot_practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {
}
