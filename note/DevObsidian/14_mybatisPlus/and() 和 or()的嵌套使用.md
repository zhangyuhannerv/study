and 里面嵌套 or 如下使用
```java
QueryWrapper<ErrorData> ew = new QueryWrapper<>();
ew.eq("dcjh_id", csrwInfo.getCsrwID()).and(wrapper -> wrapper.eq("wtlx", "0").or().eq("wtlx", "1"));// where dcjh_id = '' and (wtlx = '0' or wtlx = '1'),注意：这里是一个lambda表达式
List<ErrorData> dataList = errorDataService.list(ew);
```