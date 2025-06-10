package com.pieceofcake.paymentservice.payment.vo.in;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreatePaymentRequestVo {
    private Long amount;
    private String orderName;
}
