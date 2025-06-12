package com.pieceofcake.paymentservice.money.dto.in;

import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import com.pieceofcake.paymentservice.money.vo.in.CreateMoneyRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateMoneyRequestDto {
    private String memberUuid;
    private Long amount; // 금액
    private Boolean isPositive; // 입출금여부
    private MoneyHistoryType historyType; // 거래유형
    private String moneyHistoryDetail; // 상세정보
    private String bankName; // 은행
    private String accountNumber; // 계좌번호
    private String accountHolderName; // 예금주명
    private String paymentUuid;
    private LocalDateTime paymentTime;
    private String paymentMethod;
    private String paymentStatus;

    @Builder
    public CreateMoneyRequestDto(
            String memberUuid,
            Long amount,
            Boolean isPositive,
            MoneyHistoryType historyType,
            String moneyHistoryDetail,
            String bankName,
            String accountNumber,
            String accountHolderName,
            String paymentUuid,
            LocalDateTime paymentTime,
            String paymentMethod,
            String paymentStatus
    ) {
        this.memberUuid = memberUuid;
        this.amount = amount;
        this.isPositive = isPositive;
        this.historyType = historyType;
        this.moneyHistoryDetail = moneyHistoryDetail;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.paymentUuid = paymentUuid;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public static CreateMoneyRequestDto from(CreateMoneyRequestVo createMoneyRequestVo, String memberUuid) {
        return CreateMoneyRequestDto.builder()
                .memberUuid(memberUuid)
                .amount(createMoneyRequestVo.getAmount())
                .isPositive(createMoneyRequestVo.getIsPositive())
                .historyType(createMoneyRequestVo.getHistoryType())
                .moneyHistoryDetail(createMoneyRequestVo.getMoneyHistoryDetail())
                .bankName(createMoneyRequestVo.getBankName())
                .accountNumber(createMoneyRequestVo.getAccountNumber())
                .accountHolderName(createMoneyRequestVo.getAccountHolderName())
                .paymentUuid(createMoneyRequestVo.getPaymentUuid())
                .paymentTime(createMoneyRequestVo.getPaymentTime())
                .paymentMethod(createMoneyRequestVo.getPaymentMethod())
                .paymentStatus(createMoneyRequestVo.getPaymentStatus())
                .build();
    }

    public Money toEntity(Long newRemainingMoney){
        return Money.builder()
                .memberUuid(memberUuid)
                .amount(amount)
                .isPositive(isPositive)
                .historyType(historyType)
                .moneyHistoryDetail(moneyHistoryDetail)
                .bankName(bankName)
                .accountNumber(accountNumber)
                .accountHolderName(accountHolderName)
                .remainingMoney(newRemainingMoney)
                .paymentUuid(paymentUuid)
                .paymentTime(paymentTime)
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .build();
    }
}
