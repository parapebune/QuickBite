package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.*;
import com.sda.QuickBite.entity.*;
import com.sda.QuickBite.enums.RestaurantSpecific;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MvcController extends DefaultController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;
    @Autowired
    private Util util;


    @Autowired
    private OrderEntryService orderEntryService;
    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    private OrderCartEntryService orderCartEntryService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private FoodOrderService foodOrderService;




    @GetMapping("/home")
    public String homeGet(Model model, @RequestParam(name = "category", required = false) String category, Authentication authentication) {

        List<RestaurantDto> restaurantDtoList = null;
        if (category == null) {
            restaurantDtoList = restaurantService.getAllRestaurantDto();
        } else {
            restaurantDtoList = restaurantService.getRestaurantDtoListByCategory(category);
        }
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        return "home";
    }
    @GetMapping("/navBar")
    public String navBarGet(Model model) {
        return "fragments/navBar";
    }

    @GetMapping("/sellerPage")
    public String sellerPageGet(Model model, @RequestParam(name = "category", required = false) String category, Authentication authentication) {
        model.addAttribute("activePage", "/sellerPage");
        User user = userService.getAuthenticatedUser(authentication);
        List<RestaurantDto> restaurantDtoList = null;
        model.addAttribute("restaurantDtoList", restaurantDtoList);

        String userId = String.valueOf(user.getUserId());

        List<RestaurantDto> restaurantDtoListByUserId = restaurantService.getRestaurantDToListByUserId(userId);
        if (category == null) {
            restaurantDtoList = restaurantDtoListByUserId;
        } else {
            restaurantDtoList = restaurantService.getRestaurantDtoListByUserIdAndCategory(restaurantDtoListByUserId, category);
        }
        model.addAttribute("restaurantDtoList", restaurantDtoList);

        List<RestaurantSpecific> restaurantSpecificlistByUserId = restaurantService.getRestaurantSpecificListByUserId(userId);

        model.addAttribute("restaurantSpecificlistByUserId", restaurantSpecificlistByUserId);

        return "sellerPage";

    }

}
