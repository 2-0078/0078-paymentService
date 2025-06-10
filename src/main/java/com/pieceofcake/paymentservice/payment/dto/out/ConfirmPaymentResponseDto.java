package com.pieceofcake.paymentservice.payment.dto.out;

import com.pieceofcake.paymentservice.payment.vo.out.ConfirmPaymentResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ConfirmPaymentResponseDto {
    private String paymentKey;
    private String orderId;
    private Long amount;
    private String status;
    private String method;
    private String approvedAt;

    @Builder
    public ConfirmPaymentResponseDto(String paymentKey, String orderId, Long amount, String status, String method, String approvedAt) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.approvedAt = approvedAt;
    }

    public ConfirmPaymentResponseVo toVo() {
        return ConfirmPaymentResponseVo.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(amount)
                .status(status)
                .method(method)
                .approvedAt(approvedAt)
                .build();
    }
}
