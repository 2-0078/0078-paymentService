package com.pieceofcake.paymentservice.payment.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreatePaymentRequestVo {
    @Schema(description = "결제 금액", required = true)
    private Long amount;
    @Schema(
        description = "주문 이름. toss에서 의무로 요구하는 사항입니다. 프론트에서 원하는대로 적어도 충분합니다.",
        example = "toss 5000원",
        required = true
    )
    private String orderName;
}
