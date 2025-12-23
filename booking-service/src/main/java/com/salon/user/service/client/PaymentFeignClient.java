package com.salon.user.service.client;

import com.salon.user.domain.PaymentMethod;
import com.salon.user.dto.BookingDTO;
import com.salon.user.dto.PaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentFeignClient {

    @PostMapping("api/payments/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod,
                                                                 @RequestHeader("Authorization") String jwt);

}
