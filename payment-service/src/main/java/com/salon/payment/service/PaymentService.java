package com.salon.payment.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.salon.payment.domain.PaymentMethod;

import com.salon.payment.entity.PaymentOrder;
import com.salon.payment.payload.response.PaymentLinkResponse;
import com.salon.payment.payload.response.dto.BookingDTO;
import com.salon.payment.payload.response.dto.UserDTO;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;
}
