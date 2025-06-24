package com.pieceofcake.paymentservice.payment.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ConfirmPaymentRequestVo {
    @Schema(
            description = "결제 종류. toss에서 redirect URL로 전해주긴 하지만, 저희가 사용하진 않습니다.\n" +
                    "일단 받아두는 용도입니다.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String paymentType;
    @Schema(
            description = "paymentUuid. toss에서 결제 성공 후 redirect URL로 전해줍니다.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String orderId;
    @Schema(
            description = "결제 키. toss에서 결제 성공 후 redirect URL로 전해줍니다.\n" +
                    "이 키를 통해 toss의 특정 결제 정보를 검증할 수 있습니다.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String paymentKey;
    @Schema(
            description = "결제 금액. toss에서 결제 성공 후 redirect URL로 전해줍니다.\n",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long amount;
}
