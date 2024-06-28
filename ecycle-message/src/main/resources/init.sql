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

