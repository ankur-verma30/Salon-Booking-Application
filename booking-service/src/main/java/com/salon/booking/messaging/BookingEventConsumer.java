package com.salon.booking.messaging;

import com.salon.booking.entity.PaymentOrder;
import com.salon.booking.service.BookingService;
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
