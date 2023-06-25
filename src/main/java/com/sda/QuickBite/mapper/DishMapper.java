package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.DishCategory;
import com.sda.QuickBite.utils.Util;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DishMapper {

    @Autowired
    private Util util;
    public Dish map(DishDto dishDto, MultipartFile dishImage, Restaurant restaurant) {
        return Dish.builder()
                .name(dishDto.getName())
                .description(dishDto.getDescription())
                .price(Double.valueOf(dishDto.getPrice()))
                .cookingTime(Integer.valueOf(dishDto.getCookingTime()))
                .category(DishCategory.valueOf(dishDto.getCategory()))
//                .imageName("")
                .restaurant(restaurant)
                .build();
    }

    public DishDto map(Dish dish){
        return DishDto.builder()
                .id(dish.getId().toString())
                .name(dish.getName())
                .description(dish.getDescription())
                .cookingTime(dish.getCookingTime().toString())
                .category(dish.getCategory().name())
                .price(dish.getPrice().toString())
                .imageName(dish.getImageName())
                .build();
    }


}
