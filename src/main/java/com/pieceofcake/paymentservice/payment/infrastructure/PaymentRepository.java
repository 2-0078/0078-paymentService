package com.pieceofcake.paymentservice.payment.infrastructure;

import com.pieceofcake.paymentservice.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
