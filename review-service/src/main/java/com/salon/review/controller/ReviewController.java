package com.salon.review.controller;

import com.salon.review.dto.ApiResponse;
import com.salon.review.dto.ReviewRequest;
import com.salon.review.dto.SalonDTO;
import com.salon.review.dto.UserDTO;
import com.salon.review.entity.Review;
import com.salon.review.service.ReviewService;
import com.salon.review.service.client.SalonFeignClient;
import com.salon.review.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
                                               @PathVariable Long salonId,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(req,user,salon);

        return ResponseEntity.ok(review);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Review>> getReviewsBySalonId(@PathVariable Long salonId,
                                               @RequestHeader("Authorization") String jwt) throws Exception {

        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        List<Review> review = reviewService.getReviewsBySalonId(salon.getId());

        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                                     @RequestBody ReviewRequest req,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        Review review = reviewService.updateReview(req,reviewId,user.getId());

        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        reviewService.deleteReview(reviewId,user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review Deleted");
        return ResponseEntity.ok(apiResponse);
    }
}
