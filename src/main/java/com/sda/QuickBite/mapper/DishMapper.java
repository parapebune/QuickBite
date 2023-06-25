package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.enums.DishCategory;
import com.sda.QuickBite.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DishMapper {

    @Autowired
    private Convertor convertor;
    public Dish map(DishDto dishDto, MultipartFile dishImage) {
        return Dish.builder()
                .name(dishDto.getName())
                .description(dishDto.getDescription())
                .price(Double.valueOf(dishDto.getPrice()))
                .cookingTime(Integer.valueOf(dishDto.getCookingTime()))
                .category(DishCategory.valueOf(dishDto.getCategory()))
                .image(convertor.convertToBytes(dishImage))
                .build();
    }


}
