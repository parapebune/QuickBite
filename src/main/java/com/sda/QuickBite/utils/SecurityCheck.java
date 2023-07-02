package com.sda.QuickBite.utils;

import com.sda.QuickBite.repository.DishRepository;
import com.sda.QuickBite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityCheck {

    @Autowired
    private UserService userService;
    @Autowired
    private DishRepository dishRepository;

//    public Boolean checkBeforeUpdate(Authentication authentication, Class<?> classToBeCheck , String classId){
//        Optional<Long> optionalUserId = userService.getUserIdByEmail(authentication.getName());
//        if(optionalUserId.isEmpty()){
//            return false;
//        }
//        Long userId = optionalUserId.get();
//        if(Dish.class.equals(classToBeCheck)){
//            List<Dish> dishList = dishRepository.findAllByRestaurantUserUserId(userId);
//            if(dishList.size() > 0){
//
//            }
//        }else {
//            if(Restaurant.class.equals(classToBeCheck)){
//                List<Restaurant>
//            }
//        }
//
//    }
}
