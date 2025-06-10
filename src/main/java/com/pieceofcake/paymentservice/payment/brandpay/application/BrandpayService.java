package com.pieceofcake.paymentservice.payment.brandpay.application;

import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardCallbackRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.out.RegisterCardResponseDto;

public interface BrandpayService {
    RegisterCardResponseDto registerCard(RegisterCardRequestDto registerCardRequestDto);
    void registerCardCallback(RegisterCardCallbackRequestDto registerCardCallbackRequestDto);
}
