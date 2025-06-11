package com.pieceofcake.paymentservice.money.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadMoneyAmountResponseVo {
    private Long amount;

    @Builder
    public ReadMoneyAmountResponseVo(Long amount) {
        this.amount = amount;
    }
}
