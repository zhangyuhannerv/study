## stream流处理将用`,`拼接的字符串转为Double集合
```java
// 将用','拼接的字符串转为Double集合
List<Double> singlePoint = Arrays.asList(pointStr.split(","))
                                .stream()
                                .map(str -> Double.parseDouble(str.trim()))
                                .collect(Collectors.toList());
```

