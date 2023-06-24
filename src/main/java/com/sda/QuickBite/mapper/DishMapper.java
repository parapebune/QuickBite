package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.enums.DishCategory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DishMapper {
    public Dish map(DishDto dishDto, MultipartFile dishImage) {
        return Dish.builder()
                .name(dishDto.getName())
                .description(dishDto.getDescription())
                .price(Double.valueOf(dishDto.getPrice()))
                .cookingTime(Integer.valueOf(dishDto.getCookingTime()))
                .category(DishCategory.valueOf(dishDto.getCategory()))
                .image(convertToBytes(dishImage))
                .build();

    }

    private byte[] convertToBytes(MultipartFile multipartFile){
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
