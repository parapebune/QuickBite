package com.sda.QuickBite.service;

import com.sda.QuickBite.dto.FeedbackDto;
import com.sda.QuickBite.entity.Feedback;
import com.sda.QuickBite.entity.Restaurant;
import com.sda.QuickBite.entity.User;
import com.sda.QuickBite.mapper.FeedbackMapper;
import com.sda.QuickBite.repository.FeedbackRepository;
import com.sda.QuickBite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    public void addFeedback(FeedbackDto feedbackDto, Restaurant restaurant, User user){
        Feedback feedback = feedbackMapper.map(feedbackDto, restaurant, user);
        feedbackRepository.save(feedback);
    }





}
