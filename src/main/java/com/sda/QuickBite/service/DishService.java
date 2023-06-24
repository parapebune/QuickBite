package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.mapper.DishMapper;
import com.sda.QuickBite.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishMapper dishMapper;
    public void addDish(DishDto dishDto, MultipartFile dishImage) {
        Dish dish = dishMapper.map(dishDto, dishImage);
        dishRepository.save(dish);
    }
}
