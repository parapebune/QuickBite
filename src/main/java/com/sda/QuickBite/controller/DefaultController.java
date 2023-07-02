package com.sda.QuickBite.controller;

import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller

public class DefaultController {
    @Autowired
    private Util util;



    @ModelAttribute("fullName")
    public String fullName(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        return util.displayAuthenticatedUserFullName(authentication);
    }
}
