package com.pieceofcake.paymentservice.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "payment_customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_key", nullable = false, unique = true, updatable = false)
    private String customerKey;

    @Column(name = "member_uuid", nullable = false, length = 50)
    private String memberUuid;

    @Builder
    public PaymentCustomer(String customerKey, String memberUuid) {
        this.customerKey = customerKey;
        this.memberUuid = memberUuid;
    }
}
