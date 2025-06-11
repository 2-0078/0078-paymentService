package com.pieceofcake.paymentservice.payment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.common.exception.BaseException;
import com.pieceofcake.paymentservice.money.application.MoneyService;
import com.pieceofcake.paymentservice.money.dto.CreateMoneyDto;
import com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType;
import com.pieceofcake.paymentservice.payment.dto.in.ConfirmPaymentRequestDto;
import com.pieceofcake.paymentservice.payment.dto.in.CreatePaymentRequestDto;
import com.pieceofcake.paymentservice.payment.dto.out.ConfirmPaymentResponseDto;
import com.pieceofcake.paymentservice.payment.dto.out.CreatePaymentResponseDto;
import com.pieceofcake.paymentservice.payment.entity.Payment;
import com.pieceofcake.paymentservice.payment.entity.enums.PaymentStatus;
import com.pieceofcake.paymentservice.payment.infrastructure.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    @Value("${toss.payments.base-url}")
    private String baseUrl;

    @Value("${toss.payments.widget-secret-key}")
    private String widgetSecretKey;

    private final PaymentRepository paymentRepository;
    private final PaymentCustomerService paymentCustomerService;
    private final MoneyService moneyService;

    @Override
    public CreatePaymentResponseDto createPayment(CreatePaymentRequestDto createPaymentRequestDto) {

        // customerKey 있으면 받아오고, 없으면 만들고
        String customerKey = paymentCustomerService.getCustomerKey(createPaymentRequestDto.getMemberUuid());

        // Payment 생성 로직
        Payment payment = createPaymentRequestDto.toEntity();
        paymentRepository.save(payment);

        // 반환
        return CreatePaymentResponseDto.from(payment, customerKey);
    }

    @Override
    public ConfirmPaymentResponseDto confirmPayment(ConfirmPaymentRequestDto confirmPaymentRequestDto) {
        String url = baseUrl + "/v1/payments/confirm";

        Payment payment = paymentRepository.findByPaymentUuid(confirmPaymentRequestDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("결제 정보가 없습니다."));

        // Authorization 헤더 생성
        String encryptedSecretKey = "Basic " +
                java.util.Base64.getEncoder().encodeToString((widgetSecretKey + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", encryptedSecretKey);
        headers.set("Content-Type", "application/json");

        // 요청 데이터 설정 (toss에 보낼 api)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("orderId", confirmPaymentRequestDto.getOrderId());
        requestBody.put("paymentKey", confirmPaymentRequestDto.getPaymentKey());
        requestBody.put("amount", confirmPaymentRequestDto.getAmount());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        try {
            // 이미 인증 끝난 결제는 다시 인증할 수 없음
            if (payment.getStatus() != PaymentStatus.READY) {
                throw new BaseException(BaseResponseStatus.ALREADY_PAYMENT_DONE);
            }
            // toss api 호출
            ResponseEntity<String> response = restTemplate.postForEntity(
                    url, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);

            // payment 갱신
            Payment updatedPayment = Payment.builder()
                    .id(payment.getId())
                    .memberUuid(payment.getMemberUuid())
                    .paymentUuid(payment.getPaymentUuid())
                    .paymentCode(payment.getPaymentCode())
                    .amount(payment.getAmount())
                    .status(PaymentStatus.valueOf((String) responseBody.get("status")))
                    .failReason(responseBody.get("failure") != null ? (String) responseBody.get("failure") : null)
                    .approvedAt(responseBody.get("approvedAt") != null ? OffsetDateTime.parse((String) responseBody.get("approvedAt")).toLocalDateTime() : null)
                    .method(responseBody.get("method") != null ? (String) responseBody.get("method") : payment.getMethod())
                    .orderName((String) responseBody.get("orderName"))
                    .build();

            paymentRepository.save(updatedPayment);

            // money 테이블에 예치금 추가
            CreateMoneyDto createMoneyDto = CreateMoneyDto.builder()
                    .memberUuid(updatedPayment.getMemberUuid())
                    .amount(updatedPayment.getAmount())
                    .isPositive(true) // 예치금이므로 true
                    .historyType(MoneyHistoryType.DEPOSIT)
                    .paymentUuid(updatedPayment.getPaymentUuid())
                    .paymentTime(responseBody.get("approvedAt") != null ? OffsetDateTime.parse((String) responseBody.get("approvedAt")).toLocalDateTime() : null)
                    .paymentMethod(responseBody.get("method") != null ? (String) responseBody.get("method") : payment.getMethod())
                    .paymentStatus(updatedPayment.getStatus().toString())
                    .build();

            moneyService.createMoney(createMoneyDto);

            // 응답 DTO 변환

            return ConfirmPaymentResponseDto.builder()
                    .paymentKey((String) responseBody.get("paymentKey"))
                    .orderId((String) responseBody.get("orderId"))
                    .amount(responseBody.get("totalAmount") != null ? ((Number) responseBody.get("totalAmount")).longValue() : null)
                    .status((String) responseBody.get("status"))
                    .method((String) responseBody.get("method"))
                    .approvedAt((String) responseBody.get("approvedAt"))
                    .build();

        } catch (HttpClientErrorException e) {
            // HTTP 클라이언트 오류 처리
            System.err.println("HTTP 오류%%%%%: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new BaseException(BaseResponseStatus.PAYMENT_CONFIRMATION_FAILED);
        } catch (Exception e) {
            // 일반적인 예외 처리
            System.err.println("결제 승인 중 예외 발생%%%%: " + e.getMessage());
            throw new BaseException(BaseResponseStatus.PAYMENT_CONFIRMATION_EXCEPTION);
        }


    }
}
