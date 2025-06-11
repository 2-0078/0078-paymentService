package com.pieceofcake.paymentservice.money.infrastructure;

import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoneyRepository extends JpaRepository<Money, Long> {
    Optional<Money> findTopByMemberUuidOrderByCreatedAtDesc(String memberUuid);

}
