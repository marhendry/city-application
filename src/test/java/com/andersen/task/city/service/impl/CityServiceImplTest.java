package com.andersen.task.city.service.impl;

import com.andersen.task.city.model.dto.CityDto;
import com.andersen.task.city.model.entity.City;
import com.andersen.task.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class CityServiceImplTest {

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityServiceImpl cityService;

    @Test
    void testFindAllCities() {
        int page = 0;
        int size = 10;
        List<City> cityEntities = Arrays.asList(new City(1L, "New York", "ny.jpg"),
                new City(2L, "Los Angeles", "la.jpg"));
        List<CityDto> cityDtos = Arrays.asList(new CityDto(1L, "New York", "ny.jpg"),
                new CityDto(2L, "Los Angeles", "la.jpg"));
        Page<City> pageCityEntitiesResult = new PageImpl<>(cityEntities);
        when(cityRepository.findAll(PageRequest.of(page, size))).thenReturn(pageCityEntitiesResult);
        Page<CityDto> result = cityService.findAllCities(page, size);
        assertEquals(cityDtos, result.getContent());
    }

    @Test
    public void testGetCityById() {
        Long id = 1L;
        City city = new City();
        city.setId(id);
        city.setName("Test City");
        city.setPhoto("test_photo.jpg");

        when(cityRepository.findById(id)).thenReturn(Optional.of(city));

        Optional<CityDto> result = cityService.getCityById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(city.getName(), result.get().getName());
        assertEquals(city.getPhoto(), result.get().getPhoto());
    }

    @Test
    void findCitiesByName_ReturnsPageOfCityDto() {
        String name = "test";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<City> cities = Arrays.asList(new City(1L, "TestCity", "Photo1"));
        Page<City> cityPage = new PageImpl<>(cities, pageable, cities.size());
        when(cityRepository.findAllByName(name, pageable)).thenReturn(cityPage);

        Page<CityDto> result = cityService.findCitiesByName(name, page, size);

        assertEquals(1, result.getContent().size());
        assertEquals(new CityDto(1L, "TestCity", "Photo1"), result.getContent().get(0));
        verify(cityRepository).findAllByName(name, pageable);
    }


    @Test
    public void testUpdateCityById() {
        Long id = 1L;
        String name = "Test City";
        String photo = "test_photo";
        CityDto cityDtoEx = new CityDto(id, name, photo);
        City city = new City(id, name, photo);

        when(cityRepository.save(any())).thenReturn(city);

        CityDto result = cityService.updateCityById(cityDtoEx);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(photo, result.getPhoto());
    }

}