package com.pieceofcake.paymentservice.payment.brandpay.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    BRANDPAY("브랜드페이"),
    CARD("카드"),
    ACCOUNT("계좌"),
    PHONE("휴대폰");

    @JsonValue
    private final String label;
}