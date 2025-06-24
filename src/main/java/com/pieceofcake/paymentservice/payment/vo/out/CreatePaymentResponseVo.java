package com.pieceofcake.paymentservice.payment.vo.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePaymentResponseVo {
    @Schema(
            description = "주문 ID. 사실상 저희의 paymentUuid\n" +
                    "toss에서 요구하는 주문 고유값의 변수명이 orderId여서 해당 이름으로 전달합니다.",
            example = "order-1234",
            required = true
    )
    private String orderId;
    @Schema(
            description = "고객 키. toss에서 요구하는 고객 고유값의 변수명.",
            example = "customer-1234",
            required = true
    )
    private String customerKey;
    @Schema(
            description = "결제 금액. toss에서 요구하는 결제 금액의 변수명.",
            example = "10000",
            required = true
    )
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
