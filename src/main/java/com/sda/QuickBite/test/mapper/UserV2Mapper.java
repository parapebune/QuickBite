package com.sda.QuickBite.test.mapper;

import com.sda.QuickBite.test.dto.UserV2Dto;
import com.sda.QuickBite.test.entity.UserV2;
import org.springframework.stereotype.Component;

@Component
public class UserV2Mapper {
    public UserV2 map(UserV2Dto userV2Dto) {
        UserV2 userV2 = UserV2.builder()
                .name(userV2Dto.getName())
                .email(userV2Dto.getEmail())
                .password(userV2Dto.getPassword())
                .build();
        return userV2;
    }
    public UserV2Dto map(UserV2 userV2) {
        UserV2Dto userV2Dto = UserV2Dto.builder()
                .name(userV2.getName())
                .email(userV2.getEmail())
                .build();
        return userV2Dto;
    }
}
