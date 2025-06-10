package com.pieceofcake.paymentservice.payment.brandpay.presentation;

import com.pieceofcake.paymentservice.common.entity.BaseResponseEntity;
import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.payment.brandpay.application.BrandpayService;
import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardCallbackRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.vo.in.RegisterCardCallbackRequestVo;
import com.pieceofcake.paymentservice.payment.brandpay.vo.in.RegisterCardRequestVo;
import com.pieceofcake.paymentservice.payment.brandpay.vo.out.RegisterCardResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/v1/brandpay")
@RequiredArgsConstructor
public class BrandpayController {
    private final BrandpayService brandpayService;

    @PostMapping("/register")
    public BaseResponseEntity<RegisterCardResponseVo> registerCard(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid
    ) {
        RegisterCardRequestVo registerCardRequestVo = RegisterCardRequestVo.of(memberUuid);
        RegisterCardResponseVo registerCardResponseVo = brandpayService
                .registerCard(RegisterCardRequestDto.from(registerCardRequestVo)).toVo();

        return new BaseResponseEntity<>(registerCardResponseVo);
    }

    @PostMapping("/callback")
    public BaseResponseEntity<Void> registerCardCallback(
            RegisterCardCallbackRequestVo registerCardCallbackRequestVo
    ) {
//        RegisterCardCallbackRequestVo registerCardCallbackRequestVo = RegisterCardCallbackRequestVo.builder()
//                .customerKey(customerKey)
//                .code(code)
//                .build();
        log.info("Brandpay register card callback: @@@@@@@@@{}", registerCardCallbackRequestVo.getCode());
        brandpayService.registerCardCallback(RegisterCardCallbackRequestDto.from(registerCardCallbackRequestVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
