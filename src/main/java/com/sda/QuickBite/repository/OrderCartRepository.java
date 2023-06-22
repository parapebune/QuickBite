package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.OrderCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartRepository extends CrudRepository<OrderCart, Long> {
}
