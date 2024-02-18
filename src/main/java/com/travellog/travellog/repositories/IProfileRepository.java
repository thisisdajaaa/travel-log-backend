package com.travellog.travellog.repositories;

import com.travellog.travellog.models.Profile;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUserId(Integer id);

    void deleteById(@NotNull Integer id);
}
