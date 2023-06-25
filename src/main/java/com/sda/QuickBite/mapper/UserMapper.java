package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserDto userDto){
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }
}
