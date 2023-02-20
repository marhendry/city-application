package com.andersen.task.city.controller;

import com.andersen.task.city.dto.City;
import com.andersen.task.city.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CityControllerTest {
    @Autowired
    private CityController cityController;

    @MockBean
    private CityService cityService;

    @BeforeEach
    public void setUp() {
        cityController = new CityController(cityService);
    }
    @Test
    void testGetAllCities() {
        int page = 0;
        int size = 10;
        List<City> cities = Arrays.asList(new City(1L, "New York", "photo1"), new City(2L, "London", "photo2"));
        Page<City> cityPage = new PageImpl<>(cities, PageRequest.of(page, size), cities.size());
        when(cityService.findAllCities(page, size)).thenReturn(cityPage);
        ResponseEntity<Page<City>> response = cityController.getAllCities(page, size);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cityPage, response.getBody());
    }

    @Test
    public void testGetCitiesByName() {
        String name = "New York";
        int page = 0;
        int size = 10;
        List<City> cityList = Arrays.asList(new City(1L, "New York", "photo1"), new City(2L, "London", "photo2"));
        Page<City> cityPage = new PageImpl<>(cityList);
        when(cityService.findCitiesByName(name, page, size)).thenReturn(cityPage);
        ResponseEntity<Page<City>> response = cityController.getCitiesByName(name, page, size);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Page<City> result = response.getBody();
        assertEquals(cityPage.getContent(), result.getContent());
        assertEquals(cityPage.getTotalElements(), result.getTotalElements());
        assertEquals(cityPage.getTotalPages(), result.getTotalPages());
        assertEquals(cityPage.getNumber(), result.getNumber());
        assertEquals(cityPage.getSize(), result.getSize());
    }

    @Test
    public void testUpdateCityBy() {
        // create a City object to pass in the request body
        City city = new City();
        City.builder().id(1L).name("NY").photo("NY.jpg").build();

        // mock the CityService to return the updated City object
        when(cityService.updateCityById(city)).thenReturn(city);

        // create the request entity with the City object in the request body
        HttpEntity<City> requestEntity = new HttpEntity<>(city);

        // make the request to the updateCityBy endpoint
        ResponseEntity<City> responseEntity = cityController.updateCityBy(requestEntity.getBody());

        // assert that the response entity has status 201 CREATED
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // assert that the response entity has the updated City object in the body
        assertEquals(city, responseEntity.getBody());

        // verify that the CityService was called with the correct City object
        verify(cityService).updateCityById(city);
    }

    @Test
    public void testUploadFromFileSuccess() {
        String filename = "cities.csv";
        when(cityService.fillDatabaseFromCsvFile(filename)).thenReturn(true);
        ResponseEntity<Void> response = cityController.uploadFromFile(filename);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUploadFromFileFailure() {
        String filename = "cities.csv";
        when(cityService.fillDatabaseFromCsvFile(filename)).thenReturn(false);
        ResponseEntity<Void> response = cityController.uploadFromFile(filename);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}