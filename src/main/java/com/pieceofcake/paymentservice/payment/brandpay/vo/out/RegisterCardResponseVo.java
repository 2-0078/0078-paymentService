package com.pieceofcake.paymentservice.payment.brandpay.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterCardResponseVo {
    private String customerKey;
    private String clientKey;

    @Builder
    public RegisterCardResponseVo(String customerKey, String clientKey) {
        this.customerKey = customerKey;
        this.clientKey = clientKey;
    }
}
