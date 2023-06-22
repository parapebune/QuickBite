package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.RestaurantSpecific;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public Restaurant map(RestaurantDto restaurantDto){
        return Restaurant.builder()
                .name(restaurantDto.getName())
                .description(restaurantDto.getDescription())
                .address(restaurantDto.getAddress())
                .phoneNo(restaurantDto.getPhoneNo())
                .restaurantSpecific(RestaurantSpecific.valueOf(restaurantDto.getRestaurantSpecific()))
                .build();
    }
}
