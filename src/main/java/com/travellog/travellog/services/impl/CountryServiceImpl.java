package com.travellog.travellog.services.impl;

import com.travellog.travellog.services.spec.ICountryService;
import org.springframework.stereotype.Service;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.CountryDetailDto;
import com.travellog.travellog.dtos.CreateCountryDto;
import com.travellog.travellog.models.Country;
import com.travellog.travellog.repositories.ICountryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements ICountryService {
    private final ICountryRepository countryRepository;
    private final ConversionConfiguration conversionConfiguration;

    public CountryServiceImpl(ICountryRepository countryRepository, ConversionConfiguration conversionConfiguration) {
        this.countryRepository = countryRepository;
        this.conversionConfiguration = conversionConfiguration;
    }

    @Override
    public CountryDetailDto createCountry(CreateCountryDto countryDto) {
        Country country = conversionConfiguration.convert(countryDto, Country.class);
        Country savedCountry = countryRepository.save(country);
        return conversionConfiguration.convert(savedCountry, CountryDetailDto.class);
    }

    @Override
    public List<CountryDetailDto> getCountries() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDetailDto> mappedCountries = new ArrayList<>();

        for (Country country : countries) {
            mappedCountries.add(conversionConfiguration.convert(country, CountryDetailDto.class));
        }

        return mappedCountries;
    }

    @Override
    public boolean isCountryListEmpty() {
        return countryRepository.count() == 0;
    }
}
