package com.pieceofcake.paymentservice.payment.application;

import com.pieceofcake.paymentservice.common.entity.BaseResponseStatus;
import com.pieceofcake.paymentservice.common.exception.BaseException;
import com.pieceofcake.paymentservice.payment.entity.PaymentCustomer;
import com.pieceofcake.paymentservice.payment.infrastructure.PaymentCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentCustomerServiceImpl implements PaymentCustomerService{
    private final PaymentCustomerRepository paymentCustomerRepository;

    @Override
    public String getCustomerKey(String memberUuid) {
        String customerKey;

        if (paymentCustomerRepository.existsByMemberUuid(memberUuid)) {
            // customerKey가 있으면 return
            PaymentCustomer paymentCustomer = paymentCustomerRepository.findCustomerKeyByMemberUuid(memberUuid)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_CUSTOMER_KEY));

            customerKey = paymentCustomer.getCustomerKey();
        } else {
            // customerKey가 없으면 생성 후 return
            PaymentCustomer paymentCustomer = PaymentCustomer.builder()
                    .memberUuid(memberUuid)
                    .customerKey(UUID.randomUUID().toString())
                    .build();

            paymentCustomerRepository.save(paymentCustomer);

            customerKey = paymentCustomer.getCustomerKey();
        }

        return customerKey;


    }
}
