package com.pieceofcake.paymentservice.money.vo.in;

import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateMoneyRequestVo {
    @Schema(description = "금액", example = "10000", required = true)
    private Long amount; // 금액
    @Schema(description = "입출금 여부 (true: 입금, false: 출금)", example = "true", required = true)
    private Boolean isPositive; // 입출금여부
    @Schema(description = "거래유형", example = "DEPOSIT", required = true)
    private MoneyHistoryType historyType; // 거래유형
    @Schema(description = "결제관련상세정보(상품UUID 등)", example = "piece-123, auction-123")
    private String moneyHistoryDetail; // 상세정보
    @Schema(description = "은행이름")
    private String bankName; // 은행
    @Schema(description = "계좌번호")
    private String accountNumber; // 계좌번호
    @Schema(description = "예금주명")
    private String accountHolderName; // 예금주명
    @Schema(description = "결제UUID, toss 입금 시에만 저장")
    private String paymentUuid;
    @Schema(description = "결제 시기, toss 입금 시에만 저장.")
    private LocalDateTime paymentTime;
    @Schema(description = "결제 방법 (예: Toss - 카드결제 등)")
    private String paymentMethod;
    @Schema(description = "결제 상태 (예: Toss - 결제 성공 등)")
    private String paymentStatus;
}
