package com.javaproject.javaprojectthree.repository;

import com.javaproject.javaprojectthree.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
