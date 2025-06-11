package com.pieceofcake.paymentservice.money.presentation;

import com.pieceofcake.paymentservice.common.entity.BaseResponseEntity;
import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.money.application.MoneyService;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyAmountRequestDto;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyHistoryRequestDto;
import com.pieceofcake.paymentservice.money.dto.out.ReadMoneyHistoryResponseDto;
import com.pieceofcake.paymentservice.money.vo.in.ReadMoneyHistoryRequestVo;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyAmountResponseVo;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyHistoryResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/money")
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @GetMapping()
    public BaseResponseEntity<ReadMoneyAmountResponseVo> getMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid
    ) {
        ReadMoneyAmountResponseVo readMoneyAmountResponseVo = moneyService.readRemainingMoney(
                ReadMoneyAmountRequestDto.of(memberUuid)).toVo();

        return new BaseResponseEntity<>(readMoneyAmountResponseVo);
    }

    @GetMapping("/history")
    public BaseResponseEntity<List<ReadMoneyHistoryResponseVo>> getMoneyHistory(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        ReadMoneyHistoryRequestVo readMoneyHistoryRequestVo = ReadMoneyHistoryRequestVo.builder()
                .memberUuid(memberUuid)
                .page(page)
                .build();

        List<ReadMoneyHistoryResponseVo> result = moneyService
                .readMoneyHistory(ReadMoneyHistoryRequestDto.from(readMoneyHistoryRequestVo))
                .stream()
                .map(ReadMoneyHistoryResponseDto::toVo)
                .toList();

        return new BaseResponseEntity<>(result);
    }
}
