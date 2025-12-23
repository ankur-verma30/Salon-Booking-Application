package com.salon.user.messaging;

import com.salon.user.entity.PaymentOrder;
import com.salon.user.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingService bookingService;

    @RabbitListener(queues = "booking-queue")
    public void bookingUpdateListener(PaymentOrder paymentOrder) throws Exception {
        bookingService.bookingSucess(paymentOrder);
    }
}
