package com.sda.QuickBite.service;


import com.sda.QuickBite.dto.QuantityDto;
import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.OrderCart;
import com.sda.QuickBite.entity.OrderCartEntry;
import com.sda.QuickBite.repository.DishRepository;
import com.sda.QuickBite.repository.OrderCartRepository;
import com.sda.QuickBite.repository.OrderCartEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCartService {
    @Autowired
    private OrderCartRepository orderCartRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderCartEntryRepository orderCartEntryRepository;


    public void addToCart(Dish dish, QuantityDto quantityDto, String name) {


        Optional<OrderCart> optionalOrderCart = orderCartRepository.findByUserEmail(name);

        if(optionalOrderCart.isEmpty()){
            throw new RuntimeException("No shipping cart for user with email " + name);
        }
        OrderCart orderCart = optionalOrderCart.get();
        OrderCartEntry orderCartEntry = OrderCartEntry.builder()
                .dish(dish)
                .quantity(Integer.valueOf( quantityDto.getQuantity()))
                .orderCart(orderCart)
                .build();
        orderCartEntryRepository.save(orderCartEntry);
    }


}
