package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.travelLog.CreateTravelLogDto;
import com.travellog.travellog.dtos.travelLog.TravelLogDetailDto;
import com.travellog.travellog.dtos.travelLog.UpdateTravelLogDto;

import java.util.List;

public interface ITravelLogService {
    TravelLogDetailDto createTravelLog(CreateTravelLogDto createTravelLogDto);

    List<TravelLogDetailDto> getTravelLogsByUserId(Integer userId);

    TravelLogDetailDto getTravelLogById(Integer id);

    boolean deleteTravelLog(Integer id);

    TravelLogDetailDto updateTravelLog(Integer travelLogId, UpdateTravelLogDto updateTravelLogDto);
}
