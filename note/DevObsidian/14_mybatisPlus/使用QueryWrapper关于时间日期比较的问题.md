首先明确。springmvc默认不支持将前台传过来的日期/日期时间字符串在到达controller层之前直接转成Date/LocalDate/LocalDateTime类型的，所以接收还是要用字符串类型接收

mp的条件构造器不支持时间日期字符串与mysql的date/datetime类型的字段的比较

所以在比较的时候。要这么写
```java
//query:startDate->String,endDate->String，例子：2020-08-01
//jcrq:mysql->date类型的

// 应该同样适用2020-08-01 08:00:00的字符串和mysql中datetime类型的比较

// 可以这么理解。统一转成时间戳再进行比较

QueryWrapper<TSjfxJcjl> qw = new QueryWrapper<>();
qw
 	.apply(!StringUtils.isEmpty(query.getStartDate()),
         "UNIX_TIMESTAMP(jcrq) >= UNIX_TIMESTAMP('" + query.getStartDate() + "')")
  .apply(!StringUtils.isEmpty(query.getEndDate()),
         "UNIX_TIMESTAMP(jcrq) <= UNIX_TIMESTAMP('" + query.getEndDate() + "')")
```