package com.andersen.task.city.mapper;

import com.andersen.task.city.model.entity.City;
import com.andersen.task.city.model.dto.CityDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "photo", source = "photo")
    CityDto toCityDto(City city);

    @InheritInverseConfiguration
    City toCityEntity(CityDto cityDto);
}
