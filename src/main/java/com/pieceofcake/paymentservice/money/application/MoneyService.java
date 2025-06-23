package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.money.dto.in.*;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyAmountResponseDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import java.util.List;

public interface MoneyService {
    void createMoney(CreateMoneyRequestDto createMoneyDto);
    ReadMoneyAmountResponseDto readUsableMoney(ReadMoneyAmountRequestDto readMoneyAmountRequestDto);
    List<ReadMoneyHistoryResponseDto> readMoneyHistory(ReadMoneyHistoryRequestDto readMoneyHistoryRequestDto);
    void withdrawMoney(WithdrawMoneyRequestDto withdrawMoneyRequestDto);
}
