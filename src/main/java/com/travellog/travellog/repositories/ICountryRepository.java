package com.travellog.travellog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travellog.travellog.models.Country;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Integer> {
}
