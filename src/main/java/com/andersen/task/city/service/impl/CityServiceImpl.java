package com.andersen.task.city.service.impl;

import com.andersen.task.city.mapper.CityMapper;
import com.andersen.task.city.model.dto.CityDto;
import com.andersen.task.city.model.entity.City;
import com.andersen.task.city.repository.CityRepository;
import com.andersen.task.city.service.CityService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Setter
@Getter
public class CityServiceImpl implements CityService {

    private final CityMapper cityMapper;

    private final CityRepository cityRepository;

    @PostConstruct
    public void init() throws FileNotFoundException {
        final List<City> cityList = new CsvToBeanBuilder<City>(new FileReader("src/main/resources/cities.csv"))
                .withSeparator(',')
                .withType(City.class)
                .build()
                .parse();
        if (cityList != null) {
            cityRepository.saveAll(cityList);
        }
    }

    @Override
    public Page<CityDto> findAllCities(int page, int size) {
        return cityRepository.findAll(PageRequest.of(page, size)).map(cityMapper::ToCityDto);
    }

    @Override
    public Optional<CityDto> getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.map(c -> new CityDto(c.getId(), c.getName(), c.getPhoto()));
    }

    @Override
    public Page<CityDto> findCitiesByName(String name, int page, int size) {
        return cityRepository.findAllByName(name, PageRequest.of(page, size)).map(cityMapper::ToCityDto);
    }

    @Override
    public CityDto updateCityById(CityDto city) {
        return cityMapper.ToCityDto(cityRepository.save(cityMapper.toCityEntity(city)));
    }
}
