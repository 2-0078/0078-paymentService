package com.pieceofcake.paymentservice.payment.brandpay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pieceofcake.paymentservice.common.entity.BaseEntity;
import com.pieceofcake.paymentservice.payment.brandpay.entity.enums.PaymentMethod;
import com.pieceofcake.paymentservice.payment.brandpay.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_uuid", nullable = false, unique = true)
    private String paymentUuid; // 결제 UUID, toss의 paymentUuid와 매칭되는 결제 ID

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod method; // ENUM: BRANDPAY, CARD, ACCOUNT, PHONE

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status; // ENUM: REQUESTED, APPROVED, FAILED, REFUNDED

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Builder
    public Payment(
            String paymentUuid,
            String paymentKey,
            PaymentMethod method,
            PaymentStatus status,
            Long amount,
            LocalDateTime approvedAt
    ) {
        this.paymentUuid = paymentUuid;
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = status;
        this.amount = amount;
        this.approvedAt = approvedAt;
    }
}