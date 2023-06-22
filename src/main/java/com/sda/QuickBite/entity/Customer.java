package com.sda.QuickBite.entity;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    private String deliveryAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private OrderCart orderCart;

}
