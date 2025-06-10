package com.pieceofcake.paymentservice.payment.entity;

import com.pieceofcake.paymentservice.payment.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_uuid", nullable = false, length = 50)
    private String memberUuid;

    @Column(name = "payment_uuid", nullable = false, length = 50)
    private String paymentUuid;

    @Column(name = "payment_code", length = 50)
    private String paymentCode;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "fail_reason", length = 255)
    private String failReason;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "method", length = 50)
    private String method;

    @Column(name = "order_name", length = 100)
    private String orderName;

    @Builder
    public Payment(
            Long id,
            String memberUuid,
            String paymentUuid,
            String paymentCode,
            Long amount,
            PaymentStatus status,
            String failReason,
            LocalDateTime approvedAt,
            String method,
            String orderName
    ) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.paymentUuid = paymentUuid;
        this.paymentCode = paymentCode;
        this.amount = amount;
        this.status = status;
        this.failReason = failReason;
        this.approvedAt = approvedAt;
        this.method = method;
        this.orderName = orderName;
    }
}
