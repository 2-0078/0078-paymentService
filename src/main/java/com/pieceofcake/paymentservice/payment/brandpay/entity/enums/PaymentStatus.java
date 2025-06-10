package com.pieceofcake.paymentservice.payment.brandpay.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    REQUESTED("요청됨"),
    APPROVED("승인됨"),
    FAILED("실패함"),
    REFUNDED("환불됨");

    @JsonValue
    private final String label;
}