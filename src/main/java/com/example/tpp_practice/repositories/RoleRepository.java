package com.example.tpp_practice.repositories;

import com.example.tpp_practice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
