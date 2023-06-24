package com.sda.QuickBite.test.service;

import com.sda.QuickBite.test.dto.UserV2Dto;
import com.sda.QuickBite.test.entity.UserV2;
import com.sda.QuickBite.test.mapper.UserV2Mapper;
import com.sda.QuickBite.test.repository.UserV2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserV2Service {

    @Autowired
    private UserV2Mapper userV2Mapper;

    @Autowired
    private UserV2Repository userV2Repository;
    public void addUserV2(UserV2Dto userV2Dto) {
        UserV2 userV2 = userV2Mapper.map(userV2Dto);
        userV2Repository.save(userV2);
    }
}
