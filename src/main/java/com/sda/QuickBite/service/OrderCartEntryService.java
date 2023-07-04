package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.OrderCartEntryDto;
import com.sda.QuickBite.dto.TotalAmountDto;
import com.sda.QuickBite.entity.FoodOrder;
import com.sda.QuickBite.entity.OrderCartEntry;
import com.sda.QuickBite.mapper.OrderCartEntryMapper;
import com.sda.QuickBite.repository.OrderCartEntryRepository;
import com.sda.QuickBite.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderCartEntryService {

    @Autowired
    private  OrderCartEntryRepository orderCartEntryRepository;
    @Autowired
    private  OrderCartEntryMapper orderCartEntryMapper;

    @Autowired
    private FoodOrderService foodOrderService;
    @Autowired
    private Util util;


    public List<OrderCartEntryDto> getOrderCartEntryList(String email) {
        List<OrderCartEntry> orderCartEntryList = orderCartEntryRepository.findByOrderCartUserEmail(email);
        List<OrderCartEntryDto> orderCartEntryDtoList = new ArrayList<>();
        for (OrderCartEntry orderCartEntry : orderCartEntryList){
            OrderCartEntryDto orderCartEntryDto = orderCartEntryMapper.map(orderCartEntry);
            orderCartEntryDtoList.add(orderCartEntryDto);
        }
        return orderCartEntryDtoList;
    }

    public TotalAmountDto getOrderTotalAmount(String email) {
        List<OrderCartEntry> orderCartEntryList = orderCartEntryRepository.findByOrderCartUserEmail(email);
        double totalAmount = util.calculateTotalAmount(orderCartEntryList);
        return TotalAmountDto.builder().totalAmount(String.valueOf(totalAmount)).build();
    }

    public List<OrderCartEntry> getOrderCartEntryListByOrderCartId(Long orderCartId) {
        return orderCartEntryRepository.findAllByOrderCartOrderCartId(orderCartId);
    }

    public void increaseQuantity(String orderCartEntryId) {
        Optional<OrderCartEntry> optionalOrderCartEntry = orderCartEntryRepository.findById(Long.valueOf(orderCartEntryId));
        if (optionalOrderCartEntry.isEmpty()){
            throw new RuntimeException("Dish Not Found");
        }
        OrderCartEntry orderCartEntry = optionalOrderCartEntry.get();
        orderCartEntry.setQuantity(orderCartEntry.getQuantity()+1);
        orderCartEntryRepository.save(orderCartEntry);
    }

    public void decreaseQuantity(String orderCartEntryId) {
        Optional<OrderCartEntry> optionalOrderCartEntry = orderCartEntryRepository.findById(Long.valueOf(orderCartEntryId));
        if (optionalOrderCartEntry.isEmpty()){
            throw new RuntimeException("Dish Not Found");
        }
        OrderCartEntry orderCartEntry = optionalOrderCartEntry.get();
        if (orderCartEntry.getQuantity() > 1){
            orderCartEntry.setQuantity(orderCartEntry.getQuantity()-1);
            orderCartEntryRepository.save(orderCartEntry);
        }
    }

    public void removeOrderCartEntry(String orderCartEntryId) {
        Optional<OrderCartEntry> optionalOrderCartEntry = orderCartEntryRepository.findById(Long.valueOf(orderCartEntryId));
        if (optionalOrderCartEntry.isEmpty()){
            throw new RuntimeException("Dish Not Found");
        }
        OrderCartEntry orderCartEntry = optionalOrderCartEntry.get();
        orderCartEntryRepository.deleteById(orderCartEntry.getOrderCartEntryId());
    }

    public List<OrderCartEntryDto> getAllOrderCartEntryDtoByFoodOrderId(String foodOrderId) {
        Optional<FoodOrder> optionalFoodOrder = foodOrderService.getFoodOrderById(foodOrderId);
        if(optionalFoodOrder.isEmpty()){
            return new ArrayList<>();
        }
        FoodOrder foodOrder = optionalFoodOrder.get();

        List<OrderCartEntry> orderCartEntryList = orderCartEntryRepository.findAllByOrderCartOrderCartId(foodOrder.getOrderCart().getOrderCartId());
        List<OrderCartEntryDto> orderCartEntryDtoList = new ArrayList<>();
        for (OrderCartEntry orderCartEntry : orderCartEntryList){
            OrderCartEntryDto orderCartEntryDto = orderCartEntryMapper.map(orderCartEntry);
            orderCartEntryDtoList.add(orderCartEntryDto);
        }
        return orderCartEntryDtoList;
    }
}
