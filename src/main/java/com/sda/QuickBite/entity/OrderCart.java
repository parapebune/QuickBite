package com.sda.QuickBite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class OrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "orderCart")
    private Customer customer;

    @OneToMany(mappedBy = "orderCart")
    private List<OrderEntry> orderEntryList;



}
