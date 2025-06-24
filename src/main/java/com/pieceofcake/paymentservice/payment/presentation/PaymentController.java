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

    @Operation(
            summary = "payment CREATE API",
            description = "toss 결제 요청 api 입니다.\n\n" +
                    "결제 요청 시, `CreatePaymentRequestVo` 객체를 요청 본문에 포함해야 합니다.\n\n" +
                    "해당 api의 response 값들을 toss payments 결제위젯 SDK에 넣어서 결제를 진행합니다.\n\n" +
                    "그러면 결제 완료 후, 자동으로 toss SDK의 화면에서 저희가 설정한 redirect URL로 이동합니다.\n\n" +
                    "이 때 redirect URL의 path variable로 어떤 값들이 넘어옵니다. 그 값을 아래의 'confirm api'에 담아 backend에 전달하면 됩니다..\n\n" +
                    "요청 본문 예시:\n" +
                    "{\n" +
                    "  \"amount\": 10000,\n" +
                    "  \"orderName\": \"상품명\"\n" +
                    "}"
    )
    @PostMapping("/create")
    public BaseResponseEntity<CreatePaymentResponseVo> addPayment(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody CreatePaymentRequestVo createPaymentRequestVo
    ) {
        CreatePaymentResponseVo result = paymentService.createPayment(CreatePaymentRequestDto.from(
                createPaymentRequestVo, memberUuid)).toVo();
        return new BaseResponseEntity<>(result);
    }

    @Operation(
            summary = "payment CONFIRM API",
            description = "결제 확인(toss) api 입니다.\n\n" +
                    "결제 완료 후 toss SDK에서 redirect URL로 이동하면, 해당 URL의 path variable로 결제 정보가 담겨서 넘어옵니다.\n\n" +
                    "그 값을 `ConfirmPaymentRequestVo` 객체에 담아서 요청 본문에 포함해야 합니다.\n\n" +
                    "이후 backend와 toss 통신을 통해 해당 결제를 검증하고, 실제 결제가 진행됩니다.\n\n" +
                    "요청 본문 예시:\n" +
                    "{\n" +
                    "  \"paymentKey\": \"toss-payment-key\",\n" +
                    "  \"orderId\": \"order-id\"\n" +
                    "}"
    )
    @PostMapping("/confirm")
    public BaseResponseEntity<ConfirmPaymentResponseVo> confirmPayment(
            @RequestHeader(value = "X-Member-Uuid") String memberUuid,
            @RequestBody ConfirmPaymentRequestVo confirmPaymentRequestVo
            ) {

        ConfirmPaymentResponseVo confirmPaymentResponseVo = paymentService.confirmPayment(ConfirmPaymentRequestDto.from(confirmPaymentRequestVo)).toVo();
        return new BaseResponseEntity<>(confirmPaymentResponseVo);
    }
}
