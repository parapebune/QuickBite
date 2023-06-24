package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.mapper.UserMapper;
import com.sda.QuickBite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;
    public void addUser(UserDto userDto){
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }
}
