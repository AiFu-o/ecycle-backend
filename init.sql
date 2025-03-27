create table ecycle_authority
(
    id   varchar(36)  not null comment 'id'
        primary key,
    type varchar(255) null comment '权限类型',
    name varchar(255) not null comment '权限名'
);

create table ecycle_authority_action
(
    id     varchar(36)  not null comment 'id'
        primary key,
    name   varchar(255) null comment '权限名',
    title  varchar(255) null comment '权限标题',
    `desc` varchar(255) null comment '权限描述',
    constraint action_authority_onetomany
        foreign key (id) references ecycle_authority (id)
);

create table ecycle_authority_menu
(
    id     varchar(36)  not null comment 'id'
        primary key,
    name   varchar(255) null comment '权限名',
    title  varchar(255) null comment '权限标题',
    `desc` text         null comment '权限描述',
    constraint menu_authority_onetomany
        foreign key (id) references ecycle_authority (id)
);

create table ecycle_role
(
    id          varchar(36)                         not null comment 'id'
        primary key,
    code        varchar(255)                        not null comment '编号',
    name        varchar(255)                        not null comment '名称',
    `desc`      text                                null comment '描述',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
);

create table ecycle_role_authority
(
    id           varchar(36) not null
        primary key,
    role_id      varchar(36) null,
    authority_id varchar(36) null,
    constraint uk_authority
        unique (authority_id),
    constraint uk_role
        unique (role_id),
    constraint key_authority
        foreign key (authority_id) references ecycle_authority (id),
    constraint key_authority_role
        foreign key (role_id) references ecycle_role (id)
);

create table ecycle_user
(
    id          varchar(36)                         not null comment 'id'
        primary key,
    nick_name   varchar(255)                        null comment '昵称',
    profile     text                                null comment '头像',
    username    varchar(255)                        not null comment '用户名',
    password    varchar(255)                        null comment '密码',
    age         int                                 null comment '年龄',
    sex         varchar(50)                         null comment '性别',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间',
    open_id     varchar(255)                        null comment '微信openId',
    telephone   varchar(11)                         null comment '手机号'
);

create table ecycle_provider_apply
(
    id               varchar(36)                         not null comment 'id'
        primary key,
    belong_id        varchar(36)                         null comment '附件',
    id_card          varchar(18)                         not null comment '身份证',
    name             varchar(30)                         not null comment '姓名',
    telephone        varchar(11)                         not null comment '手机号',
    status           varchar(100)                        not null comment '申请单状态',
    approval_message text                                null comment '审批意见',
    create_time      timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    audit_time       timestamp                           null comment '审批时间',
    user_id          varchar(36)                         null comment '小程序用户',
    constraint ecycle_provider_apply_user_id
        foreign key (user_id) references ecycle_user (id)
)
    comment '回收商申请单';

create table ecycle_user_role
(
    id      varchar(36) not null
        primary key,
    user_id varchar(36) null,
    role_id varchar(36) null,
    constraint ecycle_user_role_user_role_uindex
        unique (user_id, role_id) comment '用户角色唯一索引',
    constraint role_key
        foreign key (role_id) references ecycle_role (id),
    constraint user_key
        foreign key (user_id) references ecycle_user (id)
);

create index uk_role
    on ecycle_user_role (role_id);

create index uk_user
    on ecycle_user_role (user_id);


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

create table ecycle_notification
(
    id          varchar(36)                         not null comment 'id'
        primary key,
    content     text                                not null comment '消息内容',
    type        varchar(100)                        not null comment '通知类型',
    receiver_id varchar(36)                         null comment '接收人id',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    is_del      tinyint   default 0                 not null comment '是否删除',
    is_read     tinyint   default 0                 not null comment '是否已读',
    link_id     varchar(36)                         null comment '关联模型 id',
    cover_id    varchar(36)                         null comment '封面图片 id',
    constraint ecycle_notification_ecycle_user_id_fk
        foreign key (receiver_id) references ecycle_user (id)
);

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

create table ecycle_attachment_info
(
    id          varchar(36)                         not null comment 'id'
        primary key,
    category    varchar(100)                        null comment '文件分类',
    file_name   varchar(100)                        null comment '文件名称',
    file_type   varchar(20)                         null comment 'varchar',
    size        bigint                              null comment '文件大小',
    info        text                                null comment '文件描述',
    belong_id   varchar(36)                         null comment 'belongId',
    create_time timestamp default CURRENT_TIMESTAMP null comment '上传时间',
    creator_id  varchar(36)                         null comment '创建人 id',
    address     text                                null comment '存储地址',
    protocol    varchar(100)                        null comment '存储协议'
)
    comment '附件';

