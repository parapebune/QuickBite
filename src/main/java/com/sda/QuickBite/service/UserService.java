package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.ChangePasswordDto;
import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.dto.UserProfileDto;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.mapper.UserMapper;
import com.sda.QuickBite.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;


    public void addUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }


    public Optional<User> getUserById(String userId) {
        return userRepository.findById(Long.valueOf(userId));
    }

    public Optional<UserDto> getUserDtoById(String userId) {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        UserDto userDto = userMapper.map(user);
        return Optional.of(userDto);
    }

    public Optional<User> getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        return optionalUser;

    }

    public Optional<UserDto> getUserDtoByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        UserDto userDto = userMapper.map(user);
        return Optional.of(userDto);
    }

    public User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = getUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        return user;
    }

    public void updateUser(User user, @Valid UserProfileDto userDto) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    public UserDto getAuthenticatedUserDto(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = getUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        return userMapper.map(user);
    }

    public UserProfileDto getAuthenticatedUserProfileDto(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = getUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        return userMapper.mapProfile(user);
    }

//    public String getEncodedPassword(ChangePasswordDto changePasswordDto) {
//        return bCryptPasswordEncoder.encode(changePasswordDto.getOldPassword());
//    }

    public void updateUserPassword(User user) {
        userRepository.save(user);
    }


}
