package com.pieceofcake.paymentservice.money.vo.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadMoneyHistoryRequestVo {
    private String memberUuid;
    private int page;

    @Builder
    public ReadMoneyHistoryRequestVo(String memberUuid, int page) {
        this.memberUuid = memberUuid;
        this.page = page;
    }
}
