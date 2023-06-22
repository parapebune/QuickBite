package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.OrderEntry;
import org.springframework.data.repository.CrudRepository;

public interface OrderEntryRepository extends CrudRepository<OrderEntry, Long> {
}
