package com.pieceofcake.paymentservice.money.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class ReadMoneyHistoryResponseVo {

    private Long amount;
    private Boolean isPositive;
    private String historyType;
    private Long remainingMoney;
    private String moneyHistoryDetail;
    private LocalDateTime createdAt;

    @Builder
    public ReadMoneyHistoryResponseVo(
            Long amount,
            Boolean isPositive,
            String historyType,
            Long remainingMoney,
            String moneyHistoryDetail,
            LocalDateTime createdAt
    ) {
        this.amount = amount;
        this.isPositive = isPositive;
        this.historyType = historyType;
        this.remainingMoney = remainingMoney;
        this.moneyHistoryDetail = moneyHistoryDetail;
        this.createdAt = createdAt;
    }
}
