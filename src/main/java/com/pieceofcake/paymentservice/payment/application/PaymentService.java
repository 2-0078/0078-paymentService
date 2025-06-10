package com.pieceofcake.paymentservice.payment.application;

import com.pieceofcake.paymentservice.payment.dto.in.ConfirmPaymentRequestDto;
import com.pieceofcake.paymentservice.payment.dto.in.CreatePaymentRequestDto;
import com.pieceofcake.paymentservice.payment.dto.out.ConfirmPaymentResponseDto;
import com.pieceofcake.paymentservice.payment.dto.out.CreatePaymentResponseDto;

public interface PaymentService {
    CreatePaymentResponseDto createPayment(CreatePaymentRequestDto createPaymentRequestDto);
    ConfirmPaymentResponseDto confirmPayment(ConfirmPaymentRequestDto confirmPaymentRequestDto);

}
