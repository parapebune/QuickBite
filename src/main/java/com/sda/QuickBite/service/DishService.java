package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.DishCategoryDto;
import com.sda.QuickBite.dto.DishDto;
import com.sda.QuickBite.dto.OrderCartEntryDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.FoodOrder;
import com.sda.QuickBite.entity.OrderCartEntry;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.enums.DishCategory;
import com.sda.QuickBite.mapper.DishMapper;
import com.sda.QuickBite.mapper.FoodOrderMapper;
import com.sda.QuickBite.mapper.OrderCartEntryMapper;
import com.sda.QuickBite.repository.DishRepository;
import com.sda.QuickBite.repository.OrderCartRepository;
import com.sda.QuickBite.repository.RestaurantRepository;
import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private RestaurantService restaurantService;



    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private Util util;
    @Autowired
    private OrderCartRepository orderCartRepository;
    @Autowired
    private FoodOrderService foodOrderService;
    @Autowired
    private OrderCartEntryService orderCartEntryService;
    @Autowired
    private FoodOrderMapper foodOrderMapper;
    @Autowired
    private OrderCartEntryMapper orderCartEntryMapper;

    public void addDish(DishDto dishDto, MultipartFile dishImage, Restaurant restaurant) {
        Dish dish = dishMapper.map(dishDto, dishImage, restaurant);
        dishRepository.save(dish);



    }
    public List<DishCategoryDto> getDishDtoListGroupByCategory(String restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(restaurantId);
        if(optionalRestaurant.isEmpty()){
            return new ArrayList<>();
        }
        Restaurant restaurant = optionalRestaurant.get();
        List<Dish> dishList = restaurant.getMenu();
        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish dish : dishList){
            DishDto dishDto = dishMapper.map(dish);
            dishDtoList.add(dishDto);
        }

        List<DishCategoryDto> dishCategoryDtoList = new ArrayList<>();

        for (DishCategory dishCategory : DishCategory.values()){
            DishCategoryDto dishCategoryDto  = DishCategoryDto.builder()
                    .categoryName(dishCategory.name())
                    .dishDtoList(new ArrayList<>())
                    .build();
            for (DishDto dishDto : dishDtoList){
                if(dishCategory.name().equals(dishDto.getCategory())){
                    dishCategoryDto.getDishDtoList().add(dishDto);
                }
            }
            if(dishCategoryDto.getDishDtoList().size() > 0){
                dishCategoryDtoList.add(dishCategoryDto);
            }
        }
        return dishCategoryDtoList;
    }

    public Optional<DishDto> getDishDtoById(String dishId) {
        Optional<Dish> optionalDish = dishRepository.findById(Long.valueOf(dishId));
        if(optionalDish.isEmpty()){
            return Optional.empty();
        }
        Dish dish = optionalDish.get();
        DishDto dishDto = dishMapper.map(dish);
        return Optional.of(dishDto);


    }
    public void updateDish(Dish outdatedDish, DishDto dishDto, MultipartFile dishImage) {
        Dish dishToBeSaved = dishMapper.map(dishDto, dishImage);
        dishToBeSaved.setDishId(outdatedDish.getDishId());
        dishToBeSaved.setRestaurant(outdatedDish.getRestaurant());
        dishRepository.save(dishToBeSaved);
    }

//    public List<OrderCartEntryDto> getAllDishDtoByFoodOrderId(String foodOrderId) {
//        Optional<FoodOrder> optionalFoodOrder = foodOrderService.getFoodOrderById(foodOrderId);
//        if(optionalFoodOrder.isEmpty()){
//            return new ArrayList<>();
//        }
//        FoodOrder foodOrder = optionalFoodOrder.get();
//
//        List<OrderCartEntry> orderCartEntryList = orderCartEntryService.getOrderCartEntryListByOrderCartId(foodOrder.getOrderCart().getOrderCartId());
//        List<OrderCartEntryDto> orderCartEntryDtoList = new ArrayList<>();
//        for (OrderCartEntry orderCartEntry : orderCartEntryList){
//            OrderCartEntryDto orderCartEntryDto = orderCartEntryMapper.map(orderCartEntry);
//            orderCartEntryDtoList.add(orderCartEntryDto);
//        }
//        return orderCartEntryDtoList;
//    }
    public void updateDish(DishDto dishDto, String dishId, MultipartFile dishImage) {
        Optional<Dish> optionalDish = dishRepository.findById(Long.valueOf(dishId));
        if(optionalDish.isEmpty()){
            throw new RuntimeException("Dish not found");
        }
        Dish dish = optionalDish.get();
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setCookingTime(Integer.valueOf(dishDto.getCookingTime()));
        dish.setCategory(DishCategory.valueOf(dishDto.getCategory()));
        dish.setPrice(Double.valueOf(dishDto.getPrice()));
        if(!dishImage.isEmpty()){
            dish.setImage(util.convertToBytes(dishImage));
        }
        dishRepository.save(dish);
    }

    public void removeDishById(String dishId) {
        dishRepository.deleteById(Long.valueOf(dishId));
    }

    public Optional<Dish> getDishById(String dishId) {
        return dishRepository.findById(Long.valueOf(dishId));
    }
}
