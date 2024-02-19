package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.travelLog.CreateTravelLogDto;
import com.travellog.travellog.dtos.travelLog.TravelLogDetailDto;
import com.travellog.travellog.dtos.travelLog.UpdateTravelLogDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.models.User;
import com.travellog.travellog.services.spec.ICustomUserDetailsService;
import com.travellog.travellog.services.spec.ITravelLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-logs")
public class TravelLogController {
    private final ITravelLogService travelLogService;
    private final ICustomUserDetailsService customUserDetailsService;

    public TravelLogController(ITravelLogService travelLogService, ICustomUserDetailsService customUserDetailsService) {
        this.travelLogService = travelLogService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<TravelLogDetailDto>> createTravelLog(
            @Valid @RequestBody CreateTravelLogDto createTravelLogDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created travel log!",
                        travelLogService.createTravelLog(createTravelLogDto)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<TravelLogDetailDto>>> getAllTravelLogs() {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved all travel logs!",
                        travelLogService.getTravelLogsByUserId(null)),
                HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<ResponseHelper.CustomResponse<List<TravelLogDetailDto>>> getCurrentUserTravelLogs() {
        User currentUser = customUserDetailsService.getAuthenticatedUser();

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved current user travel logs!",
                        travelLogService.getTravelLogsByUserId(currentUser.getId())),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseHelper.CustomResponse<List<TravelLogDetailDto>>> getCurrentUserTravelLogs(
            @PathVariable Integer userId) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved user travel logs!",
                        travelLogService.getTravelLogsByUserId(userId)),
                HttpStatus.OK);
    }

    @GetMapping("/id/{travelLogId}")
    public ResponseEntity<ResponseHelper.CustomResponse<TravelLogDetailDto>> getTravelLogById(
            @PathVariable Integer travelLogId) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved travel log!",
                        travelLogService.getTravelLogById(travelLogId)),
                HttpStatus.OK);
    }

    @DeleteMapping("/id/{travelLogId}")
    public ResponseEntity<ResponseHelper.CustomResponse<TravelLogDetailDto>> deleteTravelLog(
            @PathVariable Integer travelLogId) {
        boolean isTravelLogDeleted = travelLogService.deleteTravelLog(travelLogId);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(isTravelLogDeleted, "Successfully deleted travel log!", null),
                HttpStatus.OK);
    }

    @PatchMapping("/id/{travelLogId}")
    public ResponseEntity<ResponseHelper.CustomResponse<TravelLogDetailDto>> updateTravelLog(
            @PathVariable Integer travelLogId, @Valid @RequestBody UpdateTravelLogDto updateTravelLogDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated travel log!",
                        travelLogService.updateTravelLog(travelLogId, updateTravelLogDto)),
                HttpStatus.OK);
    }
}