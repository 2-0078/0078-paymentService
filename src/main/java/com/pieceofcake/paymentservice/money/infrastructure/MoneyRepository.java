package com.pieceofcake.paymentservice.money.infrastructure;

import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyHistoryRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface MoneyRepository extends JpaRepository<Money, Long>, MoneyCustomRepository {
    Page<Money> getMoneyHistoryByFilters(
            ReadMoneyHistoryRequestDto readMoneyHistoryRequestDto, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Money> findFirstByMemberUuidOrderByIdDesc(String memberUuid);

    Optional<Money> findTopByMemberUuidOrderByIdDesc(String memberUuid);
    Page<Money>findAllByMemberUuid(String memberUuid, Pageable pageable);
}
