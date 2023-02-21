package com.andersen.task.city.controller;

import com.andersen.task.city.model.dto.CityDto;
import com.andersen.task.city.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CityAppControllerTest {

    @Autowired
    private CityAppController cityAppController;

    @MockBean
    private CityService cityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cityAppController = new CityAppController(cityService);
    }

    @Test
    public void testGetAllCities() {
        // Создаем тестовые данные
        Page<CityDto> testPage = new PageImpl<>(Arrays.asList(
                new CityDto(1L, "City 1", "Photo1"),
                new CityDto(2L, "City 2", "Photo2")));
        int testPageNumber = 1;
        int testPageSize = 2;

        // Мокаем вызов метода сервиса
        when(cityService.findAllCities(testPageNumber, testPageSize)).thenReturn(testPage);

        // Вызываем метод контроллера и проверяем возвращаемый ответ
        ResponseEntity<Page<CityDto>> responseEntity = cityAppController.getAllCities(testPageNumber, testPageSize);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testPage, responseEntity.getBody());

        // Проверяем, что метод сервиса был вызван один раз с правильными параметрами
        verify(cityService, times(1)).findAllCities(testPageNumber, testPageSize);
    }

    @Test
    public void testGetCitiesByName() {
        String testName = "TestCity";
        Page<CityDto> testPage = new PageImpl<>(Arrays.asList(
                new CityDto(1L, "TestCity1","Photo1"),
                new CityDto(2L, "TestCity2","Photo2")));
        int testPageNumber = 1;
        int testPageSize = 2;

        when(cityService.findCitiesByName(testName, testPageNumber, testPageSize)).thenReturn(testPage);

        ResponseEntity<Page<CityDto>> responseEntity = cityAppController.getCitiesByName(testName, testPageNumber, testPageSize);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testPage, responseEntity.getBody());

        verify(cityService, times(1)).findCitiesByName(testName, testPageNumber, testPageSize);
    }

    @Test
    public void testUpdateCityById() {
        CityDto testCity = new CityDto(1L, "TestCity","Photo1");

        when(cityService.updateCityById(testCity)).thenReturn(testCity);

        ResponseEntity<CityDto> responseEntity = cityAppController.updateCityBy(testCity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testCity, responseEntity.getBody());

        verify(cityService, times(1)).updateCityById(testCity);
    }
}

