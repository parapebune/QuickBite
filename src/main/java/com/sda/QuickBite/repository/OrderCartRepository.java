package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.OrderCart;
import com.sda.QuickBite.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCartRepository extends CrudRepository<OrderCart, Long> {
    List<OrderCart> findByUser(User user);
}
