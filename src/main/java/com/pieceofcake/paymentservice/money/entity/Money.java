package com.pieceofcake.paymentservice.money.entity;

import com.pieceofcake.paymentservice.common.entity.BaseEntity;
import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "money")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예치금내역ID

    @Column(name = "member_uuid", nullable = false)
    private String memberUuid; // 멤버UUID

    @Column(name = "amount", nullable = false)
    private Long amount; // 금액

    @Column(name = "is_positive", nullable = false)
    private Boolean isPositive; // 입출금여부

    @Enumerated(EnumType.STRING)
    @Column(name = "history_type", nullable = false)
    private MoneyHistoryType historyType; // 거래유형

    @Column(name = "remaining_money", nullable = false)
    private Long remainingMoney; // 잔여금액

    @Column(name = "frozen_money", nullable = false)
    private Long frozenMoney; // 동결금액 (보증금)

    @Column(name = "money_history_detail")
    private String moneyHistoryDetail; // 상세정보

    @Column(name = "payment_uuid", length = 50)
    private String paymentUuid; // 결제UUID

    @Column(name = "bank_name")
    private String bankName; // 은행

    @Column(name = "account_number")
    private String accountNumber; // 계좌번호

    @Column(name = "account_holder_name")
    private String accountHolderName; // 예금주명

    @Column(name = "payment_time")
    private LocalDateTime paymentTime; // 결제 시기

    @Column(name = "payment_method")
    private String paymentMethod; // 결제 방법 (~PG)

    @Column(name = "payment_status")
    private String paymentStatus; // 결제 상태

    @Builder
    public Money(
            Long id,
            String memberUuid,
            Long amount,
            Boolean isPositive,
            MoneyHistoryType historyType,
            Long remainingMoney,
            Long frozenMoney,
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
        this.remainingMoney = remainingMoney;
        this.frozenMoney = frozenMoney;
        this.moneyHistoryDetail = moneyHistoryDetail;
        this.paymentUuid = paymentUuid;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }
}