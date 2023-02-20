package com.andersen.task.city.service;

import com.andersen.task.city.dto.City;
import org.springframework.data.domain.Page;

public interface CityService {

    Page<City> findAllCities(int page, int size);

    Page<City> findCitiesByName(String name, int page, int size);

    City updateCityById(City city);

    boolean fillDatabaseFromCsvFile(String filename);
}
