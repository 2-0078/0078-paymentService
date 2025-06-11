package com.pieceofcake.paymentservice.money.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadMoneyAmountRequestDto {
    private String memberUuid;

    @Builder
    public ReadMoneyAmountRequestDto(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public static ReadMoneyAmountRequestDto of(String memberUuid) {
        return ReadMoneyAmountRequestDto.builder()
                .memberUuid(memberUuid)
                .build();
    }
}
