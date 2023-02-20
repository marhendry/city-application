package com.andersen.task.city.repository;

import com.andersen.task.city.dto.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    Page<City> findAllByName(String name, Pageable pageable);

}
