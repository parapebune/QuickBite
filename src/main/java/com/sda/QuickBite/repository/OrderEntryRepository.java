package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.OrderEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntryRepository extends CrudRepository<OrderEntry, Long> {
}
