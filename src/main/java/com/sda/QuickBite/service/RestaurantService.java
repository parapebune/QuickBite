package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.enums.RestaurantSpecific;
import com.sda.QuickBite.mapper.RestaurantMapper;
import com.sda.QuickBite.repository.RestaurantRepository;
import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private Util util;

    public void addRestaurant(RestaurantDto restaurantDto, MultipartFile restaurantImage, MultipartFile restaurantBackgroundImg, User user) {

        Restaurant restaurant = restaurantMapper.map(restaurantDto, restaurantImage, restaurantBackgroundImg, user);
        restaurantRepository.save(restaurant);
    }

    public Optional<RestaurantDto> getRestaurantDtoById(String restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(Long.valueOf(restaurantId));
        if (optionalRestaurant.isEmpty()) {
            return Optional.empty();
        }
        Restaurant restaurant = optionalRestaurant.get();
        RestaurantDto restaurantDto = restaurantMapper.map(restaurant);
        return Optional.of(restaurantDto);
    }

    public Optional<Restaurant> getRestaurantById(String restaurantId) {
        return restaurantRepository.findById(Long.valueOf(restaurantId));
    }


    public List<RestaurantDto> getAllRestaurantDto() {
        Iterable<Restaurant> restaurantIterable = restaurantRepository.findAll();
        return getRestaurantDtoList(restaurantIterable);
    }

    public List<RestaurantDto> getRestaurantDtoListByCategory(String category) {
        Iterable<Restaurant> restaurantIterable = restaurantRepository.findByRestaurantSpecific(RestaurantSpecific.valueOf(category));
        return getRestaurantDtoList(restaurantIterable);
    }

    private List<RestaurantDto> getRestaurantDtoList(Iterable<Restaurant> restaurantIterable) {
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        for (Restaurant restaurant : restaurantIterable) {
            RestaurantDto restaurantDto = restaurantMapper.map(restaurant);
            restaurantDtoList.add(restaurantDto);
        }
        return restaurantDtoList;
    }
    public List<RestaurantDto> getRestaurantDToListByUserId(String userId) {
        List<Restaurant> restaurantList = restaurantRepository.findByUserUserId(Long.valueOf(userId));
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getUser().getUserId().toString().equals(userId)) {
                RestaurantDto restaurantDto = restaurantMapper.map(restaurant);
                restaurantDtoList.add(restaurantDto);
            }

        }

        return restaurantDtoList;

    }



    public List<RestaurantDto> getRestaurantDtoListByUserIdAndCategory(List<RestaurantDto> restaurantDtoListByUserId, String category) {
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        for (RestaurantDto restaurantDto : restaurantDtoListByUserId) {
            if (restaurantDto.getRestaurantSpecific().equals(category)) {
                restaurantDtoList.add(restaurantDto);
            }

        }
        return restaurantDtoList;
    }

    public List<RestaurantSpecific> getRestaurantSpecificListByUserId(String userId) {
        Set<RestaurantSpecific> uniqueRestaurantSpecificSet = new HashSet<>();
        List<Restaurant> restaurantList = restaurantRepository.findByUserUserId(Long.valueOf(userId));
        for (Restaurant restaurant : restaurantList) {
            uniqueRestaurantSpecificSet.add(restaurant.getRestaurantSpecific());
        }
        return new ArrayList<>(uniqueRestaurantSpecificSet);
    }

    public void updateRestaurant(Restaurant outDatedRestaurant, RestaurantDto restaurantDto, MultipartFile restaurantImage, MultipartFile restaurantBackgroundImg) {
        Restaurant restaurant = Restaurant.builder()
                .restaurantId(outDatedRestaurant.getRestaurantId())
                .name(restaurantDto.getName())
                .description(restaurantDto.getDescription())
                .address(restaurantDto.getAddress())
                .restaurantSpecific(RestaurantSpecific.valueOf(restaurantDto.getRestaurantSpecific()))
                .phoneNo(restaurantDto.getPhoneNo())
                .user(outDatedRestaurant.getUser())
                .build();
        if(!restaurantImage.isEmpty()){
            restaurant.setLogo(util.convertToBytes(restaurantImage));
        }
        if(!restaurantBackgroundImg.isEmpty()){
            restaurant.setBackgroundImage(util.convertToBytes(restaurantBackgroundImg));
        }
        restaurantRepository.save(restaurant);
    }
}

