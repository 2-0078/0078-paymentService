package com.pieceofcake.paymentservice.payment.application;

import com.pieceofcake.paymentservice.payment.dto.in.CreatePaymentRequestDto;
import com.pieceofcake.paymentservice.payment.dto.out.CreatePaymentResponseDto;
import com.pieceofcake.paymentservice.payment.entity.Payment;
import com.pieceofcake.paymentservice.payment.infrastructure.PaymentCustomerRepository;
import com.pieceofcake.paymentservice.payment.infrastructure.PaymentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final PaymentCustomerService paymentCustomerService;

    public CreatePaymentResponseDto createPayment(CreatePaymentRequestDto createPaymentRequestDto) {

        // customerKey 있으면 받아오고, 없으면 만들고
        String customerKey = paymentCustomerService.getCustomerKey(createPaymentRequestDto.getMemberUuid());

        // Payment 생성 로직
        Payment payment = createPaymentRequestDto.toEntity();
        paymentRepository.save(payment);

        // 반환
        return CreatePaymentResponseDto.from(payment, customerKey);
    }
}
