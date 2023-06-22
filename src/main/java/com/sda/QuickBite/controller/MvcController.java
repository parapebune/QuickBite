package com.sda.QuickBite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

    @GetMapping("/home")
    public String homeGet(Model model){
        return "home";
    }

    @GetMapping("/index")
    public String indexGet(Model model){
        return "index";
    }




}
