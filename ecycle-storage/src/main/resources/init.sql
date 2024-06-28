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

