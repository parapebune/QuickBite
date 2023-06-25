package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.dto.UserDto;
import com.sda.QuickBite.service.DishService;
import com.sda.QuickBite.service.RestaurantService;
import com.sda.QuickBite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class MvcController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

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

    @PostMapping("/addRestaurant")
    public String addRestaurantPost(@ModelAttribute(name = "restaurantDto") RestaurantDto restaurantDto,
                                    MultipartFile restaurantImage){
        restaurantService.addRestaurant(restaurantDto, restaurantImage);
        return "redirect:/addRestaurant";
    }

    @PostMapping("/registration")
    public String registerPost(@ModelAttribute(name = "userDto") UserDto userDto){
        userService.addUser(userDto);
        return "redirect:/registration";
    }

    @GetMapping("/addDish")
    public String addDishGet(Model model){
        DishDto dishDto = new DishDto();
        model.addAttribute("dishDto",dishDto);
        return "addDish";
    }

    @PostMapping("/addDish")
    public String addDishPost(@ModelAttribute(name = "dishDto") DishDto dishDto,
                              @RequestParam("dishImage") MultipartFile dishImage){
        dishService.addDish(dishDto, dishImage);
        return "redirect:/addDish";
    }

    @GetMapping("/restaurantPage/{restaurantId}")
    public String restaurantPageGet(@PathVariable(value = "restaurantId") String restaurantId, Model model){
        Optional<RestaurantDto> optionalRestaurantDto = restaurantService.getRestaurantById(restaurantId);
        if(optionalRestaurantDto.isEmpty()){
            return "error";
        }
        RestaurantDto restaurantDto = optionalRestaurantDto.get();
        model.addAttribute("restaurantDto",restaurantDto);
        return "restaurantPage";
    }



}
