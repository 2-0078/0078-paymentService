package com.pieceofcake.paymentservice.payment.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmPaymentResponseVo {
    private Integer totalOriginPrice;
    private Integer totalPurchasePrice;
    private String method;
    private String orderName;
    private String orderListUuid;

    @Builder
    public ConfirmPaymentResponseVo(Integer totalOriginPrice, Integer totalPurchasePrice, String method, String orderName, String orderListUuid) {
        this.totalOriginPrice = totalOriginPrice;
        this.totalPurchasePrice = totalPurchasePrice;
        this.method = method;
        this.orderName = orderName;
        this.orderListUuid = orderListUuid;
    }
}
