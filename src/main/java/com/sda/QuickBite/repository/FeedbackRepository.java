package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Feedback;
import com.sda.QuickBite.entity.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
    List<Feedback> findAllByRestaurant(Restaurant restaurant);

    List<Feedback> findAllByRestaurantRestaurantId(Long restaurantId);
}
