package com.pieceofcake.paymentservice.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentCustomer is a Querydsl query type for PaymentCustomer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentCustomer extends EntityPathBase<PaymentCustomer> {

    private static final long serialVersionUID = 2018921282L;

    public static final QPaymentCustomer paymentCustomer = new QPaymentCustomer("paymentCustomer");

    public final StringPath customerKey = createString("customerKey");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberUuid = createString("memberUuid");

    public QPaymentCustomer(String variable) {
        super(PaymentCustomer.class, forVariable(variable));
    }

    public QPaymentCustomer(Path<? extends PaymentCustomer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentCustomer(PathMetadata metadata) {
        super(PaymentCustomer.class, metadata);
    }

}

