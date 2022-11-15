常用的DATE_FORMAA()格式
```sql
DATE_FORMAT(updatetime,'%Y-%m-%d') -- 把mysql的datetime格式化成2021-09-23的字符串格式
DATE_FORMAT(updatetime,'%Y-%m-%d %H:%i:%S') -- 把mysql的datetime格式化成2021-09-07 09:30:37的字符串格式
```

常用的STR_TO_DATE()格式
```sql
STR_TO_DATE('2015-09-01 00:00:00','%Y-%m-%d %H:%i:%s') -- 把字符串转为datetimeg
```