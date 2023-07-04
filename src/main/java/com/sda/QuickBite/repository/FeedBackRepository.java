package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends CrudRepository<Feedback, Long> {
}
