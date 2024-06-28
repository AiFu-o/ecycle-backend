create table ecycle_commodity_category
(
    id                     varchar(36)                          not null comment 'id'
        primary key,
    name                   varchar(100)                         null comment '名称',
    title                  varchar(100)                         null comment '标题',
    enable                 tinyint(1) default 1                 null comment '是否启用',
    create_time            timestamp  default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time            timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    service_charge_type    varchar(100)                         null comment '服务费类型',
    service_charge_setting decimal(10, 2)                       null comment '服务费设置',
    icon_file_id           varchar(36)                          null comment '分类图标'
)
    comment '商品分类';

create table ecycle_user_address
(
    id              varchar(36)                          not null comment 'id'
        primary key,
    name            varchar(50)                          null comment '姓名',
    phone           varchar(11)                          null comment '手机号',
    province        varchar(100)                         null comment '省',
    province_code   varchar(100)                         null comment '省份编号',
    district        varchar(100)                         null comment '区',
    district_code   varchar(100)                         null comment '区编号',
    city            varchar(100)                         null comment '市',
    city_code       varchar(100)                         null comment '市编号',
    stress          varchar(100)                         null comment '街道',
    stress_code     varchar(100)                         null comment '街道编号',
    address         text                                 null comment '详细地址',
    create_time     timestamp  default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time     timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    creator_id      varchar(36)                          not null comment '创建人',
    default_address tinyint(1) default 0                 not null comment '默认地址',
    constraint ecycle_user_address_ecycle_user_id_fk
        foreign key (creator_id) references ecycle_user (id)
)
    comment '用户地址' row_format = DYNAMIC;

create table ecycle_commodity
(
    id             varchar(36)                         not null comment 'id'
        primary key,
    name           varchar(255)                        null comment '商品名称',
    info           text                                null comment '商品描述',
    category_id    varchar(36)                         null comment '商品种类',
    create_time    timestamp default CURRENT_TIMESTAMP null,
    modify_time    timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    creator_id     varchar(36)                         null comment '创建人id',
    belong_id      varchar(36)                         null comment '附件 id',
    page_views     int       default 0                 null comment '浏览量',
    status         varchar(150)                        null comment '商品状态',
    amount         decimal(16, 2)                      null comment '成交价',
    price          decimal(16, 2)                      null comment '单价',
    quantity       decimal(16, 2)                      null comment '数量',
    service_charge decimal(18, 2)                      null comment '服务费',
    address_id     varchar(36)                         null comment '地址',
    cover_file_id  varchar(36)                         null comment '封面附件 id',
    constraint category
        foreign key (category_id) references ecycle_commodity_category (id),
    constraint ecycle_commodity___fk
        foreign key (address_id) references ecycle_user_address (id)
)
    comment '商品';

create table ecycle_bidding_record
(
    id               varchar(36)                         not null comment 'id'
        primary key,
    bill_code        varchar(255)                        not null comment '订单编号',
    commodity_id     varchar(36)                         not null comment '商品 id',
    creator_id       varchar(36)                         not null comment '创建人 id',
    status           varchar(150)                        not null comment '状态',
    commodity_amount decimal(18, 2)                      not null comment '商品价格',
    service_charge   decimal(18, 2)                      not null comment '服务费',
    create_time      timestamp default CURRENT_TIMESTAMP not null comment '出价时间',
    constraint ecycle_bidding_record_ecycle_commodity_id_fk
        foreign key (commodity_id) references ecycle_commodity (id)
)
    comment '出价记录';

create table ecycle_commodity_favorite
(
    id           varchar(36)                         not null comment 'id'
        primary key,
    user_id      varchar(36)                         not null comment '用户 id',
    commodity_id varchar(36)                         not null comment '商品 id',
    create_time  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '收藏时间',
    constraint ecycle_commodity_favorite_ecycle_commodity_id_fk
        foreign key (commodity_id) references ecycle_commodity (id),
    constraint ecycle_commodity_favorite_ecycle_user_id_fk
        foreign key (user_id) references ecycle_user (id)
)
    comment '商品收藏';

create index ecycle_commodity_favorite_commodity_id_index
    on ecycle_commodity_favorite (commodity_id);

create index ecycle_commodity_favorite_user_id_index
    on ecycle_commodity_favorite (user_id);

create table ecycle_commodity_view_record
(
    id           varchar(36)                         not null comment 'id'
        primary key,
    user_id      varchar(36)                         not null comment '用户 id',
    commodity_id varchar(36)                         not null comment '商品 id',
    view_time    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '浏览时间',
    constraint ecycle_commodity_view_record_ecycle_commodity_id_fk
        foreign key (commodity_id) references ecycle_commodity (id),
    constraint ecycle_commodity_view_record_ecycle_user_id_fk
        foreign key (user_id) references ecycle_user (id)
);

create index ecycle_commodity_view_record_commodity_id_index
    on ecycle_commodity_view_record (commodity_id);

create index ecycle_commodity_view_record_user_id_index
    on ecycle_commodity_view_record (user_id);

create table ecycle_order
(
    id                        varchar(36)                              not null comment 'id'
        primary key,
    bill_code                 varchar(255)                             not null comment '订单编号',
    bidding_id                varchar(36)                              not null comment '出价 id',
    commodity_id              varchar(36)                              not null comment '商品 id',
    creator_id                varchar(36)                              not null comment '创建人 id',
    status                    varchar(150)                             not null comment '状态',
    commodity_amount          decimal(18, 2)                           not null comment '商品价格',
    service_charge_receivable decimal(18, 2)                           not null comment '服务费应收',
    service_charge_received   decimal(18, 2) default 0.00              not null comment '服务费实收',
    create_time               timestamp      default CURRENT_TIMESTAMP not null comment '创建时间',
    service_charge_pay_time   timestamp                                null comment '服务费支付时间',
    seller_id                 varchar(36)                              null comment '出售人',
    arrived_time              timestamp                                null comment '回收商抵达时间',
    finish_time               int                                      null comment '订单完成时间',
    constraint ecycle_order_ecycle_bidding_id_fk
        foreign key (bidding_id) references ecycle_bidding_record (id),
    constraint ecycle_order_ecycle_commodity_id_fk
        foreign key (commodity_id) references ecycle_commodity (id)
);

