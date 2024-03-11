package com.travellog.travellog.repositories;

import com.travellog.travellog.models.TravelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITravelPhotoRepository extends JpaRepository<TravelPhoto, Integer> {
}
