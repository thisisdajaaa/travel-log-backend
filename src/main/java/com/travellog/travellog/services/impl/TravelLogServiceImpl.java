package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.travelLog.CreateTravelLogDto;
import com.travellog.travellog.dtos.travelLog.TravelLogDetailDto;
import com.travellog.travellog.dtos.travelLog.UpdateTravelLogDto;
import com.travellog.travellog.exceptions.TravelLogException;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.Country;
import com.travellog.travellog.models.TravelLog;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.ITravelLogRepository;
import com.travellog.travellog.services.spec.ICountryService;
import com.travellog.travellog.services.spec.ICustomUserDetailsService;
import com.travellog.travellog.services.spec.ITravelLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelLogServiceImpl implements ITravelLogService {
    private final ITravelLogRepository travelLogRepository;
    private final ICountryService countryService;
    private final ConversionConfiguration conversionConfiguration;
    private final ICustomUserDetailsService customUserDetailsService;

    public TravelLogServiceImpl(ITravelLogRepository travelLogRepository, ICountryService countryService,
            ConversionConfiguration conversionConfiguration, ICustomUserDetailsService customUserDetailsService) {
        this.travelLogRepository = travelLogRepository;
        this.countryService = countryService;
        this.conversionConfiguration = conversionConfiguration;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public TravelLogDetailDto createTravelLog(CreateTravelLogDto createTravelLogDto) {
        Integer countryId = createTravelLogDto.getCountryId();
        Country country = conversionConfiguration.convert(countryService.getCountryById(countryId), Country.class);
        User currentUser = customUserDetailsService.getAuthenticatedUser();

        TravelLog travelLog = TravelLog.builder()
                .user(currentUser)
                .title(createTravelLogDto.getTitle())
                .description(createTravelLogDto.getDescription())
                .visitDate(createTravelLogDto.getVisitDate())
                .country(country)
                .build();

        TravelLog savedTravelLog = travelLogRepository.save(travelLog);

        return conversionConfiguration.convert(savedTravelLog, TravelLogDetailDto.class);
    }

    @Override
    public List<TravelLogDetailDto> getTravelLogsByUserId(Integer userId) {
        List<TravelLog> travelLogs;

        if (userId != null) {
            travelLogs = travelLogRepository.findByUserId(userId);

            if (travelLogs.isEmpty()) {
                throw new UserException.NotFoundException();
            }
        } else {
            travelLogs = travelLogRepository.findAll();
        }

        return travelLogs.stream()
                .map(travelLog -> conversionConfiguration.convert(travelLog, TravelLogDetailDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TravelLogDetailDto getTravelLogById(Integer id) {
        TravelLog travelLog = travelLogRepository.findById(id).orElseThrow(TravelLogException.NotFoundException::new);

        return conversionConfiguration.convert(travelLog, TravelLogDetailDto.class);
    }

    @Override
    public boolean deleteTravelLog(Integer id) {
        TravelLog travelLog = travelLogRepository.findById(id).orElseThrow(TravelLogException.NotFoundException::new);

        travelLogRepository.delete(travelLog);

        return true;
    }

    @Override
    public TravelLogDetailDto updateTravelLog(Integer travelLogId, UpdateTravelLogDto updateTravelLogDto) {
        TravelLog existingTravelLog = travelLogRepository.findById(travelLogId)
                .orElseThrow(TravelLogException.NotFoundException::new);

        if (updateTravelLogDto.getCountryId() != null) {
            CountryDetailDto countryDetailDto = countryService.getCountryById(updateTravelLogDto.getCountryId());
            existingTravelLog.setCountry(conversionConfiguration.convert(countryDetailDto, Country.class));
        }

        if (updateTravelLogDto.getTitle() != null)
            existingTravelLog.setTitle(updateTravelLogDto.getTitle());
        if (updateTravelLogDto.getDescription() != null)
            existingTravelLog.setDescription(updateTravelLogDto.getDescription());
        if (updateTravelLogDto.getVisitDate() != null)
            existingTravelLog.setVisitDate(updateTravelLogDto.getVisitDate());

        TravelLog updatedTravelLog = travelLogRepository.save(existingTravelLog);

        return conversionConfiguration.convert(updatedTravelLog, TravelLogDetailDto.class);
    }

}
