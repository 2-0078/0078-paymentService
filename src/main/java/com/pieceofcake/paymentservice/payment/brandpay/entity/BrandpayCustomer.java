package com.pieceofcake.paymentservice.payment.brandpay.entity;

import com.pieceofcake.paymentservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "brandpay_customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandpayCustomer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // BrandPay 고객 ID (자동 생성)

    @Column(name = "member_uuid", nullable = false)
    private String memberUuid;      // 우리 서비스의 유저 식별자

    @Column(name = "customer_key")
    private String customerKey; // Toss BrandPay 고객 식별키용 UUID (가맹점 생성)

    @Column(name = "method_key")
    private String methodKey; // 고객이 등록한 카드 식별키 (결제수단 키)

    @Column(name = "access_token")
    private String accessToken; // 발급된 Access Token (Bearer 용)

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;

    @Builder
    public BrandpayCustomer(
            String memberUuid,
            String customerKey,
            String methodKey,
            String accessToken,
            String refreshToken,
            LocalDateTime tokenExpiresAt
    ) {
        this.memberUuid = memberUuid;
        this.customerKey = customerKey;
        this.methodKey = methodKey;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenExpiresAt = tokenExpiresAt;
    }
}
