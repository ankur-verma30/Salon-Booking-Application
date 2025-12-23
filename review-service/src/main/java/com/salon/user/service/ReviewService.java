package com.salon.user.service;

import com.salon.user.dto.ReviewRequest;
import com.salon.user.dto.SalonDTO;
import com.salon.user.dto.UserDTO;
import com.salon.user.entity.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest req, UserDTO user, SalonDTO salon);
    List<Review> getReviewsBySalonId(Long salonId);
    Review updateReview(ReviewRequest req,Long reviewId, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;

}
