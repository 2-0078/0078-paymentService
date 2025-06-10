package com.pieceofcake.paymentservice.payment.brandpay.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.common.exception.BaseException;
import com.pieceofcake.paymentservice.payment.brandpay.dto.AccessTokenResponseDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.RegisterCardDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardCallbackRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.out.RegisterCardResponseDto;
import com.pieceofcake.paymentservice.payment.brandpay.entity.BrandpayCustomer;
import com.pieceofcake.paymentservice.payment.brandpay.infrastructure.BrandpayRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandpayServiceImpl implements BrandpayService {

    @Value("${toss.payments.client-key}")
    private String clientKey;
    @Value("${toss.payments.base-url}")
    private String tossBaseUrl;
    @Value("${toss.payments.secret-key}")
    private String secretKey;
    // secretKey를 Base64 인코딩한 값을 캐싱 (인증 헤더용)
    private String basicAuthHeader;

    private final BrandpayRepository brandpayRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        // Secret Key를 Base64 인코딩 (secretKey + ":" 형태)
        String toEncode = secretKey + ":";
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(toEncode.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public RegisterCardResponseDto registerCard(RegisterCardRequestDto registerCardRequestDto) {
        // brandpayCustomer가 존재하는지 확인
        BrandpayCustomer brandpayCustomer = brandpayRepository.findByMemberUuid(registerCardRequestDto.getMemberUuid())
                .orElse(null);

        String customerKey;
        if (brandpayCustomer != null) {
            // brandpayCustomer가 존재하면 새로 생성
            customerKey = brandpayCustomer.getCustomerKey();
        } else {
            // brandpayCustomer가 존재하지 않으면 새로 생성
            customerKey = UUID.randomUUID().toString();  // 무작위 UUID로 생성

            RegisterCardDto registerCardDto = RegisterCardDto.builder()
                    .memberUuid(registerCardRequestDto.getMemberUuid())
                    .customerKey(customerKey)
                    .build();

            BrandpayCustomer newBrandpayCustomer = BrandpayCustomer.builder()
                    .memberUuid(registerCardRequestDto.getMemberUuid())
                    .customerKey(customerKey)
                    .build();

            brandpayRepository.save(newBrandpayCustomer);
        }
        // 고객 키와 클라이언트 키를 사용하여 응답 DTO 생성
        return RegisterCardResponseDto.of(customerKey, clientKey);
    }

    @Override
    public void registerCardCallback(
            RegisterCardCallbackRequestDto registerCardCallbackRequestDto
    ) {
        // 1. customerKey 검증 (우리 서비스에서 발급한 key인지 확인)
        BrandpayCustomer brandpayCustomer = brandpayRepository.findByCustomerKey(
                registerCardCallbackRequestDto.getCustomerKey()
        ).orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CUSTOMER_KEY)
        );

        String customerKey = brandpayCustomer.getCustomerKey();
        String code = registerCardCallbackRequestDto.getCode();

        // 2. Toss API 호출: Access Token 발급 (AuthorizationCode 사용)
        try {

            String url = tossBaseUrl + "/v1/brandpay/authorizations/access-token";
            // 요청 JSON 생성
            ObjectNode requestBody = objectMapper.createObjectNode()
                    .put("grantType", "AuthorizationCode")
                    .put("customerKey", customerKey)
                    .put("code", code);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", basicAuthHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody.toString(), headers);
            ResponseEntity<AccessTokenResponseDto> response = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity, AccessTokenResponseDto.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Failed to issue access token");
            }

            AccessTokenResponseDto accessTokenResponseDto = response.getBody();
            log.info("accessTokenResponseDto: {}", accessTokenResponseDto);

            // 발급된 토큰을 brandpayCustomer에 저장
            BrandpayCustomer brandpayCustomerWithAccessToken = RegisterCardDto.updatedEntityAccessToken(
                    brandpayCustomer, accessTokenResponseDto);

            brandpayRepository.save(brandpayCustomerWithAccessToken);
        } catch (Exception e) {
            // 토큰 발급 실패 처리
            throw new BaseException(BaseResponseStatus.FAILED_TO_GET_TOSS_ACCESS_TOKEN);
        }

        // 3. 결제수단 조회 API 호출하여 methodKey 확보 (Basic 인증 방식)
        try {
            String methodKey;

            String url = tossBaseUrl + "/v1/brandpay/payments/methods/" + customerKey;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", basicAuthHeader);
            HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    url, HttpMethod.GET, httpEntity, JsonNode.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Failed to get payment methods");
            }
            JsonNode body = response.getBody();
            // 응답 JSON에서 첫 번째 카드의 methodKey 추출
            JsonNode cards = body.get("cards");
            if (cards != null && cards.size() > 0) {
                methodKey = cards.get(0).get("methodKey").asText();
            } else {
                throw new RuntimeException("No cards found for customer");
            }

            // 4. 결제수단 등록 (brandpayCustomer에 저장)
            BrandpayCustomer brandpayCustomerWithMethodKey = RegisterCardDto.updatedEntityMethodKey(
                    brandpayCustomer, methodKey);

            brandpayRepository.save(brandpayCustomerWithMethodKey);

        }catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_GET_TOSS_METHOD_KEY);

        }


    }

}
