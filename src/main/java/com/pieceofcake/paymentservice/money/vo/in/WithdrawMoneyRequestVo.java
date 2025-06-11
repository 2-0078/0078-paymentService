package com.pieceofcake.paymentservice.money.vo.in;

import lombok.Getter;

@Getter
public class WithdrawMoneyRequestVo {
    private String bank;
    private Long amount;
    private String accountHolderName;
    private String accountNumber;
}
