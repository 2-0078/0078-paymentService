package com.pieceofcake.paymentservice.money.application;

import com.pieceofcake.paymentservice.money.dto.CreateMoneyDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.infrastructure.MoneyRepository;
import com.pieceofcake.paymentservice.payment.dto.in.ConfirmPaymentRequestDto;
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
        Long oldRemainingMoney = oldMoney.isPresent() ? oldMoney.get().getRemainingMoney() : "0";

        Money money = createMoneyDto.toEntity(oldRemainingMoney);

        moneyRepository.save(money);
    }
}
