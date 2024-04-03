create table ecycle_commodity_category
(
    id          varchar(36)                          not null comment 'id'
        primary key,
    name        int                                  null comment '名称',
    title       int                                  null comment '标题',
    enable      tinyint(1) default 1                 null comment '是否启用',
    create_time timestamp  default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '商品分类';

create table ecycle_commodity
(
    id          varchar(36)                         not null comment 'id'
        primary key,
    name        varchar(255)                        null comment '商品名称',
    `desc`      text                                null comment '商品描述',
    category_id varchar(36)                         null comment '商品种类',
    create_time timestamp default CURRENT_TIMESTAMP null,
    modify_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    creator_id  varchar(36)                         null comment '创建人id',
    belong_id   varchar(36)                         null comment '附件 id',
    page_views  int                                 null comment '浏览量',
    status      int                                 null comment '商品状态',
    amount      decimal(16, 2)                      null comment '成交价',
    price       decimal(16, 2)                      null comment '单价',
    quantity    decimal(16, 2)                      null comment '数量',
    constraint category
        foreign key (category_id) references ecycle_commodity_category (id)
)
    comment '商品';

create table ecycle_order
(
    id             varchar(36)    not null
        primary key,
    status         varchar(255)   null comment '状态',
    price          decimal(12, 2) null comment '单价',
    quantity       decimal(12, 2) null comment '数量',
    amount         decimal(12, 2) null comment '成交价',
    create_user_id varchar(36)    null comment '创建人id',
    buy_user_id    varchar(36)    null comment '购买人id',
    create_time    datetime       null comment '创建时间',
    pay_time       datetime       null comment '支付时间',
    cancel_time    datetime       null comment '取消时间',
    grab_time      datetime       null comment '抢单完成时间',
    commodity_id   varchar(36)    null comment '商品id'
);

