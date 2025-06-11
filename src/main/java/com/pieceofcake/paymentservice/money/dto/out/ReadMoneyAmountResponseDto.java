package com.pieceofcake.paymentservice.money.dto.out;

import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyAmountResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadMoneyAmountResponseDto {
    private Long amount;

    @Builder
    public ReadMoneyAmountResponseDto(Long amount) {
        this.amount = amount;
    }

    public static ReadMoneyAmountResponseDto of(Long amount) {
        return ReadMoneyAmountResponseDto.builder()
                .amount(amount)
                .build();
    }

    public ReadMoneyAmountResponseVo toVo() {
        return ReadMoneyAmountResponseVo.builder()
                .amount(amount)
                .build();
    }
}
