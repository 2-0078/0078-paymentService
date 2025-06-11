package com.pieceofcake.paymentservice.payment.presentation;

import com.pieceofcake.paymentservice.common.entity.BaseResponseEntity;
import com.pieceofcake.paymentservice.payment.application.PaymentService;
import com.pieceofcake.paymentservice.payment.dto.in.ConfirmPaymentRequestDto;
import com.pieceofcake.paymentservice.payment.dto.in.CreatePaymentRequestDto;
import com.pieceofcake.paymentservice.payment.vo.in.ConfirmPaymentRequestVo;
import com.pieceofcake.paymentservice.payment.vo.in.CreatePaymentRequestVo;
import com.pieceofcake.paymentservice.payment.vo.out.ConfirmPaymentResponseVo;
import com.pieceofcake.paymentservice.payment.vo.out.CreatePaymentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "payment CREATE API", description = "결제 요청 api 입니다.")
    @PostMapping("/create")
    public BaseResponseEntity<CreatePaymentResponseVo> addPayment(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody CreatePaymentRequestVo createPaymentRequestVo
    ) {
        CreatePaymentResponseVo result = paymentService.createPayment(CreatePaymentRequestDto.from(
                createPaymentRequestVo, memberUuid)).toVo();
        return new BaseResponseEntity<>(result);
    }

    @Operation(summary = "payment CONFIRM API", description = "결제 확인(toss) api 입니다.")
    @PostMapping("/confirm")
    public BaseResponseEntity<ConfirmPaymentResponseVo> confirmPayment(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody ConfirmPaymentRequestVo confirmPaymentRequestVo
            ) {

        ConfirmPaymentResponseVo confirmPaymentResponseVo = paymentService.confirmPayment(ConfirmPaymentRequestDto.from(confirmPaymentRequestVo)).toVo();
        return new BaseResponseEntity<>(confirmPaymentResponseVo);
    }
}
