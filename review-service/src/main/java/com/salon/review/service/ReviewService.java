package com.salon.review.service;

import com.salon.review.dto.ReviewRequest;
import com.salon.review.dto.SalonDTO;
import com.salon.review.dto.UserDTO;
import com.salon.review.entity.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest req, UserDTO user, SalonDTO salon);
    List<Review> getReviewsBySalonId(Long salonId);
    Review updateReview(ReviewRequest req,Long reviewId, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;

}
