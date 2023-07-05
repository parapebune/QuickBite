package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.FoodOrderDto;
import com.sda.QuickBite.entity.Feedback;
import com.sda.QuickBite.entity.FoodOrder;
import com.sda.QuickBite.entity.OrderCartEntry;
import com.sda.QuickBite.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class FoodOrderMapper {

    @Autowired
    private FeedbackService feedbackService;

    public FoodOrderDto map(FoodOrder foodOrder) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return FoodOrderDto.builder()
                .foodOrderId(foodOrder.getOrderId().toString())
                .date(foodOrder.getOrderDate().format(formatter))
                .restaurantName(foodOrder.getRestaurant().getName())
                .restaurantId(foodOrder.getRestaurant().getRestaurantId().toString())
                .totalAmount(foodOrder.getTotalAmount().toString())
                .status(foodOrder.getOrderStatus().getLabel())
                .rated(feedbackService.reviewPermission(foodOrder.getOrderStatus()))
                .build();
    }

}
