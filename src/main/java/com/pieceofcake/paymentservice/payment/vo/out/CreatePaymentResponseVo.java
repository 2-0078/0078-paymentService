package com.pieceofcake.paymentservice.payment.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePaymentResponseVo {
    private String orderId;
    private String customerKey;
    private Long amount;

    @Builder
    public CreatePaymentResponseVo(
            String orderId,
            String customerKey,
            Long amount
    ) {
        this.orderId = orderId;
        this.customerKey = customerKey;
        this.amount = amount;
    }
}
