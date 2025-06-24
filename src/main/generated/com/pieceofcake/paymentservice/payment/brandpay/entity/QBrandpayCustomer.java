package com.pieceofcake.paymentservice.payment.brandpay.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBrandpayCustomer is a Querydsl query type for BrandpayCustomer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBrandpayCustomer extends EntityPathBase<BrandpayCustomer> {

    private static final long serialVersionUID = 22221446L;

    public static final QBrandpayCustomer brandpayCustomer = new QBrandpayCustomer("brandpayCustomer");

    public final com.pieceofcake.paymentservice.common.entity.QBaseEntity _super = new com.pieceofcake.paymentservice.common.entity.QBaseEntity(this);

    public final StringPath accessToken = createString("accessToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath customerKey = createString("customerKey");

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath methodKey = createString("methodKey");

    public final StringPath refreshToken = createString("refreshToken");

    public final DateTimePath<java.time.LocalDateTime> tokenExpiresAt = createDateTime("tokenExpiresAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBrandpayCustomer(String variable) {
        super(BrandpayCustomer.class, forVariable(variable));
    }

    public QBrandpayCustomer(Path<? extends BrandpayCustomer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBrandpayCustomer(PathMetadata metadata) {
        super(BrandpayCustomer.class, metadata);
    }

}

