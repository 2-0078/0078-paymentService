package com.pieceofcake.paymentservice.money.dto.in;

import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import com.pieceofcake.paymentservice.money.vo.in.CreateMoneyWithMemberUuidRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class CreateMoneyWithMemberUuidRequestDto {
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
    public CreateMoneyWithMemberUuidRequestDto(
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

    public static CreateMoneyWithMemberUuidRequestDto from(CreateMoneyWithMemberUuidRequestVo requestVo) {
        return CreateMoneyWithMemberUuidRequestDto.builder()
                .memberUuid(requestVo.getMemberUuid())
                .amount(requestVo.getAmount())
                .isPositive(requestVo.getIsPositive())
                .historyType(requestVo.getHistoryType())
                .moneyHistoryDetail(requestVo.getMoneyHistoryDetail())
                .bankName(requestVo.getBankName())
                .accountNumber(requestVo.getAccountNumber())
                .accountHolderName(requestVo.getAccountHolderName())
                .paymentUuid(requestVo.getPaymentUuid())
                .paymentTime(requestVo.getPaymentTime())
                .paymentMethod(requestVo.getPaymentMethod())
                .paymentStatus(requestVo.getPaymentStatus())
                .build();
    }
}
