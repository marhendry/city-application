package com.andersen.task.city.controller;

import com.andersen.task.city.dto.City;
import com.andersen.task.city.service.CityService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @GetMapping
    ResponseEntity<Page<City>> getAllCities(@RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(cityService.findAllCities(page, size), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    ResponseEntity<Page<City>> getCitiesByName(@PathVariable String name,
                                               @RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(cityService.findCitiesByName(name, page, size), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<City> updateCityBy(@RequestBody City city) {
        return new ResponseEntity<>(cityService.updateCityById(city), CREATED);
    }

    @GetMapping("/uploadFile/{filename}")
    ResponseEntity<Void> uploadFromFile(@PathVariable String filename) {
        boolean dataUploadedSuccessfully = cityService.fillDatabaseFromCsvFile(filename);
        return dataUploadedSuccessfully
                ? new ResponseEntity<>(CREATED)
                : new ResponseEntity<>(BAD_REQUEST);
    }

}
