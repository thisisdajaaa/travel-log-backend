package com.travellog.travellog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travellog.travellog.models.Profile;

import lombok.NonNull;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{
    Optional<Profile> findByUserId(Integer id);
    void deleteById(@NonNull Integer id);
}
