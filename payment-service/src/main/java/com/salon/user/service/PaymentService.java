package com.salon.user.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.salon.user.domain.PaymentMethod;

import com.salon.user.entity.PaymentOrder;
import com.salon.user.payload.response.PaymentLinkResponse;
import com.salon.user.payload.response.dto.BookingDTO;
import com.salon.user.payload.response.dto.UserDTO;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;
}
