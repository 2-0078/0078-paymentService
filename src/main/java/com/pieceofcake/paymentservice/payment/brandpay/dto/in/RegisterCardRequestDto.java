package com.pieceofcake.paymentservice.payment.brandpay.dto.in;

import com.pieceofcake.paymentservice.payment.brandpay.vo.in.RegisterCardRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RegisterCardRequestDto {
    private String memberUuid;

    @Builder
    public RegisterCardRequestDto(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public static RegisterCardRequestDto from(RegisterCardRequestVo registerCardRequestVo) {
        return RegisterCardRequestDto.builder()
                .memberUuid(registerCardRequestVo.getMemberUuid())
                .build();
    }
}
