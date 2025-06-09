package com.pieceofcake.paymentservice.payment.brandpay.application;

import com.pieceofcake.paymentservice.payment.brandpay.dto.RegisterCardDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.in.RegisterCardRequestDto;
import com.pieceofcake.paymentservice.payment.brandpay.dto.out.RegisterCardResponseDto;
import com.pieceofcake.paymentservice.payment.brandpay.entity.BrandpayCustomer;
import com.pieceofcake.paymentservice.payment.brandpay.infrastructure.BrandpayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandpayServiceImpl implements BrandpayService {

    @Value("${toss.payments.client-key}")
    private String clientKey;

    private final BrandpayRepository brandpayRepository;

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

}
