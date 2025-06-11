package com.pieceofcake.paymentservice.money.infrastructure;

import com.pieceofcake.paymentservice.money.entity.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MoneyRepository extends JpaRepository<Money, Long> {
    Optional<Money> findTopByMemberUuidOrderByCreatedAtDesc(String memberUuid);
    Page<Money> findAllByMemberUuid(String memberUuid, Pageable pageable);
}
