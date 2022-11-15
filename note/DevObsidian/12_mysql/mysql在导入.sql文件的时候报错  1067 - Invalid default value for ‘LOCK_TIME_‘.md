推荐使用以下的方式永久修改

编辑mysql的配配置文件 my.cnf

在\[mysqld\]下面添加如下列：

```properties
sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
```
