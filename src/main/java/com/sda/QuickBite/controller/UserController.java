package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.ChangePasswordDto;
import com.sda.QuickBite.dto.ErrorMessageDto;
import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.dto.UserProfileDto;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController extends DefaultController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration")
    public String registrationGet(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPost(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userDto.getPassword().equals(userDto.getPasswordRetype())) {
            bindingResult.rejectValue("passwordRetype", "error.userDto",
                    "Passwords do not match! Please try again!");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }

    @GetMapping("/yourProfile")
    public String yourProfileGet(Model model, Authentication authentication) {
        UserProfileDto userProfileDto = userService.getAuthenticatedUserProfileDto(authentication);
        model.addAttribute("userProfileDto", userProfileDto);
        return "yourProfile";
    }

    @PostMapping("/yourProfile")
    public String yourProfilePost(@ModelAttribute(name = "userProfileDto") @Valid UserProfileDto userProfileDto
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "yourProfile";
        }
        Optional<User> optionalUser = userService.getUserByEmail(userProfileDto.getEmail());
        if (optionalUser.isEmpty()) {
            util.getErrorMessage("User Not Found", model);
            return "error";
        }
        User user = optionalUser.get();
        userService.updateUser(user, userProfileDto);
        return "redirect:/yourProfile";
    }

    @GetMapping("/changePassword")
    public String changePasswordGet(Model model, Authentication authentication) {

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        model.addAttribute("changePasswordDto", changePasswordDto);


        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassWordPost(@ModelAttribute(name = "changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
                                     BindingResult bindingResult, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String oldPasswordInputByUser = changePasswordDto.getOldPassword();
        String oldPasswordFromDatabase = user.getPassword();

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordRetype())) {
            bindingResult.rejectValue("newPasswordRetype", "error.changePasswordDto",
                    "New Passwords do not match! Please try again!");
        }

        if (bindingResult.hasErrors()) {
            return "changePassword";
        }

        if (!bCryptPasswordEncoder.matches(oldPasswordInputByUser, oldPasswordFromDatabase)) {
            bindingResult.rejectValue("oldPassword", "error.changePasswordDto", "Incorrect old password");
        }

        if (bindingResult.hasErrors()) {
            return "changePassword";
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
        userService.updateUserPassword(user);
        return "redirect:/login";
    }
}
