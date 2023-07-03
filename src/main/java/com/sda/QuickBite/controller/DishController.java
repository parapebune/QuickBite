package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.dto.ErrorMessageDto;
import com.sda.QuickBite.dto.QuantityDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.SecurityCheck;
import com.sda.QuickBite.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class DishController extends DefaultController {
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
    @Autowired
    private SecurityCheck securityCheck;

    @GetMapping("/restaurant/{restaurantId}/addDish")
    public String addDishGet(Model model, @PathVariable(name = "restaurantId") String restaurantId){
        DishDto dishDto = new DishDto();
        model.addAttribute("dishDto",dishDto);
        return "addDish";
    }

    @PostMapping("/restaurant/{restaurantId}/addDish")
    public String addDishPost(@ModelAttribute(name = "dishDto") @Valid DishDto dishDto, BindingResult bindingResult,
                              @RequestParam("dishImage") MultipartFile dishImage,
                              @PathVariable(name = "restaurantId") String restaurantId,
                              Model model){
        if(bindingResult.hasErrors()){
            return "addDish";
        }


        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(restaurantId);
        if(optionalRestaurant.isEmpty()){
            util.getErrorMessage("Restaurant not found!", model);
            return "error";
        }

        Restaurant restaurant = optionalRestaurant.get();
        dishService.addDish(dishDto, dishImage, restaurant);
        return "redirect:/restaurant/" + restaurantId;
    }
    @GetMapping("/dish/{dishId}")
    public String dishGet(Model model, @PathVariable(name = "dishId") String dishId,
                          @RequestParam(name = "restaurantId", required = false) String restaurantId){
        model.addAttribute("restaurantId",restaurantId);
        Optional<DishDto> optionalDishDto = dishService.getDishDtoById(dishId);
        if(optionalDishDto.isEmpty()){
            util.getErrorMessage("Dish not found!", model);
            return "error";
        }
        DishDto dishDto = optionalDishDto.get();
        model.addAttribute("dishDto",dishDto);
        QuantityDto quantityDto = QuantityDto.builder()
                .quantity("1").build();

        model.addAttribute("quantityDto", quantityDto);
        return "dish";
    }

    @GetMapping("/dish/{dishId}/modify")
    public String modifyDishGet(@PathVariable(name = "dishId") String dishId, Model model){
        Optional<DishDto> optionalDishDto = dishService.getDishDtoById(dishId);
        if(optionalDishDto.isEmpty()){
            util.getErrorMessage("Dish not found!", model);
            return "error";
        }
        DishDto dishDto = optionalDishDto.get();
        model.addAttribute("dishDto",dishDto);
        return "modifyDish";
    }



    @PostMapping("/dish/{dishId}/update")
    public String updateDishPost(@ModelAttribute(name = "dishDto") DishDto dishDto, @PathVariable(name = "dishId") String dishId,
                                 @RequestParam(name = "dishImage",required = false) MultipartFile dishImage ){
        dishService.updateDish(dishDto, dishId, dishImage);
        return "redirect:/dish/" + dishId;
    }

    @GetMapping("/dish/{dishId}/remove")
    public String removeDishGet(@ModelAttribute(name = "dishId") String dishId,
                                @RequestParam(name = "restaurantId") String restaurantId){
        dishService.removeDishById(dishId);
        return "redirect:/restaurant/"+restaurantId;
    }

    @GetMapping("/editDish/{dishId}")
    public String editDishGet(Model model, @PathVariable(name = "dishId") String dishId) {
        System.out.println("Dish ID: " + dishId);

        Optional<DishDto> optionalDishDto = dishService.getDishDtoById(dishId);
        if (optionalDishDto.isEmpty()) {
            util.getErrorMessage("Dish Not Found", model);
            return "error";
        }
        DishDto dishDto = optionalDishDto.get();
        model.addAttribute("dishDto", dishDto);
        return "editDish";
    }

    @PostMapping("/editDish/{dishId}")
    public String editDishPost(@ModelAttribute(name = "dishDto") @Valid DishDto dishDto, BindingResult bindingResult,
                               @RequestParam("dishImage") MultipartFile dishImage,
                               @PathVariable(name = "dishId") String dishId, Model model) {
        System.out.println("Dish ID" + dishId);
        if (bindingResult.hasErrors()) {
            return "editDish";
        }

        Optional<Dish> optionalOutdatedDish = dishService.getDishById(dishId);
        if (optionalOutdatedDish.isEmpty()) {
            util.getErrorMessage("Dish Not Found", model);
            return "error";
        }
        Dish outdatedDish = optionalOutdatedDish.get();
        dishService.updateDish(outdatedDish, dishDto, dishImage);
        return "redirect:/dish/" + dishId;
    }




}
