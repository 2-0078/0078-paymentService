package com.pieceofcake.paymentservice.payment.dto.out;

import com.pieceofcake.paymentservice.payment.entity.Payment;
import com.pieceofcake.paymentservice.payment.vo.out.CreatePaymentResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePaymentResponseDto {
    private String orderId;
    private String customerKey;
    private Long amount;

    @Builder
    public CreatePaymentResponseDto(String orderId, String customerKey, Long amount) {
        this.orderId = orderId;
        this.customerKey = customerKey;
        this.amount = amount;
    }

    public static CreatePaymentResponseDto from(Payment payment, String customerKey) {
        return CreatePaymentResponseDto.builder()
                .orderId(payment.getPaymentUuid())
                .customerKey(customerKey)
                .amount(payment.getAmount())
                .build();
    }

    public CreatePaymentResponseVo toVo() {
        return CreatePaymentResponseVo.builder()
                .orderId(orderId)
                .customerKey(customerKey)
                .amount(amount)
                .build();
    }
}
