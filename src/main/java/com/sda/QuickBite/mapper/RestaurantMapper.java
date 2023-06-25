package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.RestaurantSpecific;
import com.sda.QuickBite.utils.Util;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class RestaurantMapper {

    @Autowired
    private Util util;
    public Restaurant map(RestaurantDto restaurantDto, MultipartFile restaurantImage){
        return Restaurant.builder()
                .name(restaurantDto.getName())
                .description(restaurantDto.getDescription())
                .address(restaurantDto.getAddress())
                .phoneNo(restaurantDto.getPhoneNo())
                .restaurantSpecific(RestaurantSpecific.valueOf(restaurantDto.getRestaurantSpecific()))
                .logo(util.convertToBytes(restaurantImage))
                .build();
    }

    public RestaurantDto map(Restaurant restaurant){
        return RestaurantDto.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .phoneNo(restaurant.getPhoneNo())
                .restaurantSpecific(restaurant.getRestaurantSpecific().name())
                .description(restaurant.getDescription())
                .logo(Base64.encodeBase64String(restaurant.getLogo()))
                .build();
    }
}
