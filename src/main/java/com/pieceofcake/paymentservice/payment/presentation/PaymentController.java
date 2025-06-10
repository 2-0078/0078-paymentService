package com.pieceofcake.paymentservice.payment.presentation;

import com.pieceofcake.paymentservice.common.entity.BaseResponseEntity;
import com.pieceofcake.paymentservice.payment.application.PaymentService;
import com.pieceofcake.paymentservice.payment.dto.in.CreatePaymentRequestDto;
import com.pieceofcake.paymentservice.payment.vo.in.CreatePaymentRequestVo;
import com.pieceofcake.paymentservice.payment.vo.out.CreatePaymentResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public BaseResponseEntity<CreatePaymentResponseVo> addPayment(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody CreatePaymentRequestVo createPaymentRequestVo
    ) {
        log.info("$$$$$$$$$$$$ {}", createPaymentRequestVo);
        CreatePaymentResponseVo result = paymentService.createPayment(CreatePaymentRequestDto.from(
                createPaymentRequestVo, memberUuid)).toVo();
        return new BaseResponseEntity<>(result);
    }
}
