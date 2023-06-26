package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.*;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.DishCategory;
import com.sda.QuickBite.service.DishService;
import com.sda.QuickBite.service.LoginService;
import com.sda.QuickBite.service.RestaurantService;
import com.sda.QuickBite.service.UserService;
import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
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

    @GetMapping("/navBar")
    public String navBarGet(Model model){
        return "fragments/navBar";
    }

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
    @GetMapping("/registration")
    public String registrationGet(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto",userDto);
        return "registration";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        LoginDto loginDto = new LoginDto();
        model.addAttribute("loginDto", loginDto);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute(name = "loginDto") LoginDto loginDto, Model model) {
        System.out.println(loginDto);
        Boolean loginSuccessfull = loginService.login(loginDto);
        if (loginSuccessfull) {
            model.addAttribute("loginMessage", "Login was successfull!!!");
        } else {
            model.addAttribute("loginMessage", "Login failed!!!");
            return "login";
        }
        return "redirect:/addRestaurant";

    }



    @PostMapping("/addRestaurant")
    public String addRestaurantPost(@ModelAttribute(name = "restaurantDto") RestaurantDto restaurantDto,
                                    MultipartFile restaurantLogo, MultipartFile restaurantBackgroundImg){
        restaurantService.addRestaurant(restaurantDto, restaurantLogo, restaurantBackgroundImg);
        return "redirect:/addRestaurant";
    }

    @PostMapping("/registration")
    public String registerPost(@ModelAttribute(name = "userDto") UserDto userDto){
        userService.addUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/addDish/{restaurantId}")
    public String addDishGet(Model model, @PathVariable(name = "restaurantId") String restaurantId){
        DishDto dishDto = new DishDto();
        model.addAttribute("dishDto",dishDto);
        return "addDish";
    }

    @PostMapping("/addDish/{restaurantId}")
    public String addDishPost(@ModelAttribute(name = "dishDto") DishDto dishDto,
                              @RequestParam("dishImage") MultipartFile dishImage,
                              @PathVariable(name = "restaurantId") String restaurantId){

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

        System.out.println(restaurantDto);
        return "restaurantPage";
    }






}
