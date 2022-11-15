二者默认值都设置为CURRENT_TIMESTAMP(DEFAULT CURRENT_TIMESTAMP)，保证插入时记录时间

update_time勾选上根据当前时间戳更新(ON UPDATE CURRENT_TIMESTAMP)，保证更新时记录时间

参考sql
```sql
CREATE TABLE `mytest` (
    `text` varchar(255) DEFAULT '' COMMENT '内容',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

注意：采用数据库层面的策略不支持切换数据库，如果想要开发的应用兼容各种类型的数据库，那么就不能设置数据库层面的策略。而是应该使用应用层的各种orm框架自动填充策略