package com.salon.payment.payload.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {

    private String payment_Link_url;
    private String getPayment_Link_id;
}
