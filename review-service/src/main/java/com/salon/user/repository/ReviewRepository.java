package com.salon.user.repository;

import com.salon.user.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySalonId(Long salonId);
}
