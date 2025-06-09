package com.pieceofcake.paymentservice.payment.brandpay.dto;

import com.pieceofcake.paymentservice.payment.brandpay.entity.BrandpayCustomer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class RegisterCardDto {
    private String memberUuid;
    private String customerKey;          // Toss BrandPay 고객 식별키 (가맹점 생성)
    private String methodKey;            // 고객이 등록한 카드 식별키 (결제수단 키)
    private String accessToken;          // 발급된 Access Token (Bearer 용)
    private String refreshToken;
    private LocalDateTime tokenExpiresAt;

    @Builder
    public RegisterCardDto(
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

    public static RegisterCardDto of(
            String memberUuid,
            String customerKey,
            String methodKey,
            String accessToken,
            String refreshToken,
            LocalDateTime tokenExpiresAt
    ) {
        return RegisterCardDto.builder()
                .memberUuid(memberUuid)
                .customerKey(customerKey)
                .methodKey(methodKey)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpiresAt(tokenExpiresAt)
                .build();
    }

    public BrandpayCustomer toEntity() {
        return BrandpayCustomer.builder()
                .memberUuid(memberUuid)
                .customerKey(customerKey)
                .methodKey(methodKey)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpiresAt(tokenExpiresAt)
                .build();
    }
}
