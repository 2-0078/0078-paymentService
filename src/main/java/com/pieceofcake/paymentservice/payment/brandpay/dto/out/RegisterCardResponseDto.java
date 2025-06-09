package com.pieceofcake.paymentservice.payment.brandpay.dto.out;

import com.pieceofcake.paymentservice.payment.brandpay.vo.out.RegisterCardResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterCardResponseDto {
    private String customerKey;
    private String clientKey;

    @Builder
    public RegisterCardResponseDto(String customerKey, String clientKey) {
        this.customerKey = customerKey;
        this.clientKey = clientKey;
    }

    public static RegisterCardResponseDto of(String customerKey, String clientKey) {
        return RegisterCardResponseDto.builder()
                .customerKey(customerKey)
                .clientKey(clientKey)
                .build();
    }

    public RegisterCardResponseVo toVo() {
        return RegisterCardResponseVo.builder()
                .customerKey(customerKey)
                .clientKey(clientKey)
                .build();
    }
}
