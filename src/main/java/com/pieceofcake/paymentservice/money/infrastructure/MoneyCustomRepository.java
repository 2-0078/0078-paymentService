package com.pieceofcake.paymentservice.money.infrastructure;

import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyHistoryRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MoneyCustomRepository {
    Page<Money> getMoneyHistoryByFilters(
            ReadMoneyHistoryRequestDto readMoneyHistoryRequestDto, Pageable pageable);
}
