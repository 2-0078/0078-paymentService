package com.pieceofcake.paymentservice.payment.application;

public interface PaymentCustomerService {
    // 각 사용자 별로 고유한 customer 키를 생성하여 반환
    // toss 결제 위젯에서 상태값을 저장해서 이용자 편의를 늘림
    String getCustomerKey(String memberId);
}
