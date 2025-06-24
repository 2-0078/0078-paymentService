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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/money")
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @Operation(summary = "remain money READ API", description = "현재 사용가능 금액 조회 API 입니다.")
    @GetMapping()
    public BaseResponseEntity<ReadMoneyAmountResponseVo> getUsableMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid
    ) {
        ReadMoneyAmountResponseVo readMoneyAmountResponseVo = moneyService.readUsableMoney(
                ReadMoneyAmountRequestDto.of(memberUuid)).toVo();

        return new BaseResponseEntity<>(readMoneyAmountResponseVo);
    }

    @Operation(summary = "money history READ API", description = "Money List 페이지네이션 + 필터링 조회 API 입니다.")
    @GetMapping("/history")
    public BaseResponseEntity<List<ReadMoneyHistoryResponseVo>> getMoneyHistory(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "isPositive", required = false) Boolean isPositive,
            @RequestParam(value = "historyType", required = false) MoneyHistoryType historyType,
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

    @Operation(summary = "createMoney API", description = "결제 API")
    @PostMapping
    public BaseResponseEntity<Void> createMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody CreateMoneyRequestVo createMoneyRequestVo
    ){
        moneyService.createMoney(CreateMoneyRequestDto.from(createMoneyRequestVo, memberUuid));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "createMoneyWithMemberUuid API", description = "결제 with memberUuid API")
    @PostMapping("/with-member-uuid")
    public BaseResponseEntity<Void> createMoney(
            @RequestBody CreateMoneyWithMemberUuidRequestVo createMoneyWithMemberUuidRequestVo
    ){
        moneyService.createMoneyWithMemberUuid(CreateMoneyWithMemberUuidRequestDto.from(createMoneyWithMemberUuidRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "withdraw API", description = "출금 API 입니다.")
    @PostMapping("/withdraw")
    public BaseResponseEntity<Void> withdrawMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody WithdrawMoneyRequestVo withdrawMoneyRequestVo
            ) {
        moneyService.withdrawMoney(WithdrawMoneyRequestDto.from(withdrawMoneyRequestVo, memberUuid));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
