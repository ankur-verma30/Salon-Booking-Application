package com.yoho.service;

import com.yoho.dto.ReviewRequest;
import com.yoho.dto.SalonDTO;
import com.yoho.dto.UserDTO;
import com.yoho.model.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest req, UserDTO user, SalonDTO salon);
    List<Review> getReviewsBySalonId(Long salonId);
    Review updateReview(ReviewRequest req,Long reviewId, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;

}
