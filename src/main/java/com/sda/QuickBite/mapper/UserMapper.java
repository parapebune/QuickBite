package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.ChangePasswordDto;
import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.dto.UserProfileDto;
import com.sda.QuickBite.entity.OrderCart;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.enums.Role;
import com.sda.QuickBite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    public User map(UserDto userDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .password(encodedPassword)
                .role(Role.valueOf(userDto.getRole()))
                .orderCart(new OrderCart())
                .build();
    }

    public UserDto map(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();


    }

    public UserProfileDto mapProfile(User user) {
        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

}
