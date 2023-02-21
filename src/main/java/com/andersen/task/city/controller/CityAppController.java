package com.andersen.task.city.controller;

import com.andersen.task.city.model.dto.CityDto;
import com.andersen.task.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.andersen.task.city.config.AuthorizationExpression.HAS_ROLE_ALLOWED_EDIT_AUTHORITY;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityAppController {

    private final CityService cityService;

    @PutMapping
    @PreAuthorize(HAS_ROLE_ALLOWED_EDIT_AUTHORITY)
    ResponseEntity<CityDto> updateCityBy(@RequestBody @Valid CityDto city) {
        return new ResponseEntity<>(cityService.updateCityById(city), CREATED);
    }

    @GetMapping("/{name}")
    ResponseEntity<Page<CityDto>> getCitiesByName(@PathVariable String name,
                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, defaultValue = "100") int size) {
        return new ResponseEntity<>(cityService.findCitiesByName(name, page, size), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<CityDto>> getAllCities(@RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(cityService.findAllCities(page, size), HttpStatus.OK);
    }
}
