package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.OrderCartEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderCartEntryRepository extends CrudRepository<OrderCartEntry, Long> {
    List<OrderCartEntry> findByOrderCartUserEmail(String name);

    List<OrderCartEntry> findAllByOrderCartOrderCartId(Long orderCartId);

    Optional<OrderCartEntry> findByDishDishIdAndOrderCartOrderCartId (Long dishId, Long orderCartId);
}


