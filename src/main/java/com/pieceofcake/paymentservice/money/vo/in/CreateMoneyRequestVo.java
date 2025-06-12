package com.pieceofcake.paymentservice.money.vo.in;

import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateMoneyRequestVo {
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
}
