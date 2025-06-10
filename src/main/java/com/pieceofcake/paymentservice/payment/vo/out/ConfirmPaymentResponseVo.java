package com.pieceofcake.paymentservice.payment.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmPaymentResponseVo {
    private String paymentKey;
    private String orderId;
    private Long amount;
    private String status;
    private String method;
    private String approvedAt;

    @Builder
    public ConfirmPaymentResponseVo(
            String paymentKey,
            String orderId,
            Long amount,
            String status,
            String method,
            String approvedAt
    ) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.approvedAt = approvedAt;
    }
}
