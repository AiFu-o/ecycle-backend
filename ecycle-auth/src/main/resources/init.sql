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

