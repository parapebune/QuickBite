package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DishDto {
    private String id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String cookingTime;
    private String imageName;

}
