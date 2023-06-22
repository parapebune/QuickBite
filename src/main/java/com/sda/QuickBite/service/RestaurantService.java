package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.mapper.RestaurantMapper;
import com.sda.QuickBite.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;
    public void addRestaurant(RestaurantDto restaurantDto){
        Restaurant restaurant = restaurantMapper.map(restaurantDto);
        restaurantRepository.save(restaurant);
    }
}
