package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    Optional<Dish> findDishByDishId(Long dishId);

}
