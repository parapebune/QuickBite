
package com.sda.QuickBite.dto;

import com.sda.QuickBite.entity.OrderEntry;
import com.sda.QuickBite.entity.User;
import jakarta.persistence.*;

import javax.lang.model.element.NestingKind;
import java.util.List;

public class OrderCartDto {

    private String id;
    private String user;
    private List<OrderEntryDto> orderEntryDtoList;


}

