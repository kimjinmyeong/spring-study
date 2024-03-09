package org.spring.study.data.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeProductNameDto {

    @NotNull
    private Long number;

    @NotEmpty
    private String name;

}
