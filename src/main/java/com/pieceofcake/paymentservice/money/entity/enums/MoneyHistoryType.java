package com.pieceofcake.paymentservice.money.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoneyHistoryType {
    DEPOSIT("입금"),
    WITHDRAWAL("출금"),
    BUY("구매"),
    SELL("판매"),
    REFUND("환불");

    private final String label;

}
