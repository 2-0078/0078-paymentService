package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.common.exception.BaseException;
import com.pieceofcake.paymentservice.money.dto.CreateMoneyDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.infrastructure.MoneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService{
    private final MoneyRepository moneyRepository;

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
}
