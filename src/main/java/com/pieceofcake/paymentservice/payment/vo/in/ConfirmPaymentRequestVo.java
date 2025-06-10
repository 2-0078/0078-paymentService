package com.pieceofcake.paymentservice.payment.vo.in;

import lombok.Getter;

@Getter
public class ConfirmPaymentRequestVo {
    private String paymentType;
    private String orderId;
    private String paymentKey;
    private Long amount;
}
