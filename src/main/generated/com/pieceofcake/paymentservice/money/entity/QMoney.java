package com.pieceofcake.paymentservice.money.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMoney is a Querydsl query type for Money
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoney extends EntityPathBase<Money> {

    private static final long serialVersionUID = -1502847676L;

    public static final QMoney money = new QMoney("money");

    public final com.pieceofcake.paymentservice.common.entity.QBaseEntity _super = new com.pieceofcake.paymentservice.common.entity.QBaseEntity(this);

    public final StringPath accountHolderName = createString("accountHolderName");

    public final StringPath accountNumber = createString("accountNumber");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final StringPath bankName = createString("bankName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> frozenMoney = createNumber("frozenMoney", Long.class);

    public final EnumPath<com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType> historyType = createEnum("historyType", com.pieceofcake.paymentservice.money.entity.enums.MoneyHistoryType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPositive = createBoolean("isPositive");

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath moneyHistoryDetail = createString("moneyHistoryDetail");

    public final StringPath paymentMethod = createString("paymentMethod");

    public final StringPath paymentStatus = createString("paymentStatus");

    public final DateTimePath<java.time.LocalDateTime> paymentTime = createDateTime("paymentTime", java.time.LocalDateTime.class);

    public final StringPath paymentUuid = createString("paymentUuid");

    public final NumberPath<Long> remainingMoney = createNumber("remainingMoney", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMoney(String variable) {
        super(Money.class, forVariable(variable));
    }

    public QMoney(Path<? extends Money> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMoney(PathMetadata metadata) {
        super(Money.class, metadata);
    }

}

