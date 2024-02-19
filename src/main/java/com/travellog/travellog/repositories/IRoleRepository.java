package com.travellog.travellog.repositories;

import com.travellog.travellog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
