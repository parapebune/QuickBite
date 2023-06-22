package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
