package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.RestaurantSpecific;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public Restaurant map(RestaurantDto restaurantDto){
        System.out.println("Restaurant Mapped");
        return Restaurant.builder()
                .name(restaurantDto.getName())
                .description(restaurantDto.getDescription())
                .address(restaurantDto.getAddress())
                .phoneNumber(restaurantDto.getPhoneNumber())
                .restaurantSpecific(RestaurantSpecific.valueOf(restaurantDto.getRestaurantSpecific()))
                .build();
    }

}
