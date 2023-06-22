package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Deliverer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelivererRepository extends CrudRepository<Deliverer, Long> {
}
