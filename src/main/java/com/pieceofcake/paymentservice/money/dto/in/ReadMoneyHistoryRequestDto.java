package com.pieceofcake.paymentservice.money.dto.in;

import com.pieceofcake.paymentservice.money.vo.in.ReadMoneyHistoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadMoneyHistoryRequestDto {
    private String memberUuid;
    private int page;

    @Builder
    public ReadMoneyHistoryRequestDto (
            String memberUuid,
            int page
    ) {
        this.memberUuid = memberUuid;
        this.page = page;
    }

    public static ReadMoneyHistoryRequestDto from(ReadMoneyHistoryRequestVo readMoneyHistoryRequestVo) {
        return ReadMoneyHistoryRequestDto.builder()
                .memberUuid(readMoneyHistoryRequestVo.getMemberUuid())
                .page(readMoneyHistoryRequestVo.getPage())
                .build();
    }
}
