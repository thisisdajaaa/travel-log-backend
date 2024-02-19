package com.travellog.travellog.repositories;

import com.travellog.travellog.models.TravelLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITravelLogRepository extends JpaRepository<TravelLog, Integer> {
    List<TravelLog> findByUserId(Integer userId);
}
