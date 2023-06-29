package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.RestaurantSpecific;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findByRestaurantSpecific(RestaurantSpecific restaurantSpecific);

    List<Restaurant> findByUserUserId(String userId);
}
