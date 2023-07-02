package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.DishCategoryDto;
import com.sda.QuickBite.dto.RestaurantDto;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;
    @Autowired
    private Util util;

    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    private OrderCartEntryService orderCartEntryService;
    @Autowired
    private FoodOrderService foodOrderService;


    @GetMapping("/addRestaurant")
    public String addRestaurantGet(Model model){
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurantDto", restaurantDto);
        return "addRestaurant";
    }

    @PostMapping("/addRestaurant")
    public String addRestaurantPost(@ModelAttribute(name = "restaurantDto") @Valid RestaurantDto restaurantDto, BindingResult bindingResult,
                                    MultipartFile restaurantLogo, MultipartFile restaurantBackground, Authentication authentication){
        String email = authentication.getName();
        Optional<User> optionalUser = userService.getUserByEmail(email);
        if(optionalUser.isEmpty()){
            return "error";
        }
        User user = optionalUser.get();
        restaurantService.addRestaurant(restaurantDto, restaurantLogo, restaurantBackground, user);
        return "redirect:/addRestaurant";
    }

    @GetMapping("/restaurant/{restaurantId}")
    public String restaurantPageGet(@PathVariable(value = "restaurantId") String restaurantId, Model model){
        Optional<RestaurantDto> optionalRestaurantDto = restaurantService.getRestaurantDtoById(restaurantId);
        if(optionalRestaurantDto.isEmpty()){
            return "error";
        }
        RestaurantDto restaurantDto = optionalRestaurantDto.get();
        model.addAttribute("restaurantDto",restaurantDto);

        List<DishCategoryDto> dishCategoryDtoList = dishService.getDishDtoListGroupByCategory(restaurantId);
        model.addAttribute("dishCategoryDtoList",dishCategoryDtoList);

        return "restaurant";
    }

    @GetMapping("/editRestaurant/{restaurantId}")
    public String editRestaurantGet(Model model, @PathVariable(name = "restaurantId") String restaurantId) {
        Optional<RestaurantDto> optionalRestaurantDto = restaurantService.getRestaurantDtoById(restaurantId);
        if (optionalRestaurantDto.isEmpty()) {
            return "error";
        }
        RestaurantDto restaurantDto = optionalRestaurantDto.get();
        model.addAttribute("restaurantDto", restaurantDto);


        return "editRestaurant";
    }

    @PostMapping("/editRestaurant/{restaurantId}")

    public String editRestaurantPost(@ModelAttribute(name = "restaurantDto") @Valid RestaurantDto restaurantDto, BindingResult bindingResult,
                                     MultipartFile restaurantLogo, MultipartFile restaurantBackground, @RequestParam(name = "restaurantId") String restaurantId) {
        Optional<Restaurant> optionalRestaurantToBeUpdated = restaurantService.getRestaurantById(restaurantId);
        if (optionalRestaurantToBeUpdated.isEmpty()) {
            return "error";
        }
        Restaurant outDatedRestaurant = optionalRestaurantToBeUpdated.get();
        restaurantService.updateRestaurant(outDatedRestaurant, restaurantDto, restaurantLogo, restaurantBackground);
        return "redirect:/restaurant/" + restaurantId;
    }
}
