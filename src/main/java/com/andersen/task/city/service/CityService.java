package com.andersen.task.city.service;

import com.andersen.task.city.model.dto.CityDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CityService {

    Page<CityDto> findAllCities(int page, int size);

    Optional<CityDto> getCityById(Long id);
    Page<CityDto> findCitiesByName(String name, int page, int size);

    CityDto updateCityById(CityDto city);


}
