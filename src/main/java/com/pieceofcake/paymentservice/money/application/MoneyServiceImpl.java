package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.common.exception.BaseException;
import com.pieceofcake.paymentservice.money.dto.in.*;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyAmountResponseDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import com.pieceofcake.paymentservice.money.infrastructure.MoneyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
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
    private final ApplicationContext applicationContext;

    int PAGE_SIZE = 10;

    @Override
    @Transactional
    public void createMoney(CreateMoneyRequestDto createMoneyRequestDto) {
        String memberUuid = createMoneyRequestDto.getMemberUuid();
        // 기존 돈이 있는지 조회
        Optional<Money> oldMoney = moneyRepository.findFirstByMemberUuidOrderByIdDesc(memberUuid);
        Long remainingMoney = oldMoney.isPresent() ? oldMoney.get().getRemainingMoney() : 0L;
        Long frozenMoney = oldMoney.isPresent() ? oldMoney.get().getFrozenMoney() : 0L;

        if (createMoneyRequestDto.getHistoryType() == MoneyHistoryType.FREEZE) {
            // 보증금 관련 create
            if (!createMoneyRequestDto.getIsPositive()) {
                // 금액 동결 처리
                frozenMoney += createMoneyRequestDto.getAmount();
                if (frozenMoney > remainingMoney) {
                    // 동결 금액이 잔액을 초과하는 경우 예외 처리
                    throw new BaseException(BaseResponseStatus.FROZEN_MONEY_EXCEED);
                }
            } else {
                // 동결 해제 처리
                frozenMoney -= createMoneyRequestDto.getAmount();
            }
        } else if (createMoneyRequestDto.getHistoryType() == MoneyHistoryType.PRODUCT_BUY) {
            // 상품 구매 관련 create
            if (createMoneyRequestDto.getIsPositive()) {
                // 상품 구매 금액 환불 처리
                remainingMoney += createMoneyRequestDto.getAmount();
            } else {
                // 상품 구매 처리
                remainingMoney -= createMoneyRequestDto.getAmount();
                frozenMoney -= createMoneyRequestDto.getAmount();
                // 잔액이 부족한 경우 예외 처리
                if (remainingMoney < 0) {
                    throw new BaseException(BaseResponseStatus.TOO_LESS_MONEY);
                }
            }
        } else {
            // 입출금 관련 create
            if (createMoneyRequestDto.getIsPositive()) {
                // 입금 처리
                remainingMoney += createMoneyRequestDto.getAmount();
            } else {
                remainingMoney -= createMoneyRequestDto.getAmount();
                // 잔액이 부족한 경우 예외 처리
                if (remainingMoney < 0) {
                    throw new BaseException(BaseResponseStatus.TOO_LESS_MONEY);
                }
            }
        }


        Money money = createMoneyRequestDto.toEntity(remainingMoney, frozenMoney);

        moneyRepository.save(money);
    }

    public void createMoneyWithMemberUuid(CreateMoneyWithMemberUuidRequestDto createMoneyWithMemberUuidRequestDto) {
        // CreateMoneyRequestDto build 후 createMoney 호출
        MoneyService proxy = applicationContext.getBean(MoneyService.class);
        proxy.createMoney(CreateMoneyRequestDto.builder()
                .memberUuid(createMoneyWithMemberUuidRequestDto.getMemberUuid())
                .amount(createMoneyWithMemberUuidRequestDto.getAmount())
                .isPositive(createMoneyWithMemberUuidRequestDto.getIsPositive())
                .historyType(createMoneyWithMemberUuidRequestDto.getHistoryType())
                .moneyHistoryDetail(createMoneyWithMemberUuidRequestDto.getMoneyHistoryDetail())
                .build());
    }

    @Override
    public ReadMoneyAmountResponseDto readUsableMoney(ReadMoneyAmountRequestDto readMoneyAmountRequestDto) {
        // money가 하나라도 있으면 최신 값 반환. 없으면 0L 반환
        Long usableMoney = moneyRepository.findTopByMemberUuidOrderByIdDesc(
                        readMoneyAmountRequestDto.getMemberUuid()
                ).map(money -> money.getRemainingMoney() - money.getFrozenMoney())
                .orElse(0L);

        return ReadMoneyAmountResponseDto.of(usableMoney);
    }

    @Override
    public List<ReadMoneyHistoryResponseDto> readMoneyHistory(ReadMoneyHistoryRequestDto readMoneyHistoryRequestDto) {
        int page = Math.max(0, readMoneyHistoryRequestDto.getPage());
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());

        return moneyRepository.getMoneyHistoryByFilters(readMoneyHistoryRequestDto, pageable)
                .stream()
                .map(ReadMoneyHistoryResponseDto::from)
                .toList();
    }

    @Transactional
    @Override
    public void withdrawMoney(WithdrawMoneyRequestDto withdrawMoneyRequestDto) {
        // CreateMoneyRequestDto build 후 createMoney 호출

        createMoney(CreateMoneyRequestDto.builder()
                .memberUuid(withdrawMoneyRequestDto.getMemberUuid())
                .amount(withdrawMoneyRequestDto.getAmount())
                .isPositive(false) // 출금이므로 false
                .historyType(MoneyHistoryType.WITHDRAWAL)
                .bankName(withdrawMoneyRequestDto.getBank())
                .accountNumber(withdrawMoneyRequestDto.getAccountNumber())
                .accountHolderName(withdrawMoneyRequestDto.getAccountHolderName())
                .build());

        // 이제 이 내역을 관리자에게 전달하면 됩니다.
    }

}
