package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.OrderEntryDto;
import com.sda.QuickBite.entity.OrderEntry;
import com.sda.QuickBite.mapper.OrderEntryMapper;
import com.sda.QuickBite.repository.OrderEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderEntryService {

    @Autowired
    private OrderEntryMapper orderEntryMapper;

    @Autowired
    private OrderEntryRepository orderEntryRepository;
    public void addOrderEntry(OrderEntryDto orderEntryDto) {
        OrderEntry orderEntry = orderEntryMapper.map(orderEntryDto);
        orderEntryRepository.save(orderEntry);
    }
}
