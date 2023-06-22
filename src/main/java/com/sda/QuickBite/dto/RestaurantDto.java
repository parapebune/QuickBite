package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RestaurantDto {

    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String restaurantSpecific;
    private String logo;



}
