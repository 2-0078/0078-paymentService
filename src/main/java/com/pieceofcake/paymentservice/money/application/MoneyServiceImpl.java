package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.common.exception.BaseException;
import com.pieceofcake.paymentservice.money.dto.CreateMoneyDto;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyAmountRequestDto;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyHistoryRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyAmountResponseDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.infrastructure.MoneyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService{
    private final MoneyRepository moneyRepository;

    int PAGE_SIZE = 10;

    @Override
    public void createMoney(CreateMoneyDto createMoneyDto) {
        String memberUuid = createMoneyDto.getMemberUuid();
        // 기존 돈이 있는지 조회
        Optional<Money> oldMoney = moneyRepository.findTopByMemberUuidOrderByCreatedAtDesc(memberUuid);
        Long oldRemainingMoney = oldMoney.isPresent() ? oldMoney.get().getRemainingMoney() : 0;
        Long newRemainingMoney;
        if (createMoneyDto.getIsPositive()) {
            newRemainingMoney = oldRemainingMoney + createMoneyDto.getAmount();
        } else {
            newRemainingMoney = oldRemainingMoney - createMoneyDto.getAmount();
            // 잔액이 부족한 경우 예외 처리
            if (newRemainingMoney < 0) {
                throw new BaseException(BaseResponseStatus.NO_MONEY);
            }
        }

        Money money = createMoneyDto.toEntity(newRemainingMoney);

        moneyRepository.save(money);
    }

    @Override
    public ReadMoneyAmountResponseDto readRemainingMoney(ReadMoneyAmountRequestDto readMoneyAmountRequestDto) {
        Long remainingMoney = moneyRepository.findTopByMemberUuidOrderByCreatedAtDesc(readMoneyAmountRequestDto.getMemberUuid())
                .map(Money::getRemainingMoney)
                .orElse(0L);

        return ReadMoneyAmountResponseDto.of(remainingMoney);
    }

    @Override
    public List<ReadMoneyHistoryResponseDto> readMoneyHistory(ReadMoneyHistoryRequestDto readMoneyHistoryRequestDto) {
        int page = Math.max(0, readMoneyHistoryRequestDto.getPage());
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());

        return moneyRepository.findAllByMemberUuid(
                    readMoneyHistoryRequestDto.getMemberUuid(),
                    pageable
                )
                .stream()
                .map(ReadMoneyHistoryResponseDto::from)
                .toList();
    }


}
