package org.spring.study.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {

    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stock;

}
