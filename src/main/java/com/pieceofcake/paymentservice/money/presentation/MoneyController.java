package com.pieceofcake.paymentservice.money.presentation;

import com.pieceofcake.paymentservice.common.entity.BaseResponseEntity;
import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.money.application.MoneyService;
import com.pieceofcake.paymentservice.money.dto.in.*;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import com.pieceofcake.paymentservice.money.vo.in.*;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyAmountResponseVo;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyHistoryResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/money")
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @Operation(summary = "remain money READ API",
            description = "현재 사용가능 금액 조회 API 입니다.\n\n" +
                    "사용가능 금액은 (잔액 - 보증금)을 의미합니다.")
    @GetMapping()
    public BaseResponseEntity<ReadMoneyAmountResponseVo> getUsableMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid
    ) {
        ReadMoneyAmountResponseVo readMoneyAmountResponseVo = moneyService.readUsableMoney(
                ReadMoneyAmountRequestDto.of(memberUuid)).toVo();

        return new BaseResponseEntity<>(readMoneyAmountResponseVo);
    }

    @Operation(
            summary = "money history READ API",
            description = "머니 내역을 페이징 + 조건 필터로 조회합니다. 아래 조건은 모두 null 가능입니다.\n\n" +
                    "- 입출금 여부(`isPositive`): true = 입금, false = 출금\n" +
                    "- 거래유형(`historyType`): DEPOSIT, WITHDRAWAL, SELL, REFUND, PIECE_BUY, PIECE_SELL, FUNDING, FEE, PROFIT, PRODUCT_BUY, FREEZE\n" +
                    "- 기간(`period`): 1개월, 3개월, 6개월, 1년"
    )
    @GetMapping("/history")
    public BaseResponseEntity<List<ReadMoneyHistoryResponseVo>> getMoneyHistory(
            @Parameter(description = "회원 UUID", example = "abc-1234", required = true)
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "입출금 여부 (true: 입금, false: 출금)", required = false)
            @RequestParam(value = "isPositive", required = false) Boolean isPositive,

            @Parameter(description = "거래 유형", example = "CHARGE", required = false)
            @RequestParam(value = "historyType", required = false) MoneyHistoryType historyType,

            @Parameter(description = "조회 기간", required = false,
                    schema = @Schema(allowableValues = {"1개월", "3개월", "6개월", "1년", "ALL"}))
            @RequestParam(value = "period", defaultValue = "ALL") String period
    ) {
        ReadMoneyHistoryRequestVo readMoneyHistoryRequestVo = ReadMoneyHistoryRequestVo.builder()
                .memberUuid(memberUuid)
                .page(page)
                .isPositive(isPositive)
                .historyType(historyType)
                .period(period)
                .build();

        List<ReadMoneyHistoryResponseVo> result = moneyService
                .readMoneyHistory(ReadMoneyHistoryRequestDto.from(readMoneyHistoryRequestVo))
                .stream()
                .map(ReadMoneyHistoryResponseDto::toVo)
                .toList();

        return new BaseResponseEntity<>(result);
    }

    @Operation(summary = "createMoney API",
            description = "결제(머니 생성) API입니다.\n\n" +
                    "- 요청 헤더 `X-Member-Uuid`는 JWT에서 받아옵니다.\n" +
                    "- 요청 바디에는 금액(amount), 입출금 여부(isPositive), 거래유형(MoneyHistoryType)이 필수값입니다."
    )
    @PostMapping
    public BaseResponseEntity<Void> createMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "결제 요청 정보 (머니 생성 정보)",
                    required = true
            )
            @RequestBody CreateMoneyRequestVo createMoneyRequestVo
    ){
        moneyService.createMoney(CreateMoneyRequestDto.from(createMoneyRequestVo, memberUuid));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(
            summary = "createMoneyWithMemberUuid API",
            description = "memberUuid를 포함한 결제(머니 생성) API입니다.\n" +
                    "- 요청 바디에는 memberUuid가 포함되며, 위의 createMoney API의 body들도 포함합니다.\n" +
                    "- 이 API는 주로 다른 서비스에서 호출(feign client)되어 특정 회원의 머니를 환불/보증취소 할 때 사용됩니다."
    )
    @PostMapping("/with-member-uuid")
    public BaseResponseEntity<Void> createMoney(
            @RequestBody CreateMoneyWithMemberUuidRequestVo createMoneyWithMemberUuidRequestVo
    ){
        moneyService.createMoneyWithMemberUuid(CreateMoneyWithMemberUuidRequestDto.from(createMoneyWithMemberUuidRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(
            summary = "withdraw API",
            description = "출금 API 입니다.\n\n" +
                    "- 요청 바디에는 은행명(bank), 출금금액(amount), 예금주명(accountHolderName), 계좌번호(accountNumber).\n" +
                    "- 현재는 예치금 차감 기능이 전부입니다. 차후 관리자에게 전달하거나 하는 식으로 구현 예정입니다."
    )
    @PostMapping("/withdraw")
    public BaseResponseEntity<Void> withdrawMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody WithdrawMoneyRequestVo withdrawMoneyRequestVo
            ) {
        moneyService.withdrawMoney(WithdrawMoneyRequestDto.from(withdrawMoneyRequestVo, memberUuid));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
