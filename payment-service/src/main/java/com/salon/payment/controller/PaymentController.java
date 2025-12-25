package com.salon.payment.controller;

import com.razorpay.RazorpayException;
import com.salon.payment.domain.PaymentMethod;
import com.salon.payment.entity.PaymentOrder;
import com.salon.payment.payload.response.PaymentLinkResponse;
import com.salon.payment.payload.response.dto.BookingDTO;
import com.salon.payment.payload.response.dto.UserDTO;
import com.salon.payment.service.PaymentService;
import com.salon.payment.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;


    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod,
                                                                 @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);
        return ResponseEntity.ok(res);

    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {

        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(res);

    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> processPaymentOrder(@RequestParam String paymentId, @RequestParam String paymentLinkId) throws RazorpayException {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);
        Boolean  res = paymentService.proceedPayment(paymentOrder, paymentId, paymentLinkId);

        return ResponseEntity.ok(res);
    }

}
