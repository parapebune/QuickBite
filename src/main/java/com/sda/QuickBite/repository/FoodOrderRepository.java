package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.FoodOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FoodOrderRepository extends CrudRepository<FoodOrder,Long> {
    List<FoodOrder> findAllByUserEmail(String email);
    List<FoodOrder> findAllByRestaurantRestaurantId(Long restaurantId);

    List<FoodOrder> findAllByUserEmailOrderByOrderDateDesc(String email);

    Collection<? extends FoodOrder> findAllByRestaurantRestaurantIdOrderByOrderDateDesc(Long restaurantId);
}
