package com.andersen.task.city.service;

import com.andersen.task.city.dto.City;
import com.andersen.task.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void testFindAllCities() {
        int page = 0;
        int size = 10;
        List<City> cities = Arrays.asList(new City(1L, "New York", "ny.jpg"),
                new City(2L, "Los Angeles", "la.jpg"));
        Page<City> pageResult = new PageImpl<>(cities);
        when(cityRepository.findAll(PageRequest.of(page, size))).thenReturn(pageResult);
        Page<City> result = cityService.findAllCities(page, size);
        assertEquals(cities, result.getContent());
    }

    @Test
    void testFindCitiesByName() {
        // given
        String name = "New York";
        int page = 0;
        int size = 10;
        List<City> expectedCities = Arrays.asList(
                new City(1L, "New York", "new_york.jpg"),
                new City(2L, "New York Mills", "new_york_mills.jpg")
        );
        Page<City> expectedPage = new PageImpl<>(expectedCities);

        when(cityRepository.findAllByName(eq(name), any(Pageable.class))).thenReturn(expectedPage);

        // when
        Page<City> actualPage = cityService.findCitiesByName(name, page, size);

        // then
        assertThat(actualPage.getContent()).isEqualTo(expectedPage.getContent());
        assertThat(actualPage.getTotalElements()).isEqualTo(expectedPage.getTotalElements());
        verify(cityRepository).findAllByName(eq(name), any(Pageable.class));
    }

    @Test
    void testUpdateCityById() {
        // given
        Long cityId = 1L;
        City cityToUpdate = new City(cityId, "New York", "new_york.jpg");
        City expectedCity = new City(cityId, "Updated Name", "updated_photo.jpg");

        when(cityRepository.save(cityToUpdate)).thenReturn(expectedCity);

        // when
        City actualCity = cityService.updateCityById(cityToUpdate);

        // then
        assertThat(actualCity).isEqualTo(expectedCity);
        verify(cityRepository).save(cityToUpdate);
    }

    @Test
    void testFillDatabaseFromCsvFile() throws Exception {
        // given
        String filename = "cities.csv";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        List<City> expectedCities = Arrays.asList(
                new City(1L, "New York", "new_york.jpg"),
                new City(2L, "Los Angeles", "los_angeles.jpg"),
                new City(3L, "Chicago", "chicago.jpg")
        );
        when(cityRepository.saveAll(anyList())).thenReturn(expectedCities);

        // when
        boolean result = cityService.fillDatabaseFromCsvFile(filename);

        // then
        assertThat(result).isTrue();
        verify(cityRepository, times(1)).saveAll(any());
    }

    @Test
    void testFillDatabaseFromWrongCsvFile() {
        // given
        String filename = "wrongFile.csv";

        // when
        boolean result = cityService.fillDatabaseFromCsvFile(filename);

        // then
        assertThat(result).isFalse();
        Mockito.verifyNoInteractions(cityRepository);
    }
}