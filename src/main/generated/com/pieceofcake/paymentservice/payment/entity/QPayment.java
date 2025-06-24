package com.pieceofcake.paymentservice.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 1397222212L;

    public static final QPayment payment = new QPayment("payment");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> approvedAt = createDateTime("approvedAt", java.time.LocalDateTime.class);

    public final StringPath failReason = createString("failReason");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath method = createString("method");

    public final StringPath orderName = createString("orderName");

    public final StringPath paymentCode = createString("paymentCode");

    public final StringPath paymentUuid = createString("paymentUuid");

    public final EnumPath<com.pieceofcake.paymentservice.payment.entity.enums.PaymentStatus> status = createEnum("status", com.pieceofcake.paymentservice.payment.entity.enums.PaymentStatus.class);

    public QPayment(String variable) {
        super(Payment.class, forVariable(variable));
    }

    public QPayment(Path<? extends Payment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayment(PathMetadata metadata) {
        super(Payment.class, metadata);
    }

}

