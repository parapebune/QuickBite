package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.*;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.enums.DishCategory;
import com.sda.QuickBite.service.DishService;
import com.sda.QuickBite.service.LoginService;
import com.sda.QuickBite.service.RestaurantService;
import com.sda.QuickBite.service.UserService;
import com.sda.QuickBite.utils.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MvcController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;
    @Autowired
    private Util util;

    @Autowired
    private LoginService loginService;

    @ModelAttribute("fullName")
    public String fullName(Authentication authentication){
        if (authentication==null){
            return null;
        }
        return util.displayAuthenticatedUserFullName(authentication);
    }

    @GetMapping("/home")
    public String homeGet(Model model, @RequestParam(name = "category",required = false) String category, Authentication authentication){

        List<RestaurantDto> restaurantDtoList = null;
        if(category == null){
             restaurantDtoList = restaurantService.getAllRestaurantDto();
        }else {
            restaurantDtoList = restaurantService.getRestaurantsByCategory(category);
        }
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        return "home";
    }

//    @GetMapping("/navBar")
//    public String navBarGet(Model model){
//        return "fragments/navBar";
//    }

    @GetMapping("/navBar")
    public String navBarGet(Model model){

        return "fragments/navBar";
    }

    @GetMapping("/registration")
    public String registrationGet(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto",userDto);
        return "registration";
    }
    @PostMapping("/registration")
    public String registerPost(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.addUser(userDto);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }

    @GetMapping("/addRestaurant")
    public String addRestaurantGet(Model model){
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurantDto", restaurantDto);
        return "addRestaurant";
    }

    @PostMapping("/addRestaurant")
    public String addRestaurantPost(@ModelAttribute(name = "restaurantDto") RestaurantDto restaurantDto, BindingResult bindingResult,
                                    MultipartFile restaurantLogo, MultipartFile restaurantBackground, Authentication authentication){

        if(bindingResult.hasErrors()){
            return "addRestaurant";
        }

        String email = authentication.getName();
        Optional<User> optionalUser = userService.getUserByEmail(email);
        if(optionalUser.isEmpty()){
            return "error";
        }
        User user = optionalUser.get();
        restaurantService.addRestaurant(restaurantDto, restaurantLogo, restaurantBackground, user);
        return "redirect:/addRestaurant";
    }

    @GetMapping("/addDish/{restaurantId}")
    public String addDishGet(Model model, @PathVariable(name = "restaurantId") String restaurantId){
        DishDto dishDto = new DishDto();
        model.addAttribute("dishDto",dishDto);
        return "addDish";
    }

    @PostMapping("/addDish/{restaurantId}")
    public String addDishPost(@ModelAttribute(name = "dishDto") @Valid DishDto dishDto, BindingResult bindingResult,
                              @RequestParam("dishImage") MultipartFile dishImage,
                              @PathVariable(name = "restaurantId") String restaurantId){
        if(bindingResult.hasErrors()){
            return "addDish";
        }

        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(restaurantId);
        if(optionalRestaurant.isEmpty()){
            return "error";
        }
        Restaurant restaurant = optionalRestaurant.get();
        dishService.addDish(dishDto, dishImage, restaurant);
        return "redirect:/restaurantPage/" + restaurantId;
    }


    @GetMapping("/dish/{dishId}")
    public String dishGet(Model model, @PathVariable(name = "dishId") String dishId){
        System.out.println("Ajunge?");
        Optional<DishDto> optionalDishDto = dishService.getDishDtoById(dishId);
        if(optionalDishDto.isEmpty()){
            return "error";
        }
        DishDto dishDto = optionalDishDto.get();
        model.addAttribute("dishDto",dishDto);
        System.out.println(dishDto);
        return "dish";
    }

    @GetMapping("/restaurantPage/{restaurantId}")
    public String restaurantPageGet(@PathVariable(value = "restaurantId") String restaurantId, Model model){
        Optional<RestaurantDto> optionalRestaurantDto = restaurantService.getRestaurantDtoById(restaurantId);
        if(optionalRestaurantDto.isEmpty()){
            return "error";
        }
        RestaurantDto restaurantDto = optionalRestaurantDto.get();
        model.addAttribute("restaurantDto",restaurantDto);

        List<DishCategoryDto> dishCategoryDtoList = dishService.getDishDtoListGroupByCategory(restaurantId);
        model.addAttribute("dishCategoryDtoList",dishCategoryDtoList);

        return "restaurantPage";
    }

    @GetMapping("/dish")
    public String dishGet(){
        return "dish";}
    @GetMapping("/orderHistory")
    public String orderHistoryGet(){
        return "orderHistory";}

    @GetMapping("/shoppingCart")
    public String shoppingCartGet(){
        return "shoppingCart";}


    @GetMapping("/sellerPage")
    public String sellerPageGet(Model model){


        return "sellerPage";
    }

    @GetMapping("/yourProfile")
    public String yourProfileGet(Model model){


        return "yourProfile";
    }





}
