package com.sda.QuickBite.controller;

import com.sda.QuickBite.service.OrderCartEntryService;
import com.sda.QuickBite.utils.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller

public class DefaultController {
    @Autowired
    private Util util;
    @Autowired
    private OrderCartEntryService orderCartEntryService;

    @ModelAttribute("fullName")
    public String fullName(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return util.displayAuthenticatedUserFullName(authentication);
    }

    @ModelAttribute("orderCartSize")
    public String redrawBadgeGet(Authentication authentication){
        if (authentication == null) {
            return null;
        }
        return String.valueOf(orderCartEntryService.getOrderCartEntryList(authentication.getName()).size()) ;
    }



}
