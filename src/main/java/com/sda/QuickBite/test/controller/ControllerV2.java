package com.sda.QuickBite.test.controller;

import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.test.dto.UserV2Dto;
import com.sda.QuickBite.test.service.UserV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerV2 {


    @Autowired
    private UserV2Service userV2Service;

    @GetMapping("/registration_v2")
    public String registrationV2Post(Model model){
        UserV2Dto userV2Dto = new UserV2Dto();
        model.addAttribute("userV2Dto", userV2Dto);
        return "registration_v2";
    }

    @PostMapping("/registration_v2")
    public String registrationPost(@ModelAttribute(name = "userV2Dto") UserV2Dto userV2Dto){
        userV2Service.addUserV2(userV2Dto);
        return "redirect:/registration_v2";
    }



}
