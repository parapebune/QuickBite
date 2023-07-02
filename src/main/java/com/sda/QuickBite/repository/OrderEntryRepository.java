package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.OrderCartEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntryRepository extends CrudRepository<OrderCartEntry, Long> {
}
