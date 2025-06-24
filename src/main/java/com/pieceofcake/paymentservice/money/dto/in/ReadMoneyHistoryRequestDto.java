package com.pieceofcake.paymentservice.money.dto.in;

import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import com.pieceofcake.paymentservice.money.vo.in.ReadMoneyHistoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadMoneyHistoryRequestDto {
    private String memberUuid;
    private int page;
    private Boolean isPositive;
    private MoneyHistoryType historyType;
    private String period;

    @Builder
    public ReadMoneyHistoryRequestDto (
            String memberUuid,
            int page,
            Boolean isPositive,
            MoneyHistoryType historyType,
            String period
    ) {
        this.memberUuid = memberUuid;
        this.page = page;
        this.isPositive = isPositive;
        this.historyType = historyType;
        this.period = period;
    }

    public static ReadMoneyHistoryRequestDto from(ReadMoneyHistoryRequestVo readMoneyHistoryRequestVo) {
        return ReadMoneyHistoryRequestDto.builder()
                .memberUuid(readMoneyHistoryRequestVo.getMemberUuid())
                .page(readMoneyHistoryRequestVo.getPage())
                .isPositive(readMoneyHistoryRequestVo.getIsPositive())
                .historyType(readMoneyHistoryRequestVo.getHistoryType())
                .period(readMoneyHistoryRequestVo.getPeriod())
                .build();
    }
}
