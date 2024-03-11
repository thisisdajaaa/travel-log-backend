package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.files.AddFileResponseDto;
import com.travellog.travellog.dtos.travelLog.CreateTravelLogDto;
import com.travellog.travellog.dtos.travelLog.TravelLogDetailDto;
import com.travellog.travellog.dtos.travelLog.UpdateTravelLogDto;
import com.travellog.travellog.dtos.travelPhoto.TravelPhotoDetailDto;
import com.travellog.travellog.exceptions.TravelLogException;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.Country;
import com.travellog.travellog.models.TravelLog;
import com.travellog.travellog.models.TravelPhoto;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.ITravelLogRepository;
import com.travellog.travellog.repositories.ITravelPhotoRepository;
import com.travellog.travellog.services.spec.ICountryService;
import com.travellog.travellog.services.spec.ICustomUserDetailsService;
import com.travellog.travellog.services.spec.IFileStorageService;
import com.travellog.travellog.services.spec.ITravelLogService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TravelLogServiceImpl implements ITravelLogService {
    private final ITravelLogRepository travelLogRepository;
    private final ITravelPhotoRepository travelPhotoRepository;
    private final IFileStorageService fileStorageService;
    private final ICountryService countryService;
    private final ConversionConfiguration conversionConfiguration;
    private final ICustomUserDetailsService customUserDetailsService;

    public TravelLogServiceImpl(ITravelLogRepository travelLogRepository, ITravelPhotoRepository travelPhotoRepository, IFileStorageService fileStorageService, ICountryService countryService,
                                ConversionConfiguration conversionConfiguration, ICustomUserDetailsService customUserDetailsService) {
        this.travelLogRepository = travelLogRepository;
        this.travelPhotoRepository = travelPhotoRepository;
        this.fileStorageService = fileStorageService;
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
                .visitStartDate(createTravelLogDto.getVisitStartDate())
                .visitEndDate(createTravelLogDto.getVisitEndDate())
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

            if (travelLogs.isEmpty()) throw new UserException.NotFoundException();
        } else {
            travelLogs = travelLogRepository.findAll();
        }

        return travelLogs
                .stream()
                .map(travelLog -> conversionConfiguration.convert(travelLog, TravelLogDetailDto.class))
                .toList();
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
        if (updateTravelLogDto.getVisitStartDate() != null)
            existingTravelLog.setVisitStartDate(updateTravelLogDto.getVisitStartDate());
        if (updateTravelLogDto.getVisitEndDate() != null)
            existingTravelLog.setVisitEndDate(updateTravelLogDto.getVisitEndDate());

        TravelLog updatedTravelLog = travelLogRepository.save(existingTravelLog);

        return conversionConfiguration.convert(updatedTravelLog, TravelLogDetailDto.class);
    }

    @Override
    public List<TravelPhotoDetailDto> addPhotosToTravelLog(Integer travelLogId, List<AddFileResponseDto> fileResponseDtos) {
        TravelLog travelLog = travelLogRepository.findById(travelLogId)
                .orElseThrow(TravelLogException.NotFoundException::new);

        List<TravelPhoto> photos = fileResponseDtos.stream().map(photo -> TravelPhoto.builder()
                .photoUrl(fileStorageService.getFormattedFilePath(photo.getPath()))
                .travelLog(travelLog)
                .build()).toList();

        // Assuming you have a TravelPhotoRepository or similar
        // Save all photos
        travelPhotoRepository.saveAll(photos);

        // Assuming you have a bidirectional relationship and want to keep both sides in sync
        photos.forEach(travelLog::addPhoto);

        // Save the updated travel log with the associated photos
        travelLogRepository.save(travelLog);

        return photos.stream()
                .map(photo -> TravelPhotoDetailDto.builder()
                        .photoUrl(photo.getPhotoUrl())
                        .id(photo.getId())
                        .travelLogId(travelLogId)
                        .description(photo.getDescription()).build()
                ).toList();
    }

    @Override
    public TravelLogDetailDto createTravelLogWithPhotos(CreateTravelLogDto createTravelLogDto, MultipartFile[] images) {
        TravelLogDetailDto travelLogDetail = createTravelLog(createTravelLogDto);
        List<AddFileResponseDto> fileResponseDtos = fileStorageService.addMultipleFiles(images);
        List<TravelPhotoDetailDto> travelPhotoDetailDtos = addPhotosToTravelLog(travelLogDetail.getId(), fileResponseDtos);
        travelLogDetail.setPhotos(travelPhotoDetailDtos);

        return travelLogDetail;
    }
}
