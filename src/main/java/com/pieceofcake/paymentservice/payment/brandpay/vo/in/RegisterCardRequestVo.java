package com.pieceofcake.paymentservice.payment.brandpay.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterCardRequestVo {
    private String memberUuid;

    @Builder
    private RegisterCardRequestVo(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public static RegisterCardRequestVo of(String memberUuid) {
        return RegisterCardRequestVo.builder()
                .memberUuid(memberUuid)
                .build();
    }
}
