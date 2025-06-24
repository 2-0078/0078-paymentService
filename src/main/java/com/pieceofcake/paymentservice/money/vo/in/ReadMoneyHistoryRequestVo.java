package com.pieceofcake.paymentservice.money.vo.in;

import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadMoneyHistoryRequestVo {
    private String memberUuid;
    private int page;
    private Boolean isPositive;
    private MoneyHistoryType historyType;
    private String period;

    @Builder
    public ReadMoneyHistoryRequestVo(
            String memberUuid,
            int page,
            Boolean isPositive,
            MoneyHistoryType historyType,
            String period
    ) {
        this.memberUuid = memberUuid;
        this.page = page;
        this.isPositive = isPositive;
        this.historyType = historyType;
        this.period = period;
    }
}
