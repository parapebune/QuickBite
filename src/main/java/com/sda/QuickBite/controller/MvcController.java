package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/home")
    public String homeGet(Model model){
        return "home";
    }

    @GetMapping("/addRestaurant")
    public String addRestaurantGet(Model model){
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurantDto", restaurantDto);
        return "addRestaurant";
    }

    @PostMapping("/addRestaurant")
    public String addRestaurantPost(@ModelAttribute(name="restaurantDto") RestaurantDto restaurantDto){
        restaurantService.addRestaurant(restaurantDto);
        System.out.println(restaurantDto);
        return "redirect:/addRestaurant";
    }




}
