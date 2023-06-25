//package com.sda.QuickBite.entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class RestaurantMenu {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(mappedBy = "restaurantMenu")
//    private Restaurant restaurant;
//
//    @OneToMany(mappedBy = "restaurantMenu")
//    private List<Dish> restaurantDishes;
//}
