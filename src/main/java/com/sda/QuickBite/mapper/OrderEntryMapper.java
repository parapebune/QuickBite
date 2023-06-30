package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.OrderEntryDto;
import com.sda.QuickBite.entity.OrderEntry;
import org.springframework.stereotype.Component;

@Component
public class OrderEntryMapper {
    public OrderEntry map(OrderEntryDto orderEntryDto) {
        OrderEntry orderEntry = OrderEntry.builder()
                .dish(orderEntryDto.getDish())
                .quantity(orderEntryDto.getQuantity())
                .orderCart(orderEntryDto.getOrderCart())
                .build();
        return orderEntry;
    }

    public OrderEntryDto map(OrderEntry orderEntry) {
        OrderEntryDto orderEntryDto = OrderEntryDto.builder()
                .id(orderEntry.getId())
                .
    }
}
