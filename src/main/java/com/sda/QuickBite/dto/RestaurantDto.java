package com.sda.QuickBite.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class RestaurantDto {
    private String name;
    private String description;
    private String address;
    private String phoneNo;
    private String restaurantSpecific;
    private String logo;
}
