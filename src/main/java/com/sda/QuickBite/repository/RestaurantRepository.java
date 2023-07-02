package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.RestaurantSpecific;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findByRestaurantSpecific(RestaurantSpecific restaurantSpecific);

    List<Restaurant> findByUserUserId(Long userId);

    Optional<Restaurant> findByMenuDishId(Long dishId);
    List<Restaurant> findAllByUserUserId(Long userId);
}
