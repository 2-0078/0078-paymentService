package com.pieceofcake.paymentservice.payment.brandpay.infrastructure;

import com.pieceofcake.paymentservice.payment.brandpay.entity.BrandpayCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandpayRepository extends JpaRepository<BrandpayCustomer, Long> {

    Optional<BrandpayCustomer> findByMemberUuid(String memberUuid);
    Optional<BrandpayCustomer> findByCustomerKey(String customerKey);
}
