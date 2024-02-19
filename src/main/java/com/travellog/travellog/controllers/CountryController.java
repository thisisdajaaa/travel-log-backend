package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.country.CreateCountryDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.spec.ICountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
    private final ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<CountryDetailDto>> createCountry(
            @Valid @RequestBody CreateCountryDto countryDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created country!",
                        countryService.createCountry(countryDto)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<CountryDetailDto>>> getCountries() {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved all countries!",
                        countryService.getCountries()),
                HttpStatus.OK);
    }
}
