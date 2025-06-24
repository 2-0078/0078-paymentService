package com.pieceofcake.paymentservice.money.infrastructure;

import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyHistoryRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.entity.Money;
import com.pieceofcake.paymentservice.money.entity.QMoney;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MoneyCustomRepositoryImpl implements MoneyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private static final int PAGE_SIZE = 10;

    @Override
    public Page<Money> getMoneyHistoryByFilters(
            ReadMoneyHistoryRequestDto requestDto,
            Pageable pageable
    ) {

        QMoney money = QMoney.money;
        BooleanBuilder builder = new BooleanBuilder();

        // 필수 조건
        builder.and(money.memberUuid.eq(requestDto.getMemberUuid()));

        // 선택 조건 1: 입출금 여부
        if (requestDto.getIsPositive() != null) {
            builder.and(money.isPositive.eq(requestDto.getIsPositive()));
        }

        // 선택 조건 2: 거래 유형
        if (requestDto.getHistoryType() != null) {
            builder.and(money.historyType.eq(requestDto.getHistoryType()));
        }

        // 선택 조건 3: 기간 조건 처리
        if (requestDto.getPeriod() != null && !requestDto.getPeriod().isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startDate = switch (requestDto.getPeriod()) {
                case "1개월" -> now.minusMonths(1);
                case "3개월" -> now.minusMonths(3);
                case "6개월" -> now.minusMonths(6);
                case "1년"  -> now.minusYears(1);
                default     -> null;
            };

            if (startDate != null) {
                builder.and(money.createdAt.goe(startDate));
            }
        }

        // ✅ 본문
        List<Money> results = jpaQueryFactory
                .selectFrom(money)
                .where(builder)
                .orderBy(money.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(money.createdAt.desc())
                .fetch();

        // ✅ 카운트 쿼리
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(money.count())
                        .from(money)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

}
