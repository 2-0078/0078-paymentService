package com.pieceofcake.paymentservice.payment.brandpay.vo.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class RegisterCardCallbackRequestVo {
    private String customerKey;
    private String code;

    @Builder
    public RegisterCardCallbackRequestVo(String customerKey, String code) {
        this.customerKey = customerKey;
        this.code = code;
    }
}
