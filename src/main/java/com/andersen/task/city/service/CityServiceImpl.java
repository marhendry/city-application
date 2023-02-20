package com.andersen.task.city.service;

import com.andersen.task.city.dto.City;
import com.andersen.task.city.repository.CityRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Page<City> findAllCities(int page, int size) {
        return cityRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<City> findCitiesByName(String name, int page, int size) {
        return cityRepository.findAllByName(name, PageRequest.of(page, size));
    }

    @Override
    public City updateCityById(City city) {
        return cityRepository.save(city);
    }

    @Override
    public boolean fillDatabaseFromCsvFile(String filename) {
        final List<City> cityList;
        try {
            cityList = new CsvToBeanBuilder<City>(new FileReader(getClass().getResource("/" + filename).toURI().getPath()))
                    .withSeparator(',')
                    .withType(City.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException | URISyntaxException | NullPointerException e) {
            return false;
        }
        cityRepository.saveAll(cityList);
        return true;
    }
}
