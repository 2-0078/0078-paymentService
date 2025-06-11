package com.pieceofcake.paymentservice.money.presentation;

import com.pieceofcake.paymentservice.common.entity.BaseResponseEntity;
import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.money.application.MoneyService;
import com.pieceofcake.paymentservice.money.dto.in.ReadMoneyAmountRequestDto;
import com.pieceofcake.paymentservice.money.vo.out.ReadMoneyAmountResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/money")
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @GetMapping()
    public BaseResponseEntity<ReadMoneyAmountResponseVo> getMoney(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid
    ) {
        ReadMoneyAmountResponseVo readMoneyAmountResponseVo = moneyService.readMoneyAmount(
                ReadMoneyAmountRequestDto.of(memberUuid)).toVo();

        return new BaseResponseEntity<>(readMoneyAmountResponseVo);
    }

    
}
