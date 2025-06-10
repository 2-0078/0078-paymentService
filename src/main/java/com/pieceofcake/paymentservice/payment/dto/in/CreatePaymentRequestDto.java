package com.pieceofcake.paymentservice.payment.dto.in;

import com.pieceofcake.paymentservice.payment.entity.Payment;
import com.pieceofcake.paymentservice.payment.entity.enums.PaymentStatus;
import com.pieceofcake.paymentservice.payment.vo.in.CreatePaymentRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
@Slf4j
@Getter
@NoArgsConstructor
public class CreatePaymentRequestDto {
    private Long amount;
    private String orderName;
    private String memberUuid;

    @Builder
    public CreatePaymentRequestDto(Long amount, String orderName, String memberUuid) {
        this.amount = amount;
        this.orderName = orderName;
        this.memberUuid = memberUuid;
    }

    public static CreatePaymentRequestDto from(CreatePaymentRequestVo createPaymentRequestVo, String memberUuid) {
        return CreatePaymentRequestDto.builder()
                .amount(createPaymentRequestVo.getAmount())
                .orderName(createPaymentRequestVo.getOrderName())
                .memberUuid(memberUuid)
                .build();
    }

    public Payment toEntity() {
        return Payment.builder()
                .memberUuid(this.memberUuid)
                .paymentUuid(UUID.randomUUID().toString())
                .amount(this.amount)
                .status(PaymentStatus.READY)
                .orderName(this.orderName)
                .build();
    }
}
