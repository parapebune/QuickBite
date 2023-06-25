package com.sda.QuickBite.test.repository;

import com.sda.QuickBite.test.entity.UserV2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserV2Repository  extends CrudRepository<UserV2,Long> {
}
