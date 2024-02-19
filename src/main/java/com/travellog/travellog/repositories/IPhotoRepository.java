package com.travellog.travellog.repositories;

import com.travellog.travellog.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhotoRepository extends JpaRepository<Photo, Integer> {
}
