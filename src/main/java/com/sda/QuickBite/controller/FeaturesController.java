package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.*;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.repository.DishRepository;
import com.sda.QuickBite.repository.RestaurantRepository;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FeaturesController extends DefaultController {
    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    private OrderCartEntryService orderCartEntryService;
    @Autowired
    private FoodOrderService foodOrderService;

    @PostMapping("/addToCard/{dishId}")
    public String addToCardPost(@PathVariable(name = "dishId") String dishId, @ModelAttribute(name = "quantityDto") QuantityDto quantityDto,
                                Authentication authentication, Model model){
        if(authentication == null){
            return "login";
        }
        Optional<Dish> optionalDish = dishService.getDishById(dishId);
        if(optionalDish.isEmpty()){
            util.getErrorMessage("Dish Not Found", model);
            return "error";
        }
        Dish dish = optionalDish.get();
        if(!orderCartService.isDishFromSameRestaurant(dish, authentication.getName())){
            util.getErrorMessage("Dish Not Found", model);
            return "error";
        }
        orderCartService.addToCart(dish, quantityDto, authentication.getName());
        return "redirect:/restaurant/" + dish.getRestaurant().getRestaurantId().toString();
//        + dishId +"?restaurantId=" + ;

    }

    @GetMapping("/orderHistory")
    public String orderHistoryGet(Authentication authentication, Model model){
        List<FoodOrderDto> foodOrderDtoList = foodOrderService.getAllFoodOrdersDtoByEmail(authentication.getName());
        model.addAttribute("foodOrderDtoList",foodOrderDtoList);
        return "orderHistory";}

    @GetMapping("/sendFoodOrder")
    public String sendOrderPost(Authentication authentication){
        foodOrderService.sendFoodOrder(authentication.getName());
        return "redirect:/home";
    }

    @GetMapping("/orderHistory/foodOrder/{foodOrderId}")
    public String foodOrderDetailsGet(@PathVariable(name = "foodOrderId") String foodOrderId, Model model){
        Optional<FoodOrderDto> optionalFoodOrderDto = foodOrderService.getFoodOrderDtoById(foodOrderId);
        if(optionalFoodOrderDto.isEmpty()){
            util.getErrorMessage("Food Order Not Found", model);
            return "error";
        }
        FoodOrderDto foodOrderDto = optionalFoodOrderDto.get();
        List<OrderCartEntryDto> orderCartEntryDtoList = orderCartEntryService.getAllOrderCartEntryDtoByFoodOrderId(foodOrderId);

        model.addAttribute("foodOrderDto",foodOrderDto);
        model.addAttribute("orderCartEntryDtoList", orderCartEntryDtoList);

        return "viewOrder";
    }

    @GetMapping("/orderCart")
    public String orderCartGet(Model model, Authentication authentication ) {
        List<OrderCartEntryDto> orderCartEntryDtoList = orderCartEntryService.getOrderCartEntryList(authentication.getName());
        TotalAmountDto orderTotalAmountDto = orderCartEntryService.getOrderTotalAmount(authentication.getName());
        model.addAttribute("orderTotalAmount", orderTotalAmountDto);
        model.addAttribute("orderCartEntryDtoList", orderCartEntryDtoList);
        if (orderCartEntryDtoList.size() != 0) {
            OrderCartEntryDto orderCartEntryDto = orderCartEntryDtoList.get(0);
            String dishId = orderCartEntryDto.getDishDto().getId();
            Optional<Dish> optionalDish = dishRepository.findDishByDishId(Long.valueOf(dishId));
            if (optionalDish.isEmpty()) {
                return "orderCart";
            }
            Dish dish = optionalDish.get();
            model.addAttribute("restaurantId", dish.getRestaurant().getRestaurantId().toString());
        }
        return "orderCart";
    }

    @GetMapping("/orderDashboard")
    public String orderDashboardGet(Model model, Authentication authentication,
                                    @RequestParam(name = "restaurantId",required = false) String restaurantId){
        Optional<User> optionalUser = userService.getUserByEmail(authentication.getName());
        if(optionalUser.isEmpty()){
            return "home";
        }
        User user = optionalUser.get();
        List<FullOrderDto> fullOrderDtoList = foodOrderService.getAllFullFoodOrdersByUser(user);
        model.addAttribute("fullOrderDtoList",fullOrderDtoList);
        StatusDto statusDto = new StatusDto();
        model.addAttribute("statusDto",statusDto);
        return "orderDashboard";
    }


    @GetMapping("/orderCart/increase/{orderCartEntryId}")
    public String increaseGet(@PathVariable(name = "orderCartEntryId") String orderCartEntryId){
        orderCartEntryService.increaseQuantity(orderCartEntryId);
        return "redirect:/orderCart";
    }

    @GetMapping("/orderCart/decrease/{orderCartEntryId}")
    public String decreaseGet(@PathVariable(name = "orderCartEntryId") String orderCartEntryId){
        orderCartEntryService.decreaseQuantity(orderCartEntryId);
        return "redirect:/orderCart";
    }

    @GetMapping("/cartEntry/remove/{orderCartEntryId}")
    public String removeGet(@PathVariable(name = "orderCartEntryId") String orderCartEntryId){
        orderCartEntryService.removeOrderCartEntry(orderCartEntryId);
        return "redirect:/orderCart";
    }

    @PostMapping("/food-order/{foodOrderId}/change-status")
    public String changeStatusPost(@ModelAttribute(name = "statusDto") StatusDto statusDto,
                                   @PathVariable(name = "foodOrderId") String foodOrderId){
        foodOrderService.changeStatus(foodOrderId,statusDto);
        return "redirect:/orderDashboard";
    }


}
