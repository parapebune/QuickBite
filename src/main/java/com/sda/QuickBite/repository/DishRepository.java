package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {
}
