package com.pieceofcake.paymentservice.money.dto.out;

import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyHistoryResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReadMoneyHistoryResponseDto {
    private Long amount;
    private Boolean isPositive;
    private String historyType;
    private Long remainingMoney;
    private Long FrozenMoney;
    private String moneyHistoryDetail;
    private LocalDateTime createdAt;

    @Builder
    public ReadMoneyHistoryResponseDto(
            Long amount,
            Boolean isPositive,
            String historyType,
            Long remainingMoney,
            Long frozenMoney,
            String moneyHistoryDetail,
            LocalDateTime createdAt
    ) {
        this.amount = amount;
        this.isPositive = isPositive;
        this.historyType = historyType;
        this.remainingMoney = remainingMoney;
        this.FrozenMoney = frozenMoney;
        this.moneyHistoryDetail = moneyHistoryDetail;
        this.createdAt = createdAt;
    }

    public static ReadMoneyHistoryResponseDto from(Money money) {
        return ReadMoneyHistoryResponseDto.builder()
                .amount(money.getAmount())
                .isPositive(money.getIsPositive())
                .historyType(money.getHistoryType().name())
                .remainingMoney(money.getRemainingMoney())
                .frozenMoney(money.getFrozenMoney())
                .moneyHistoryDetail(money.getMoneyHistoryDetail())
                .createdAt(money.getCreatedAt())
                .build();
    }

    public ReadMoneyHistoryResponseVo toVo() {
        return ReadMoneyHistoryResponseVo.builder()
                .amount(amount)
                .isPositive(isPositive)
                .historyType(historyType)
                .remainingMoney(remainingMoney)
                .frozenMoney(FrozenMoney)
                .moneyHistoryDetail(moneyHistoryDetail)
                .createdAt(createdAt)
                .build();
    }
}
