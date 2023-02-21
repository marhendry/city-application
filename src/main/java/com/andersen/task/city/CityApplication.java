package com.andersen.task.city;

import com.andersen.task.city.repository.CityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CityRepository.class)
public class CityApplication {

    public static void main(String[] args) {
		SpringApplication.run(CityApplication.class, args);
	}

}
