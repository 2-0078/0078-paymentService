package com.pieceofcake.paymentservice.money.dto;

import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@ToString
@Getter
@NoArgsConstructor
public class CreateMoneyDto {
    private Long id;
    private String memberUuid;
    private Long amount;
    private Boolean isPositive;
    private MoneyHistoryType historyType;
    private String moneyHistoryDetail;
    private String paymentUuid;
    private String bankName;
    private String accountNumber;
    private String accountHolderName;
    private LocalDateTime paymentTime;
    private String paymentMethod;
    private String paymentStatus;

    @Builder
    public CreateMoneyDto(
            Long id,
            String memberUuid,
            Long amount,
            Boolean isPositive,
            MoneyHistoryType historyType,
            String moneyHistoryDetail,
            String paymentUuid,
            String bankName,
            String accountNumber,
            String accountHolderName,
            LocalDateTime paymentTime,
            String paymentMethod,
            String paymentStatus
    ) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.amount = amount;
        this.isPositive = isPositive;
        this.historyType = historyType;
        this.moneyHistoryDetail = moneyHistoryDetail;
        this.paymentUuid = paymentUuid;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public Money toEntity(Long remainingMoney) {
        return Money.builder()
                .id(id)
                .memberUuid(memberUuid)
                .amount(amount)
                .isPositive(isPositive)
                .historyType(historyType)
                .remainingMoney(remainingMoney)
                .moneyHistoryDetail(moneyHistoryDetail)
                .paymentUuid(paymentUuid)
                .bankName(bankName)
                .accountNumber(accountNumber)
                .accountHolderName(accountHolderName)
                .paymentTime(paymentTime)
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .build();
    }
}
