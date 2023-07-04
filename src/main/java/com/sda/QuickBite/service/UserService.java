package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.EmailDto;
import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.dto.UserProfileDto;
import com.sda.QuickBite.entity.ForgotPassword;
import com.sda.QuickBite.entity.OrderCart;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.mapper.UserMapper;
import com.sda.QuickBite.repository.ForgotPasswordRepository;
import com.sda.QuickBite.repository.UserRepository;
import com.sda.QuickBite.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;


    public void addUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }


    public Optional<User> getUserById(String userId) {
        return userRepository.findByUserId(Long.valueOf(userId));
    }

    public Optional<UserDto> getUserDtoById(String userId) {

        Optional<User> optionalUser = userRepository.findByUserId(Long.valueOf(userId));
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        UserDto userDto = userMapper.map(user);
        return Optional.of(userDto);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
        return optionalUser.orElse(null);
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

    public void updateUserPassword(User user) {
        userRepository.save(user);
    }

    public void updateUserOrderCart(User user) {
        user.setOrderCart(new OrderCart());
        userRepository.save(user);
    }

    public void processForgotPassword(EmailDto emailDto) {
        UUID uuid = UUID.randomUUID();
        String recoveryCode = uuid.toString();

//        System.out.println("Email: " + emailDto.getEmail());
//        System.out.println("Recovery code: " + recoveryCode);

        String encodedEmail = Base64.getEncoder().encodeToString(emailDto.getEmail().getBytes());
        String encodedEmailForLink = URLEncoder.encode(encodedEmail, StandardCharsets.UTF_8)
                .replace(".", "%2E");
        String encodedRecoveryCode = Base64.getEncoder().encodeToString(recoveryCode.getBytes());
        String encodedRecoveryCodeForLink = URLEncoder.encode(encodedRecoveryCode, StandardCharsets.UTF_8)
                .replace(".", "%2E");

        String passwordRecoveryLink = "http://localhost:8080/forgotPassword/" + encodedEmailForLink + "/" + encodedRecoveryCodeForLink;

        Boolean alreadyInTheDatabase = forgotPasswordRepository.existsByEmail(emailDto.getEmail());

        if (!alreadyInTheDatabase) {
            ForgotPassword forgotPassword = ForgotPassword.builder()
                    .email(emailDto.getEmail())
                    .recoveryCode(recoveryCode)
                    .build();
            forgotPasswordRepository.save(forgotPassword);
            //send email to user
        } else {
            Optional<ForgotPassword> optionalForgotPassword = forgotPasswordRepository.findByEmail(emailDto.getEmail());
            if (optionalForgotPassword.isEmpty()) {
                new RuntimeException("Email not found in Database");
            }
            ForgotPassword forgotPassword = optionalForgotPassword.get();
            forgotPassword.setRecoveryCode(recoveryCode);
            forgotPasswordRepository.save(forgotPassword);
        }
        System.out.println(passwordRecoveryLink);

    }

    public Boolean verifyRecoveryCode(String encodedEmailForLink, String encodedRecoveryCodeForLink) {
        String decodedEmail = URLDecoder.decode(encodedEmailForLink, StandardCharsets.UTF_8);
        byte[] emailBytes = Base64.getDecoder().decode(decodedEmail);
        String email = new String(emailBytes);

        String decodedRecoveryCode = URLDecoder.decode(encodedRecoveryCodeForLink, StandardCharsets.UTF_8);
        byte[] recoveryCodeBytes = Base64.getDecoder().decode(decodedRecoveryCode);
        String recoveryCode = new String(recoveryCodeBytes);

        Optional<ForgotPassword> optionalForgotPassword = forgotPasswordRepository.findByEmail(email);
        if (optionalForgotPassword.isEmpty()) {
            return false;
        }
        ForgotPassword forgotPassword = optionalForgotPassword.get();
        String recoveryCodeByEmail = forgotPassword.getRecoveryCode();

        if (recoveryCodeByEmail.equals(recoveryCode)) {
            return true;
        }
        return false;

    }
}
