create table ecycle_pay_order
(
    id             varchar(36)                         not null comment 'id'
        primary key,
    version        int       default 0                 not null comment '乐观锁版本',
    bill_code      varchar(255)                        null comment '订单号',
    order_type     varchar(100)                        not null comment '订单类型',
    order_id       varchar(36)                         not null comment '订单 id',
    transaction_id varchar(255)                        null comment '第三方平台交易流水号',
    payment_method varchar(255)                        not null comment '支付方式',
    amount         int       default 0                 null comment '支付金额',
    refund_amount  int       default 0                 null comment '退款金额',
    status         varchar(100)                        null comment '支付状态',
    create_time    timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    pay_time       timestamp                           null comment '支付时间',
    creator_id     varchar(36)                         not null comment '创建人'
)
    comment '支付流水' row_format = DYNAMIC;

create table ecycle_pay_refund_order
(
    id             varchar(36)                         not null comment 'id'
        primary key,
    transaction_id int                                 null comment '第三方平台退款交易流水号',
    bill_code      int                                 null comment '退款单编号',
    status         varchar(100)                        null comment '退款状态',
    amount         int       default 0                 null comment '退款金额',
    pay_order_Id   varchar(36)                         null comment '支付订单 id',
    refund_reason  text                                null comment '退款原因',
    create_time    timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    refund_time    timestamp                           null comment '到账时间',
    creator_id     varchar(36)                         null comment '创建人',
    constraint ecycle_pay_refund_records_ecycle_pay_records_id_fk
        foreign key (pay_order_Id) references ecycle_pay_order (id)
)
    comment '支付退款订单' row_format = DYNAMIC;

