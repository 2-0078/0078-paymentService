package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.money.dto.CreateMoneyDto;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyAmountRequestDto;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyHistoryRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyAmountResponseDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import java.util.List;

public interface MoneyService {
    void createMoney(CreateMoneyDto createMoneyDto);
    ReadMoneyAmountResponseDto readRemainingMoney(ReadMoneyAmountRequestDto readMoneyAmountRequestDto);
    List<ReadMoneyHistoryResponseDto> readMoneyHistory(ReadMoneyHistoryRequestDto readMoneyHistoryRequestDto);
}
