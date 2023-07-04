
package com.sda.QuickBite.mapper;

import com.sda.QuickBite.dto.FeedbackDto;
import com.sda.QuickBite.entity.Feedback;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public Feedback map(FeedbackDto feedbackDto, Restaurant restaurant, User user) {
        return Feedback.builder()
                .rating(Integer.valueOf(feedbackDto.getRating()))
                .review(feedbackDto.getReview())
                .restaurant(restaurant)
                .user(user).build();

    }
}

