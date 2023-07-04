package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.*;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Controller
public class UserController extends DefaultController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Util util;


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
    public String changePasswordPost(@ModelAttribute(name = "changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
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

    @GetMapping("/forgotPassword")
    public String forgotPasswordGet(Model model) {
        EmailDto emailDto = new EmailDto();
        model.addAttribute("emailDto", emailDto);
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordPost(@ModelAttribute(name = "emailDto") EmailDto emailDto, Model model) {
        Optional<User> optionalUser = userService.getUserByEmail(emailDto.getEmail());
        if (optionalUser.isEmpty()) {
            util.getErrorMessage("Email not in the database!", model);
            return "error";
        }
        userService.processForgotPassword(emailDto);
        return "redirect:/login";
    }

    @GetMapping("/forgotPassword/{encodedEmailForLink}/{encodedRecoveryCodeForLink}")
    public String changeForgottenPassword(@PathVariable(name = "encodedEmailForLink") String encodedEmailForLink
            , @PathVariable(name = "encodedRecoveryCodeForLink") String encodedRecoveryCodeForLink, Model model) {


        ChangeForgottenPasswordDto changeForgottenPasswordDto = new ChangeForgottenPasswordDto();
        model.addAttribute("changeForgottenPasswordDto", changeForgottenPasswordDto);
        return "changeForgottenPassword";
    }

    @PostMapping("/changeForgottenPassword/{encodedEmailForLink}/{encodedRecoveryCodeForLink}")
    public String changeForgottenPasswordPost(@ModelAttribute(name="changeForgottenPasswordDto") ChangeForgottenPasswordDto changeForgottenPasswordDto
            , @PathVariable(name = "encodedEmailForLink") String encodedEmailForLink
            , @PathVariable(name = "encodedRecoveryCodeForLink") String encodedRecoveryCodeForLink
            , Model model, BindingResult bindingResult){

        String decodedEmail = URLDecoder.decode(encodedEmailForLink, StandardCharsets.UTF_8);
        byte[] emailBytes = Base64.getDecoder().decode(decodedEmail);
        String email = new String(emailBytes);

        String decodedRecoveryCode = URLDecoder.decode(encodedRecoveryCodeForLink, StandardCharsets.UTF_8);
        byte[] recoveryCodeBytes = Base64.getDecoder().decode(decodedRecoveryCode);
        String recoveryCode = new String(recoveryCodeBytes);

        Boolean correctRecoveryCode = userService.verifyRecoveryCode(encodedEmailForLink, encodedRecoveryCodeForLink);
        if (!correctRecoveryCode){
            util.getErrorMessage("Bad Request!", model);
            return "error";
        }

        Optional<User> optionalUser = userService.getUserByEmail(email);
        if (optionalUser.isEmpty()){
            util.getErrorMessage("User Not Found", model);
        }

        User user = optionalUser.get();
        System.out.println("Old password was: " + user.getPassword());
        
        
        if (!changeForgottenPasswordDto.getNewPassword().equals(changeForgottenPasswordDto.getNewPasswordRetype())) {
            bindingResult.rejectValue("newPasswordRetype", "error.changeForgottenPasswordDto",
                    "New Passwords do not match! Please try again!");
        }

        if (bindingResult.hasErrors()) {
            return "changeForgottenPassword";
        }

        String encodedNewPassword = bCryptPasswordEncoder.encode(changeForgottenPasswordDto.newPassword);
        System.out.println("New password id: " + encodedNewPassword);
        user.setPassword(bCryptPasswordEncoder.encode(changeForgottenPasswordDto.getNewPassword()));
        userService.updateUserPassword(user);
        return "redirect:/login";
    }

//    public String decodeRecoveryCodeFromLink(String encodedRecoveryCodeForLink) {
//        String decodedRecoveryCode = URLDecoder.decode(encodedRecoveryCodeForLink, StandardCharsets.UTF_8);
//        byte[] recoveryCodeBytes = Base64.getDecoder().decode(decodedRecoveryCode);
//        String recoveryCode = new String(recoveryCodeBytes);
//        return recoveryCode;
//    }
//
//    public String decodeEmailFromLink(String encodedEmailForLink) {
//        String decodedEmail = URLDecoder.decode(encodedEmailForLink, StandardCharsets.UTF_8);
//        byte[] emailBytes = Base64.getDecoder().decode(decodedEmail);
//        String email = new String(emailBytes);
//        return email;
//    }

}
