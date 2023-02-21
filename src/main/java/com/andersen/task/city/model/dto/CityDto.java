package com.andersen.task.city.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityDto {

    @Min(value = 1)
    private Long id;

    @Size(min = 3)
    private String name;

    private String photo;
}
