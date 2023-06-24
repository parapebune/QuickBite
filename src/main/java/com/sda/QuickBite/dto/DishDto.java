package com.sda.QuickBite.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DishDto {
    private String name;
    private String description;
    private String price;
    private String category;
    private String cookingTime;
    private String image;

}
