package com.pieceofcake.paymentservice.payment.brandpay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class AccessTokenResponseDto {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;

    @Builder
    public AccessTokenResponseDto(String accessToken, String tokenType, String refreshToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public static AccessTokenResponseDto of(String accessToken, String tokenType, String refreshToken, Long expiresIn) {
        return AccessTokenResponseDto.builder()
                .accessToken(accessToken)
                .tokenType(tokenType)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }
}
