package com.pieceofcake.paymentservice.payment.brandpay.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentBrandpay is a Querydsl query type for PaymentBrandpay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentBrandpay extends EntityPathBase<PaymentBrandpay> {

    private static final long serialVersionUID = -462396608L;

    public static final QPaymentBrandpay paymentBrandpay = new QPaymentBrandpay("paymentBrandpay");

    public final com.pieceofcake.paymentservice.common.entity.QBaseEntity _super = new com.pieceofcake.paymentservice.common.entity.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> approvedAt = createDateTime("approvedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.pieceofcake.paymentservice.payment.brandpay.entity.enums.PaymentMethod> method = createEnum("method", com.pieceofcake.paymentservice.payment.brandpay.entity.enums.PaymentMethod.class);

    public final StringPath paymentKey = createString("paymentKey");

    public final StringPath paymentUuid = createString("paymentUuid");

    public final EnumPath<com.pieceofcake.paymentservice.payment.brandpay.entity.enums.PaymentStatus> status = createEnum("status", com.pieceofcake.paymentservice.payment.brandpay.entity.enums.PaymentStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPaymentBrandpay(String variable) {
        super(PaymentBrandpay.class, forVariable(variable));
    }

    public QPaymentBrandpay(Path<? extends PaymentBrandpay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentBrandpay(PathMetadata metadata) {
        super(PaymentBrandpay.class, metadata);
    }

}

