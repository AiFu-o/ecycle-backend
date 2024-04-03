create table ecycle_authority
(
    id   varchar(36)  not null comment 'id'
        primary key,
    type varchar(255) null comment '权限类型'
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
    modify_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
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
    username    varchar(255)                        not null comment '用户名',
    password    varchar(255)                        null comment '密码',
    age         int                                 null comment '年龄',
    sex         varchar(50)                         null comment '性别',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间',
    open_id     varchar(255)                        null comment '微信openId'
);

create table ecycle_user_role
(
    id      varchar(36) not null
        primary key,
    user_id varchar(36) null,
    role_id varchar(36) null,
    constraint uk_role
        unique (role_id),
    constraint uk_user
        unique (user_id),
    constraint role_key
        foreign key (role_id) references ecycle_role (id),
    constraint user_key
        foreign key (user_id) references ecycle_user (id)
);

