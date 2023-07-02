package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.OrderCartDto;
import com.sda.QuickBite.dto.OrderEntryDto;
import com.sda.QuickBite.entity.OrderCart;
import com.sda.QuickBite.entity.OrderEntry;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.mapper.OrderEntryMapper;
import com.sda.QuickBite.repository.OrderCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderCartService {

    @Autowired
    private OrderCartRepository orderCartRepository;

    @Autowired
    private OrderEntryMapper orderEntryMapper;


}
