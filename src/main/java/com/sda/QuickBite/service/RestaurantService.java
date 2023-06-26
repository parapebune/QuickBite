package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.mapper.RestaurantMapper;
import com.sda.QuickBite.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;
    public void addRestaurant(RestaurantDto restaurantDto, MultipartFile restaurantImage, MultipartFile restaurantBackgroundImg){
        Restaurant restaurant = restaurantMapper.map(restaurantDto, restaurantImage, restaurantBackgroundImg);
        restaurantRepository.save(restaurant);
    }

    public Optional<RestaurantDto> getRestaurantDtoById(String restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(Long.valueOf(restaurantId));
        if(optionalRestaurant.isEmpty()){
            return Optional.empty();
        }
        Restaurant restaurant = optionalRestaurant.get();
        RestaurantDto restaurantDto = restaurantMapper.map(restaurant);
        return Optional.of(restaurantDto);
    }

    public Optional<Restaurant> getRestaurantById(String restaurantId) {
        return restaurantRepository.findById(Long.valueOf(restaurantId));
    }


}
