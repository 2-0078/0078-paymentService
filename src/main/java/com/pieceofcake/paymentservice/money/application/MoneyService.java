package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.money.dto.CreateMoneyDto;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyAmountRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyAmountResponseDto;
import com.pieceofcake.paymentservice.payment.dto.in.ConfirmPaymentRequestDto;

public interface MoneyService {
    void createMoney(CreateMoneyDto createMoneyDto);
    ReadMoneyAmountResponseDto readMoneyAmount(ReadMoneyAmountRequestDto readMoneyAmountRequestDto);
}
