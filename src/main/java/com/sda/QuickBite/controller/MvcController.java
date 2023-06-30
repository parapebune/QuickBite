package com.sda.QuickBite.controller;

import com.sda.QuickBite.dto.*;
import com.sda.QuickBite.entity.*;
import com.sda.QuickBite.enums.RestaurantSpecific;
import com.sda.QuickBite.service.*;
import com.sda.QuickBite.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private OrderEntryService orderEntryService;
    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    private OrderCartEntryService orderCartEntryService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @ModelAttribute("fullName")
    public String fullName(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return util.displayAuthenticatedUserFullName(authentication);
    }


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

//    @GetMapping("/navBar")
//    public String navBarGet(Model model){
//        return "fragments/navBar";
//    }

    @GetMapping("/navBar")
    public String navBarGet(Model model) {

        return "fragments/navBar";
    }

    @GetMapping("/registration")
    public String registrationGet(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPost(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userDto.getPassword().equals(userDto.getPasswordRetype())) {
            bindingResult.rejectValue("passwordRetype", "error.userDto",
                    "Passwords do not match! Please try again!");
        }

        if (bindingResult.hasErrors()) {
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
    public String addRestaurantGet(Model model) {
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurantDto", restaurantDto);
        return "addRestaurant";
    }

    @PostMapping("/addRestaurant")

    public String addRestaurantPost(@ModelAttribute(name = "restaurantDto") @Valid RestaurantDto restaurantDto, BindingResult bindingResult,
                                    MultipartFile restaurantLogo, MultipartFile restaurantBackground, Authentication authentication) {

        User user = userService.getAuthenticatedUser(authentication);
        restaurantService.addRestaurant(restaurantDto, restaurantLogo, restaurantBackground, user);
        return "redirect:/sellerPage";
    }


    @GetMapping("/restaurant/{restaurantId}/addDish")
    public String addDishGet(Model model, @PathVariable(name = "restaurantId") String restaurantId) {
        DishDto dishDto = new DishDto();
        model.addAttribute("dishDto", dishDto);
        return "addDish";
    }

    @PostMapping("/restaurant/{restaurantId}/addDish")
    public String addDishPost(@ModelAttribute(name = "dishDto") @Valid DishDto dishDto, BindingResult bindingResult,
                              @RequestParam("dishImage") MultipartFile dishImage,
                              @PathVariable(name = "restaurantId") String restaurantId) {
        if (bindingResult.hasErrors()) {
            return "addDish";
        }
        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return "error";
        }
        Restaurant restaurant = optionalRestaurant.get();
        dishService.addDish(dishDto, dishImage, restaurant);
        return "redirect:/restaurantPage/" + restaurantId;
    }


    @GetMapping("/dish/{dishId}")
    public String dishGet(Model model, @PathVariable(name = "dishId") String dishId) {

        Optional<DishDto> optionalDishDto = dishService.getDishDtoById(dishId);
        if (optionalDishDto.isEmpty()) {
            return "error";
        }
        DishDto dishDto = optionalDishDto.get();
        model.addAttribute("dishDto", dishDto);
        QuantityDto quantityDto = QuantityDto.builder()
                .quantity("1").build();
        model.addAttribute("quantityDto", quantityDto);
        return "dish";
    }

    @GetMapping("/restaurantPage/{restaurantId}")
    public String restaurantPageGet(@PathVariable(value = "restaurantId") String restaurantId, Model model) {
        Optional<RestaurantDto> optionalRestaurantDto = restaurantService.getRestaurantDtoById(restaurantId);
        if (optionalRestaurantDto.isEmpty()) {
            return "error";
        }
        RestaurantDto restaurantDto = optionalRestaurantDto.get();
        model.addAttribute("restaurantDto", restaurantDto);

        List<DishCategoryDto> dishCategoryDtoList = dishService.getDishDtoListGroupByCategory(restaurantId);
        model.addAttribute("dishCategoryDtoList", dishCategoryDtoList);

        return "restaurantPage";
    }


    @PostMapping("/addToCard/{dishId}")

    public String addToCardPost(@PathVariable(name = "dishId") String dishId, @ModelAttribute(name = "quantityDto") QuantityDto quantityDto,
                                Authentication authentication){
        if(authentication == null){
            return "login";
        }
        orderCartService.addToCart(dishId, quantityDto, authentication.getName());
        return "redirect:/dish/" + dishId;

    }

    @GetMapping("/dish")
    public String dishGet() {
        return "dish";
    }

    @GetMapping("/orderHistory")
    public String orderHistoryGet() {
        return "orderHistory";
    }


    @GetMapping("/orderCart")
    public String shoppingCartGet(Model model, Authentication authentication){
        List<OrderCartEntryDto> orderCartEntryDtoList = orderCartEntryService.getOrderCartEntryList(authentication.getName());
        model.addAttribute("orderCartEntryDtoList",orderCartEntryDtoList);
        return "orderCart";}
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
    @GetMapping("/yourProfile")
    public String yourProfileGet(Model model, Authentication authentication) {
        UserProfileDto userProfileDto = userService.getAuthenticatedUserProfileDto(authentication);
        model.addAttribute("userProfileDto", userProfileDto);
        return "yourProfile";
    }

    @PostMapping("/yourProfile")
    public String yourProfilePost(@ModelAttribute(name = "userProfileDto") @Valid UserProfileDto userProfileDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "yourProfile";
        }
        Optional<User> optionalUser = userService.getUserByEmail(userProfileDto.getEmail());
        if (optionalUser.isEmpty()) {
            return "error";
        }
        User user = optionalUser.get();
        userService.updateUser(user, userProfileDto);
        return "redirect:/yourProfile";
    }

    @GetMapping("/changePassword")
    public String changePasswordGet(Model model, Authentication authentication) {

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        model.addAttribute("changePasswordDto", changePasswordDto);


        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassWordPost(@ModelAttribute(name = "changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
                                     BindingResult bindingResult, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String oldPasswordInputByUser = changePasswordDto.getOldPassword();
        String oldPasswordFromDatabase = user.getPassword();

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordRetype())) {
            bindingResult.rejectValue("newPasswordRetype", "error.changePasswordDto",
                    "New Passwords do not match! Please try again!");
        }

        if (bindingResult.hasErrors()) {
            return "changePassword";
        }

        if (!bCryptPasswordEncoder.matches(oldPasswordInputByUser, oldPasswordFromDatabase)) {
            bindingResult.rejectValue("oldPassword", "error.changePasswordDto", "Incorrect old password");
        }

        if (bindingResult.hasErrors()) {
            return "changePassword";
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
        userService.updateUserPassword(user);
        return "redirect:/login";
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
        return "redirect:/restaurantPage/" + restaurantId;
    }

    @GetMapping("/editDish/{dishId}")
    public String editDishGet(Model model, @PathVariable(name = "dishId") String dishId) {
        System.out.println("Dish ID: " + dishId);

        Optional<DishDto> optionalDishDto = dishService.getDishDtoById(dishId);
        if (optionalDishDto.isEmpty()) {
            return "error";
        }
        DishDto dishDto = optionalDishDto.get();
        model.addAttribute("dishDto", dishDto);
        return "editDish";
    }

    @PostMapping("/editDish/{dishId}")
    public String editDishPost(@ModelAttribute(name = "dishDto") @Valid DishDto dishDto, BindingResult bindingResult,
                               @RequestParam("dishImage") MultipartFile dishImage,
                               @PathVariable(name = "dishId") String dishId) {
        System.out.println("Dish ID" + dishId);
        if (bindingResult.hasErrors()) {
            return "editDish";
        }

        Optional<Dish> optionalOutdatedDish = dishService.getDishById(dishId);
        if (optionalOutdatedDish.isEmpty()) {
            return "error";
        }
        Dish outdatedDish = optionalOutdatedDish.get();
        dishService.updateDish(outdatedDish, dishDto, dishImage);
        return "redirect:/dish/" + dishId;
    }
}
