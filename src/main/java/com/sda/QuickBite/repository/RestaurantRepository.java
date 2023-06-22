package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
