package com.pieceofcake.paymentservice.payment.brandpay.dto.in;

import com.pieceofcake.paymentservice.payment.brandpay.vo.in.RegisterCardCallbackRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterCardCallbackRequestDto {
    private String customerKey;
    private String code;

    @Builder
    public RegisterCardCallbackRequestDto(String customerKey, String code) {
        this.customerKey = customerKey;
        this.code = code;
    }

    public static RegisterCardCallbackRequestDto from(RegisterCardCallbackRequestVo registerCardCallbackRequestVo) {
        return RegisterCardCallbackRequestDto.builder()
                .customerKey(registerCardCallbackRequestVo.getCustomerKey())
                .code(registerCardCallbackRequestVo.getCode())
                .build();
    }
}
