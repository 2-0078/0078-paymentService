package com.pieceofcake.paymentservice.payment.dto.in;

import com.pieceofcake.paymentservice.payment.vo.in.ConfirmPaymentRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmPaymentRequestDto {
    private String paymentType;
    private String orderId;
    private String paymentKey;
    private Long amount;

    @Builder
    public ConfirmPaymentRequestDto(String paymentType, String orderId, String paymentKey, Long amount) {
        this.paymentType = paymentType;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.amount = amount;
    }

    public static ConfirmPaymentRequestDto from(ConfirmPaymentRequestVo confirmPaymentRequestVo) {
        return ConfirmPaymentRequestDto.builder()
                .paymentType(confirmPaymentRequestVo.getPaymentType())
                .orderId(confirmPaymentRequestVo.getOrderId())
                .paymentKey(confirmPaymentRequestVo.getPaymentKey())
                .amount(confirmPaymentRequestVo.getAmount())
                .build();
    }
}
