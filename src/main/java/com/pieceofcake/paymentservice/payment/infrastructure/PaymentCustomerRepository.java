package com.pieceofcake.paymentservice.payment.infrastructure;

import com.pieceofcake.paymentservice.payment.entity.PaymentCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentCustomerRepository extends JpaRepository<PaymentCustomer, Long> {

    Optional<PaymentCustomer> findCustomerKeyByMemberUuid(String memberUuid);

    Boolean existsByMemberUuid(String memberUuid);
}
