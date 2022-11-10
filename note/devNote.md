**张雨晗の研究ノート**

**邮箱：1355166049@qq.com或zhangyuhannerv@gmail.com**

---

[toc]

# 后端

## java

### 开发总结

#### 1.stream流处理将用','拼接的字符串转为Double集合

```java
// 将用','拼接的字符串转为Double集合
List<Double> singlePoint = Arrays.asList(pointStr.split(","))
                                .stream()
                                .map(str -> Double.parseDouble(str.trim()))
                                .collect(Collectors.toList());
```

#### 2.java使用lambda表达式建立子线程任务并阻塞主线程

```java
        // 阻塞主线程的计数器
        CountDownLatch countDownLanch = new CountDownLatch(cycleNum);
       	// 局部的线程池
        ExecutorService executor = Executors.newFixedThreadPool(cycleNum > 4 ? 4 : cycleNum);
      	// cycleNum是要执行子线程的次数
        for (int i = 0; i < cycleNum; i++) {
            int start = i * 10000;
            int num = 10000;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Map<String, Double>> dataListTemp = dataShowMapper.getWaveEchartsCorrectDataNoSparse(csrwId, xb, "0", "0", start, num);
                        for (Map<String, Double> dataMap : dataListTemp) {
                            ZSetOperations.TypedTuple<Map<String, Double>> typedTuple = new DefaultTypedTuple<>(dataMap, dataMap.get("kms"));
                            tuples.add(typedTuple);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
					// 每执行一次子线程（计数器减一）
                   		countDownLanch.countDown();
                    }

                }
            });
        }

        try {
            // 阻塞主线程
            countDownLanch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      	// 子线程都执行完后，关闭局部线程池
        executor.shutdown();

```

#### 3.spring向静态类注入bean

```java
@Component
public class DtjcProjectGeneralReportUtil {
    @Autowired
    private IDataAnalysisService dataAnalysisService;

    private static IDataAnalysisService staticDataAnalysisService;

    @PostConstruct
    public void init() {
        staticDataAnalysisService = dataAnalysisService;
    }

   //  这里是静态方法，该方法请调用静态bean
    public static String getDtcjGeneralReportTestTaskId(String projectId) {
        String TestTaskId = "";
        TestTaskId = staticDataAnalysisService.getReportDcjh(projectId);
        if (TestTaskId == null || "".equals(TestTaskId)) {
            List<Map<String, String>> list = staticDataAnalysisService.getFirstDcjh(projectId);
            if (list == null || list.size() == 0) {
                list = staticDataAnalysisService.getFirstDcjhNoData(projectId);
            }
            if (list != null && list.size() > 0) {
                TestTaskId = list.get(list.size() - 1).get("id").toString();
            }
        }
        return TestTaskId;
    }
}

```

#### 4.java中的各种日期时间类型的操作（包括映射mysql）

**Java中的Timestamp对应mysql中的dateTime类型**

比如：java的bean类型是Timestamp

插入的时候这么写

```java
setUpdateTime(new Timestamp(new Date().getTime()));
```

这样插入完成后。mysql数据库里就是datatime类型的数据了。

也可以分开写如下：

```java
 java.util.Date date = new java.util.Date();   // 获取一个Date对象
 Timestamp timeStamp = new Timestamp(date.getTime());  // 给对象赋值该值插入就行了
```

[**Java：String和Date、Timestamp之间的转换**](https://www.cnblogs.com/mybloging/p/8067698.html)

1. String与Date（java.util.Date）互转

   1. String -> Date

      ```java
      String dateStr = "2010/05/04 12:34:23";  
      Date date = new Date();  
      //注意format的格式要与日期String的格式相匹配  
      DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
      try {  
          date = sdf.parse(dateStr);  
          System.out.println(date.toString());  
      } catch (Exception e) {  
          e.printStackTrace();  
      }  
      ```
   2. Date -> String

      ```java
      	1. 		  String dateStr = "";  
      	2.         Date date = new Date();  
      	3.         //format的格式可以任意  
      	4.         DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
      	5.         DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");  
      	6.         try {  
      	7.             dateStr = sdf.format(date);  
      	8.             System.out.println(dateStr);  
      	9.             dateStr = sdf2.format(date);  
      	10.             System.out.println(dateStr);  
      	11.         } catch (Exception e) {  
      	12.             e.printStackTrace();  
              		} 
      ```
2. String与Timestamp互转

   1. String ->Timestamp

      使用Timestamp的valueOf()方法

      ```java
      	1.         Timestamp ts = new Timestamp(System.currentTimeMillis());  
      	2.         String tsStr = "2011-05-09 11:49:45";  
      	3.         try {  
      	4.             ts = Timestamp.valueOf(tsStr);  
      	5.             System.out.println(ts);  
      	6.         } catch (Exception e) {  
      	7.             e.printStackTrace();  
          		  }  
      ```

      注：String的类型必须形如： yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错！！！

      如果String为其他格式，可考虑重新解析下字符串，再重组~~
   2. Timestamp -> String

      使用Timestamp的toString()方法或者借用DateFormat

      ```java
      	1. Timestamp ts = new Timestamp(System.currentTimeMillis());  
      	2.         String tsStr = "";  
      	3.         DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
      	4.         try {  
      	5.             //方法一  
      	6.             tsStr = sdf.format(ts);  
      	7.             System.out.println(tsStr);  
      	8.             //方法二  
      	9.             tsStr = ts.toString();  
      	10.             System.out.println(tsStr);  
      	11.         } catch (Exception e) {  
      	12.             e.printStackTrace();  
                      }  
      ```

      很容易能够看出来，方法一的优势在于可以灵活的设置字符串的形式。
3. Date（ java.util.Date ）和Timestamp互转

   声明：查API可知，Date和Timesta是父子类关系

   1. Timestamp -> Date

      ```java
      	1. 		  Timestamp ts = new Timestamp(System.currentTimeMillis());  
      	2.         Date date = new Date();  
      	3.         try {  
      	4.             date = ts;  
      	5.             System.out.println(date);  
      	6.         } catch (Exception e) {  
      	7.             e.printStackTrace();  
              	   }  
      ```

      很简单，但是此刻date对象指向的实体却是一个Timestamp，即date拥有Date类的方法，但被覆盖的方法的执行实体在Timestamp中。
   2. Date -> Timestamp

      父类不能直接向子类转化，可借助中间的String~~~~

      注：使用以下方式更简洁

      ```java
         Timestamp ts = new Timestamp(date.getTime());
      ```

#### 5.java代码实现word转pdf的几种方式

1. poi(慢，格式回出问题)
2. openOffice（单线程，不支持并发)
3. jacob(效果好，但是不支持linux）
4. docx4j（没试过)
5. asposeword(效果好，块，但是付费)
6. 其他第三方组件

#### 6.java获得文件路径三种方法的区别

```java
File file = new File(".\\test.txt"); 
System.out.println(file.getPath()); 
System.out.println(file.getAbsolutePath()); 
System.out.println(file.getCanonicalPath()); 
```

输出实例：

```java
.\test.txt 
E:\workspace\Test\.\test.txt 
E:\workspace\Test\test.txt 
```

getPath():

返回的是定义时的路径，可能是相对路径，也可能是绝对路径，这个取决于定义时用的是相对路径还是绝对路径。如果定义时用的是绝对路径，那么使用getPath()返回的结果跟用getAbsolutePath()返回的结果一样

getAbsolutePath():

返回的是定义时的路径对应的相对路径，但不会处理“.”和“..”的情况

getCanonicalPath():

返回的是规范化的绝对路径，相当于将getAbsolutePath()中的“.”和“..”解析成对应的正确的路径

#### 7.java获得当前系统的文件分隔符的方法

```java
String outPath = parentFile.getCanonicalPath() + File.separator + "temp-" + fileName;// File.separator会根据当前的系统自动获得'/'或者'\\'
```

#### 8.java以特定的编码读取文件，以特定的编码写入文件

以下代码是针对BufferedReader和BufferedWriter的

```java
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inF),"GB2312"));// 以GB2312的编码读文件
```

```java
BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));// 以utf-8写文件
```

#### 9.删除单个文件的工具方法

```java
    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }
```

#### 10.删除某个文件夹及其之内的所有文件(需要和上面删除单个文件的方法配合使用)

```java
/**
     * 删除文件夹及之下的所有文件
     *
     * @param dir
     * @return
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }

        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            System.out.println("删除目录" + dir + "失败！");
            return false;
        }
    }
```

#### 11.使用stream根据条件过滤出某个枚举类的的特定枚举类型

```java
/**
     * 获得用户的角色枚举
     *
     * @param roleId
     * @return
     */
public static RoleEnum getUserRole(String roleId) {
    RoleEnum roleEnum = null;
    Optional<RoleEnum> optional = Arrays.stream(RoleEnum.values()).filter(e -> roleId.equals(e.getRoleId())).findFirst();
    if (optional.isPresent()) {
        roleEnum = optional.get();
    }
    return roleEnum;
}


/**
     * 用户角色的枚举类
     *
     */
public enum RoleEnum {
    // 仓储角色
    LABORATORY_PERSON("LABORATORY_PERSON",
                      "实验室人员",
                      "365ef7b96870d8acdd4a016866193278",
                      "laboratoryPersonIndex.html",
                      "goods"),
    EQUIPMENT_MANAGEMENT_PERSON("EQUIPMENT_MANAGEMENT_PERSON",
                                "设备管理员",
                                "34ea46330ea7757982a1bde76d6f33bd",
                                "equipmentManagePersonIndex.html",
                                "goods"),
    NORMAL_PERSON("NORMAL_PERSON",
                  "普通用户",
                  "7d9414e9a89692843acde8870de586d0",
                  "normalPersonIndex.html",
                  "goods"),

    // 业务角色
    BUY_PERSON(
        "BUY_PERSON",
        "采购人员",
        "9cec100a371ab680b1c26284d423a416",
        "buyPersonIndex.html",
        "biz"),
    GOODS_MANAGEMENT_PERSON("GOODS_MANAGEMENT_PERSON",
                            "物资管理员",
                            "d21575130f577013a307a1e5aa8ca1fe",
                            "goodsManagementPersonIndex.html",
                            "biz"),
    SELL_PERSON("SELL_PERSON",
                "出库人员",
                "b65766942f5ec940476c93530ad2eea3",
                "sellPerson.html",
                "biz"),
    SELL_PERSON_LEADER("SELL_PERSON_LEADER",
                       "出库人员领导",
                       "8ac454ce1338c811d7d83b4006a77bc1",
                       "sellPerson.html",
                       "biz"),

    // 管理员
    SUPER_ADMIN("SUPER_ADMIN",
                "超级管理员",
                "superadmin_role",
                "superAdmin.html",
                "sys");


    private String role;
    private String roleName;
    private String roleId;
    private String welcomePage;
    private String module;

    RoleEnum() {
    }

    RoleEnum(String role, String roleName, String roleId, String welcomePage, String module) {
        this.role = role;
        this.roleName = roleName;
        this.roleId = roleId;
        this.welcomePage = welcomePage;
        this.module = module;
    }

}
```

#### 12.后台运行jar包与停止运行

将运行的jar 错误日志信息输出到log.file文件中，然后（>&1）就是继续输出到标准输出(前面加的&，是为了让系统识别是标准输出)，最后一个&,表示在后台运行。

```shell
nohup java -jar 包名.jar  > log.file  2>&1 &
[1] 669 #669表示运行的pid
```

### 异常整理

#### 1.java.lang.UnsupportedOperationException：null 使用List.Add()/List.addALL() 报错

还原现场：

```java
List<Integer> agentTeamIdsList =Arrays.asList(agentIdArray);
agentTeamIdsList.add(123011);
```

将一个Integer类型数组转成List， 上面的Arrays.asList 是可以转成功的；

然后往转成功的list里面继续添加 值；

IDEA里面并没有检测出错误，实则报错：

java.lang.UnsupportedOperationException: null

原因：

Arrays.asList转成的ArrayList实际上跟往常我们创建的new ArrayList是不同的。

这个是Arrays的内部类ArrayList：

![img](https://www.freesion.com/images/372/12fde77461883b51add3859c5bfdc23c.png)

而我们往常使用的

![img](https://www.freesion.com/images/810/64f6f88277254496994cc617bb46e4f2.png)

解决方案：

```java
List<Integer> agentTeamIdsList =new ArrayList<>(Arrays.asList(agentIdArray));
```

### 常用util整理

#### 1.解压

##### 1.使用unzip解压

```java
import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class A {
    public static void main(String[] args) {
        System.out.println(unzip("C:\\Users\\13551\\Desktop\\a\\a.zip", "C:\\Users\\13551\\Desktop\\a"));
    }

    public static boolean unzip(String inFilePath, String outDirPath) {

        // 先对目标文件夹做一些判定
        File destFile = new File(outDirPath);

        if (destFile.isFile()) {// 目标是文件错过
            return false;
        }
        if (!destFile.exists()) {// 目标文件夹不存在，先创建
            destFile.mkdirs();
        }

        // 再对源文件做一些判定
        File sourceFile = new File(inFilePath);

        if (sourceFile.isDirectory() || !sourceFile.exists()) {
            return false;
        }

        String fileName = sourceFile.getName();
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        if (!".zip".equals(fileType)) {// 源文件不是zip格式的
            return false;
        }

        // 一次读取1k
        byte[] buff = new byte[1024];
        int readLen = 0;

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(inFilePath), Charset.forName("GBK"));
                ZipFile zipFile = new ZipFile(sourceFile, Charset.forName("GBK"))) {

            ZipEntry entry = null;
            while (((entry = zin.getNextEntry()) != null)) {// 如果entry不为空，并不在同一个目录下

                if (entry.isDirectory()) {// 如果是文件夹不考虑，直接跳过
                    continue;
                }

                String entryName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);

                File tmp = new File(outDirPath + "/" + entryName);// 解压出的文件路径
                if (!tmp.exists()) {// 如果文件不存在
                    File parentDir = tmp.getParentFile();

                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }

                    try (OutputStream os = new FileOutputStream(tmp); // 将文件目录中的文件放入输出流
                            // 用输入流读取压缩文件中制定目录中的文件
                            InputStream in = zipFile.getInputStream(entry)) {

                        while ((readLen = in.read(buff)) != -1) {// 如有输入流可以读取到数值
                            os.write(buff, 0, readLen);// 输出流写入
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }

                }
                zin.closeEntry();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
```

#### 2.Date类型的相关判断

##### 1.两个日期之间相差的天数，日期为单位（比如相差两秒，可能就相差一天，1.12:23:59：59和13:01:00：00就相差一天）：

```java
/**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
private static int differentDays(Date date1,Date date2) {
  Calendar cal1 = Calendar.getInstance();
  cal1.setTime(date1);

  Calendar cal2 = Calendar.getInstance();
  cal2.setTime(date2);
  int day1= cal1.get(Calendar.DAY_OF_YEAR);
  int day2 = cal2.get(Calendar.DAY_OF_YEAR);

  int year1 = cal1.get(Calendar.YEAR);
  int year2 = cal2.get(Calendar.YEAR);
  if(year1 != year2) {//同一年
    int timeDistance = 0 ;
    for(int i = year1 ; i < year2 ; i ++)
    {
      if(i%4==0 && i%100!=0 || i%400==0)    //闰年
      {
        timeDistance += 366;
      }
      else    //不是闰年
      {
        timeDistance += 365;
      }
    }

    return timeDistance + (day2-day1) ;
  } else {// 不同年
    System.out.println("判断day2 - day1 : " + (day2-day1));
    return day2-day1;
  }
}
```

##### 2.两个日期之间相差的天数，以毫秒数精确计算（比如相差22小时可能是同一天）：

```java
/**
     * 通过秒毫秒数判断两个时间的间隔的天数
     * @param date1
     * @param date2
     * @return
     */
public static int differentDaysByMillisecond(Date date1,Date date2)
{
  int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
  return days;
}
```

##### 3.两个日期是否是同一天（）

```java

public static boolean isSameDay(Date date1, Date date2) {
  LocalDate localDate1 = date1.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();
  LocalDate localDate2 = date2.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();
  return localDate1.isEqual(localDate2);

}
```

另一种方式

```java
public static boolean isSameDay(Date date1, Date date2) {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    return fmt.format(date1).equals(fmt.format(date2));
}
```

判断日期是否是同一天，也可以参考这篇文章，方法比较全：

[判断同一天](https://blog.csdn.net/w605283073/article/details/103335373)

## springboot

### 开发

#### 1.springboot中使用java代码控制事务

1. 代码中控制事务的三种方式

   - 编程式事务：就是直接在代码里手动开启事务，手动提交，手动回滚。优点就是可以灵活控制，缺点就是太麻烦了，太多重复的代码了。
   - 声明式事务：就是使用SpringAop配置事务，这种方式大大的简化了编码。需要注意的是切入点表达式一定要写正确。
   - 注解事务：直接在Service层的方法上面加上@Transactional注解，个人比较喜欢用这种方式。
2. 事务回滚的原因

   在工作中，看过别人写的代码出现了事务不回滚的现象。当然，事务不回滚的都是采用的声明式事务或者是注解事务；编程式事务都是自己写代码手动回滚的，因此是不会出现不回滚的现象。

   再说下声明式事务和注解事务回滚的原理：当被切面切中或者是加了注解的方法中抛出了RuntimeException异常时，Spring会进行事务回滚。默认情况下是捕获到方法的RuntimeException异常，也就是说抛出只要属于运行时的异常（即RuntimeException及其子类）都能回滚；但当抛出一个不属于运行时异常时，事务是不会回滚的。

   下面说说我经常见到的3种事务不回滚的产生原因：

   - （1）声明式事务配置切入点表达式写错了，没切中Service中的方法
   - （2）Service方法中，把异常给try catch了，但catch里面只是打印了异常信息，没有手动抛出RuntimeException异常
   - （3）Service方法中，抛出的异常不属于运行时异常（如IO异常），因为Spring默认情况下是捕获到运行时异常就回滚
3. 如何保证事务回滚

   正常情况下，按照正确的编码是不会出现事务回滚失败的。下面说几点保证事务能回滚的方法

   - （1）如果采用声明式事务，一定要确保切入点表达式书写正确
   - （2）如果Service层会抛出不属于运行时异常也要能回滚，那么可以将Spring默认的回滚时的异常修改为Exception，这样就可以保证碰到什么异常都可以回滚。具体的设置方式也说下。

     - 声明式事务，在配置里面添加一个rollback-for，代码如下

       ```xml
       <tx:method name="update*" propagation="REQUIRED" rollback-for="java.lang.Exception"/> 
       ```
     - 注解事务，直接在注解上面指定，代码如下

       ```java
       @Transactional(rollbackFor=Exception.class)
       ```
   - （3）只有非只读事务才能回滚的，只读事务是不会回滚的
   - （4）如果在Service层用了try catch，在catch里面再抛出一个 RuntimeException异常，这样出了异常才会回滚
   - （5）如果你不喜欢（4）的方式，你还可以直接在catch后面写一句回滚代码**TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();**来实现回滚，这样的话，就可以在抛异常后也能return 返回值；比较适合需要拿到Service层的返回值的场景。具体的用法可以参见考下面的伪代码

     ```java
     /** TransactionAspectSupport手动回滚事务：*/
     @Transactional(rollbackFor = { Exception.class })  
     public boolean test() {  
         try {  
             doDbSomeThing();  
         } catch (Exception e) {  
             e.printStackTrace();   
             //就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
             return false;
         }  
         return true;
     }  
     ```

***几点注意事项:***

***1.接口中A、B两个方法，A无@Transactional标签，B有，上层通过A间接调用B，此时事务不生效***

***2.接口中异常（运行时异常）被捕获而没有被抛出。默认配置下，spring 只有在抛出的异常为运行时 unchecked 异常时才回滚该事务，也就是抛出的异常为RuntimeException 的子类(Errors也会导致事务回滚)，而抛出 checked 异常则不会导致事务回滚 。可通过 @Transactional rollbackFor进行配置。***

***3.多线程下事务管理因为线程不属于 spring 托管，故线程不能够默认使用 spring 的事务,也不能获取spring 注入的 bean 。在被 spring 声明式事务管理的方法内开启多线程，多线程内的方法不被事务控制。
  一个使用了@Transactional 的方法，如果方法内包含多线程的使用，方法内部出现异常，不会回滚线程中调用方法的事务。***

***4.在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚***

[原文链接](https://www.cnblogs.com/zeng1994/p/8257763.html)

#### 2.springboot获取resouces下的文件的输入流InputStream（非配置文件,而是各种word或者excel的模板文件等)

```java
 String filePath = "/excel/1_20210709杭州地铁6号线平稳性_2021_08_28_005001_5S.xlsx";

// 这里io流会自动关闭，无需加finally
// try (InputStream is = new FileInputStream(filePath)) {// 读取绝对路径文件
ClassPathResource classPathResource = new ClassPathResource(filePath);
try (InputStream is = classPathResource.getInputStream()) {// 读取resources下文件方式1（适用于静态)
// try (InputStream is = this.getClass().getResourceAsStream(filePath)) {// 读取相resources下文件方式2(不适用于静态)
  
    // ....流对象各种处理代码
}
```

![图片加载失败，请确认您能连接到github](https://raw.githubusercontent.com/Takatsukun/study/main/img/202110111450956.png)

#### 3.获取相对路径下的配置文件信息

**路径位置如下**

![github连接失败](https://raw.githubusercontent.com/Takatsukun/study/main/img/202111091739023.png)

**配置文件信息**

```properties
ureport.disableFileProvider=false
ureport.debug=true
ureport.disableHttpSessionReportCache=false

ureport.fileStoreDir=D:/hussar/hussar_v8/hussar-web/src/main/resources/ureportfiles/odms
filePath = D:/DevelopKit/FILE
openOfficePath = D:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept="socket,host=127.0.0.1,port=8100;urp;"
pdfPath = D:/PDF/
fpPath = D:/fenpian
pythonPath = D:/pythoncode/
dataReaderName = DWDataReaderLib64.dll
```

**代码示例**

```java
package com.jxdinfo.hussar.util;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName PropertiesUtil
 * @Description 获取文件的配置项, 返回配置文件对象
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class PropertiesUtil {
    @Test
    public void test() {
        Properties properties = getProperties();
        System.out.println(properties.get("filePath"));

    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        try (InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("ureport.properties");) {
            // 使用properties对象加载输入流
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

```

#### 4.springboot下载文件

```java
@RequestMapping("/downLoadFile")
public void downLoadLineStruImportTemplateFile(HttpServletResponse response) {
    // 创建缓冲区
    byte buffer[] = new byte[1024];// 缓冲区大小1k
    int len = 0;
    // 重点就是获取输入流和输出流，还有设置请求头
    try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/lineStruImportTemplate.xlsx");
         OutputStream out = response.getOutputStream()) {
        // 设置头部信息
        response.setHeader(
            "Content-disposition",
            "attachment;filename="
            + new String(("线路结构导入模板.xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
  
        //循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

#### 5.springboot检查文件是否存在

前台

```java
/**
     * 下载文件,传入完成路径，先检查文件是否存在，如果存在就下载
     * @param path
     */
downloadFileWithPath = function (path) {
    $.ajax({
        url: Hussar.ctxPath + '/checkFileExist',
        type: 'get',
        async: true,
        data: {
            path
        },
        success(res) {
            if (res.code === 500) {
                Hussar.valid(res.msg);
                return
            }
            // 如果文件存在，那么就下载文件
            window.location.href = '/downLoadFile?path=' + path
        },
        error() {
            Hussar.valid('查询文件资源失败')
        }
    })
}
```

后台

```java
/**
     * 检查文件是否存在
     *
     * @param path
     * @return
     */
@RequestMapping("/checkFileExist")
@ResponseBody
public Map<String, Object> checkFileExist(String path) {
    try {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                return ReturnBodyUtil.returnError("下载所需的文件不存在");
            } else {
                return ReturnBodyUtil.returnSuccess(null);
            }
        } else {
            return ReturnBodyUtil.returnError("下载所需的文件不存在");
        }
    } catch (Exception e) {
        return ReturnBodyUtil.returnError("查询文件路径失败");
    }
}
```

#### 6.springboot动态加载服务器上的图片

前台

```html
<img src="/loadImg?path='xxx'"/>
```

后台

```java
/**
     * IO流读取存在服务器上的图片
     *
     * @return
     * @throws IOException
     */
@RequestMapping(value = "/loadImg", method = RequestMethod.GET)
public void loadImg(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
    //这里省略掉通过id去读取图片的步骤。
    File file = new File(path);//imgPath为服务器图片地址
    if (file.exists() && file.isFile()) {
        FileInputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];// 一次读取1k
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
```

#### 7.使用ZipFile解压文件夹

解压方法1（递归读取所有文件统一放到输出文件夹下。不再区分层级）

```java
/**
     * 无论压缩文件下有多少层级，所有解压后的文件都统一放在outFileDir文件夹下，且只保留压缩的文件，压缩的文件夹不保留
     *
     * @param inFilePath 压缩文件路径
     * @param outDirPath 解压目录的文件夹
     */
public static boolean unzip(String inFilePath, String outDirPath) {

    File destFile = new File(outDirPath);

    if (destFile.isFile()) {
        return false;
    }

    if (!destFile.exists()) {
        destFile.mkdirs();
    }


    File sourceFile = new File(inFilePath);
    String fileName = sourceFile.getName();
    String fileType = fileName.substring(fileName.lastIndexOf("."));

    if (!".zip".equals(fileType)) {
        return false;
    }


    // 一次读取1k
    byte[] buff = new byte[1024];
    int readLen = 0;

    try (ZipInputStream zin = new ZipInputStream(new FileInputStream(inFilePath), Charset.forName("GBK"));
         ZipFile zipFile = new ZipFile(sourceFile, Charset.forName("GBK"))) {

        ZipEntry entry = null;
        while (((entry = zin.getNextEntry()) != null)) {//如果entry不为空，并不在同一个目录下

            if (entry.isDirectory()) {
                continue;
            }

            String entryName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);

            File tmp = new File(outDirPath + "/" + entryName);//解压出的文件路径
            if (!tmp.exists()) {//如果文件不存在
                File parentDir = tmp.getParentFile();

                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                try (OutputStream os = new FileOutputStream(tmp);//将文件目录中的文件放入输出流
                     //用输入流读取压缩文件中制定目录中的文件
                     InputStream in = zipFile.getInputStream(entry)) {

                    while ((readLen = in.read(buff)) != -1) {//如有输入流可以读取到数值
                        os.write(buff, 0, readLen);//输出流写入
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            }
            zin.closeEntry();
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
```

#### 8.事务的传播行为以及在spring中的配置

一、什么是事务传播行为？
事务传播行为（propagation behavior）指的就是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何运行。

例如：methodA方法调用methodB方法时，methodB是继续在调用者methodA的事务中运行呢，还是为自己开启一个新事务运行，这就是由methodB的事务传播行为决定的。

二、事务的7种传播行为
Spring在TransactionDefinition接口中规定了7种类型的事务传播行为。
事务传播行为是Spring框架独有的事务增强特性。
7种：(required / supports / mandatory / requires_new / not supported / never / nested)

PROPAGATION_REQUIRED：如果当前没有事务，就创建一个新事务，如果当前存在事务，就加入该事务，这是最常见的选择，也是Spring默认的事务传播行为。(required需要，没有新建，有加入)
PROPAGATION_SUPPORTS：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事务执行。（supports支持，有则加入，没有就不管了，非事务运行）
PROPAGATION_MANDATORY：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就抛出异常。（mandatory强制性，有则加入，没有异常）
PROPAGATION_REQUIRES_NEW：创建新事务，无论当前存不存在事务，都创建新事务。（requires_new需要新的，不管有没有，直接创建新事务）
PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。（not supported不支持事务，存在就挂起）
PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。（never不支持事务，存在就异常）
PROPAGATION_NESTED：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则按REQUIRED属性执行。（nested存在就在嵌套的执行，没有就找是否存在外面的事务，有则加入，没有则新建）
对事务的要求程度可以从大到小排序：mandatory / supports / required / requires_new / nested / not supported / never

三、实战
1、描述

外围无事务，内部有事务，外围管不着内部

```java
@Test
void test_PROPAGATION_REQUIRED() {
    // add方法 @Transactional(propagation = Propagation.REQUIRED)
    userService.add(user);
    // add方法 @Transactional(propagation = Propagation.REQUIRED)
    userRoleService.add(userRole);
    //抛异常，不影响上面的add方法执行，外部异常不影响内部
    throw new RuntimeException();
}
```

2、描述

外围方法Propagation.REQUIRED
内部方法Propagation.REQUIRED
修饰的内部方法会加入到外围方法的事务中
内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚

```java
@Transactional  // 默认Required
@Test
void test_PROPAGATION_REQUIRED() {
    // 增加用户表 Required 加入了外部事务
    userService.add(user);
 
    // 增加用户角色表 Required 加入了外部事务
    userRoleService.add(userRole);
 
    //抛异常 所有都回滚
    throw new RuntimeException();
}
```

3、描述

支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事务执行
外围方法没有开启事务，add方法直接无事务执行

```java
@Test
void test_PROPAGATION_SUPPORTS() {
    // 增加用户表 @Transactional(propagation = Propagation.SUPPORTS)
    userService.add(user);
    // 增加用户角色表 @Transactional(propagation = Propagation.SUPPORTS)
    userRoleService.add(userRole);
    //抛异常，当前无事务，直接无事务执行
    throw new RuntimeException();
}
```

4、描述

外围加入事务，默认Propagation.REQUIRED
内部使用Propagation.SUPPORTS
内部发现有事务，加入，外部异常，内部回滚
5、描述

支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就抛出异常
外围不存在事务
内部add方法使用@Transactional(propagation = Propagation.MANDATORY)
内部发现当前没事务，直接抛出异常
其他的都同理，就不一一讲了......
————————————————
原文链接：https://blog.csdn.net/qq_38262266/article/details/108709840

***



B为主方法, C子方法, 操作B的是否有事务, 操作C的传播属性 ,这个地方的情况太多,直接在下面表格中的`本文中的解释`部分说明

```java
@Service
@Slf4j
public class Transaction3ServiceImpl implements Transaction3Service {

    @Autowired
    private Transaction4Service transaction4Service;
    
    @Autowired(required = false)
    private StudentMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class) //如果不存在事务,注释掉此行来表示
    public void B(){
        mapper.saveStudent(new Student("ZZZDC"));
        transaction4Service.C();
        // int zdc = 1/0; //如发生异常用此替代
    }
}
@Service
@Slf4j
public class Transaction4ServiceImpl implements Transaction4Service {

    @Autowired(required = false)
    private StudentMapper mapper;

    @Override 
    @Transactional(rollbackFor = Exception.class) //传播行为会在这里操作
    public void C() {
        mapper.saveStudent(new Student("ZDDDC"));
        //int zdc = 1/0;  如发生异常用此替代
    }
}
链接：https://juejin.cn/post/7032652904498462751
来源：稀土掘金
```

| 传播行为      | 本文中的解释                                                 |
| ------------- | ------------------------------------------------------------ |
| REQUIRED      | 如果B存在事务,则C加入该事务`(如果发生异常,则BC一起回滚)`;如果B不存在事务,则C创建一个新的事务`(B不回滚,如果C发生异常则只有C部分回滚)` |
| SUPPORTS      | 如果B存在事务,则C加入该事务`(如果发生异常,则BC一起回滚)`;如果B不存在事务,则C以非事务的方式继续运行`(BC任何情况都不回滚)` |
| MANDATORY     | 如果B存在事务,则C加入该事务`(如果发生异常,则一起回滚)`;如果B不存在事务,则C抛出异常.`(C直接报错,无事务B不回滚)` |
| REQUIRES_NEW  | 如果B不存在事务,C重新创建一个新的事务`(无事务B发生异常不回滚,有事C发生异常则C回滚)`;如果B存在事务,C挂起B得事务并重新创建一个新的事务`(这是两个事务,自己部分有异常,则自己部分回滚)` |
| NOT_SUPPORTED | 如果B不存在事务,C以非事务的方式运行`(任何情况都不回滚)`;如果B存在事务,C暂停当前的事务并以非事务的方式运行`(B部分报错,则B部分回滚,C不回滚;非事务C部分报错,则都不会回滚)` |
| NEVER         | 如果B不存在事务,C以非事务的方式运行`(任何情况都不回滚)`,如果B存在事务,C则抛出异常`(C报错,B因为异常回滚)` |
| NESTED        | 和REQUIRED效果一样.                                          |



作者：我也不会呀
链接：https://juejin.cn/post/7032652904498462751
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

#### 9.springboot整合druid不支持批量更新的问题

轻骑兵的解决方式

注意url后面多了个allowMultiQueries=true

```yaml
spring:
  ###################  mysql配置  ###################
  datasource:
    url: jdbc:mysql://123.123.122.138:3310/dtjc_sbgl_dev?autoReconnect=true&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2b8&allowMultiQueries=true
    username: root
    password: 123456a?
    db-name: dtjc_sbgl_dev
    filters: log4j,mergeStat
    driverClassName: com.mysql.cj.jdbc.Driver  
    #支持批量更新重点就是这里
    filter:
      wall:
        config:
          multi-statement-allow: true

```

---

网上搜到的解决方式

spring boot集成MyBatis，集成Druid批量更新报错，

原因：Druid的防火墙配置(WallConfig)中变量multiStatementAllow默认为false
解决方案：

开启Druid的防火墙配置(WallConfig)中变量multiStatementAllow，把WallConfig中的multiStatementAllow设置为true即可

集成Druid时关于DruidDataSource配置如下:

@Configuration
public class DataSourcesConfig {

    /**
     * druid初始化
     *
     * @return
     * @throws SQLException
     */
    @Primary //默认数据源 在同样的DataSource中，首先使用被标注的DataSource
    @Bean(name = "dataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource Construction() throws SQLException {
    
    DruidDataSource datasource = new DruidDataSource();
    
    // filter
        List`<Filter>` filters = new ArrayList `<Filter>`();
        WallFilter wallFilter = new WallFilter();
        filters.add(wallFilter);
        datasource.setProxyFilters(filters);
    
    return datasource;
    
    }
    
    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    public WallFilter wallFilter(WallConfig wallConfig) {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig);
        return wallFilter;
    }
    
    @Bean(name = "wallConfig")
    public WallConfig wallConfig() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);//允许一次执行多条语句
        return wallConfig;
    }

}
之后数据库连接后面需要加上allowMultiQueries=true,上面解决的是Druid的拦截，
而在数据库上的配置解决的是数据库服务层面的拦截。
url: jdbc:mysql://192.168.1.9:3306/p?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
到此结束；

另外：

pring boot开发环境下启动无异常，批量更新也成功了，但是在tomcat下运行启动会报错，批量更新可以成功，异常提示如下：Unable to register WallConfig with key wallConfig; nested exception is InstanceAlreadyExistsException:com.alibaba.druid.wall:name=wallConfig,type=WallConfig

解决办法：在SpringBoot项目中配置文件加上spring.jmx.enabled=false

#### 10.springboot使用poi导出带有数据的模版

```java
@Override
public void downloadLjcjImportTemplate(String xianbie, String xingbie, HttpServletResponse response) {
  // 设置返回头
  response.setHeader(
    "Content-disposition",
    "attachment;filename="
    + new String(("路基u型槽沉降模版.xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

  // 从库里查询数据
  QueryWrapper<TTzCjDmjcd> qw = new QueryWrapper<>();
  qw.eq("xianbie", xianbie);
  qw.eq("xingbie", xingbie);
  qw.eq("jcd_lx", "6");
  qw.orderByAsc("jcd_lc");
  List<TTzCjDmjcd> exportList = dmjcdService.list(qw);
  // 利用模版文件的输入流创建Workbook对象
  // 同时获取响应流
  try (Workbook wb = new XSSFWorkbook(
    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("template/ljuxccj.xlsx")));
       OutputStream out = response.getOutputStream()) {
    // 对Workbook进行一些数据的写入
    Sheet sheet = wb.getSheetAt(0);
    for (int i = 0; i < exportList.size(); i++) {
      TTzCjDmjcd item = exportList.get(i);
      Row row = sheet.createRow(i + 1);
      Cell cell = row.createCell(0);
      cell.setCellValue(item.getJcdBh());
    }
    // 将workbook写入到响应流
    wb.write(out);
  } catch (Exception e) {
    e.printStackTrace();
  }
}

// controller层直接调用这个方法，不需要写额外的代码
// 前台通过window.location.href = controller层的地址的方式实现文件下载
```



### 学习

## maven

### 开发

#### 1.关于项目得jar包都正常引用了，但是build时就是提示jar包不存在的解决办法

如题，编译和打包都是正常的，pom文件中依赖存在并且没有报错。找到相应包的引用位置，也能正常访问包中的内容。而且提示的一般都是基础的jar包找不到，比如单元测试用到的jar包等。。。

![](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210713083617.png)

情形一：

其他同事提交代码时把idea中的 .iml 文件也一起提交了，该文件中配置的jdk lib 路径与自己电脑中的该路径不一致。

解决方法很简单，执行一下 maven update 即可，也可以手动修改 .iml 文件中的该路径。

![](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210713083737.png)

情形二：

排除情形一出现的原因，或使用情形一中的方法解决无效时，可以使用以下命令更新不完整依赖：

```shell
mvn -U idea:idea
```

需要注意的是，该命令使用的插件早在13年就已经停止维护，所以有可能出现各种问题，比如我遇到过的空指针异常。

情形三：

使用情形二中的方法解决无效时，可以使用以下方法再次尝试

1. ctrl + alt + shift + s 或 在界面菜单选择 File --> Project Structure
2. 点击 Libraries 找到提示不存在的jar包（这里以junit为例），选中，然后右键打开菜单，选择Convert to Repository Library…
3. 执行 maven update

   ![如图](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210713084001.png)

   一般到此都能解决问题，如果还是解决不了，可能真的是人品问题，那就只能呵呵了。。

   [原文连接](https://www.jb51.net/article/189894.htm)

#### 2.pom文件的一般格式（将普通项目托付给maven管理)

如需将一个java项目托付给maven管理。在项目的根目录下，建个pom.xml把以下内容复制进去。同时**右键把该项目标记为maven项目**(不同的idea版本该操作的名称可能不一样)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!--组id-->
    <groupId>com.zh</groupId>
    <!--唯一标识id-->
    <artifactId>parseStsSwing</artifactId>
    <!--版本号-->
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!--这里面添加各种依赖,比如例子就是poi-->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.17</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.17</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!--这里面添加各种插件,例子是打所有的依赖与文件到一个jar包的插件-->
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

#### 3.项目打成jar包时，将项目下的文件，以及所有的依赖打成一个jar包

例子：在做一个单机的用maven管理的swing项目时，引入了poi，但是打成的jar包里没有poi的依赖，导致用exe4j转换出的exe程序报错（Caused by: java.lang.NoClassDefFoundError）

这种错误就是没有将依赖打包进去导致的，所以最好打包成单个jar包。

解决办法：在maven里加入以下插件

```xml
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
```

在项目的根目录下执行(在执行以下任意操作前别忘了clean。清除以前的target文件夹)

```shell
mvn assembly:assembly
```

或

```shell
install -Dmaven.test.skip=true
```

或

添加了新的插件后，右侧maven的plugins列表会多出个命令选项:**assembly**。鼠标左键双击该命令选项下的第一个命令**assembly:assembly**。

实际上和上面的是一样的。但是推荐这种方式（简单，方便，图形化操作，还能应用上maven在idea里面设置的setting.xml配置)

![github连接失败。图片无法展示](https://raw.githubusercontent.com/Takatsukun/study/main/img/202111051728272.png)

执行成功后会在target文件夹下除了普通的jar外还多出一个以-jar-with-dependencies结尾的JAR包. 这个JAR包就包含了项目所依赖的所有JAR的CLASS.

用这个包含所有CLASS的单独的jar包通过exe4j转出的exe文件就能成功执行

#### 4.部署项目时，在maven库上添加自己手动添加的jar 包

进入到放这个jar包的文件夹，运行cmd

然后根据下面的例子手动自己可以尝试着添加

```shell
mvn install:install-file -Dfile=aspose-words-16.4.0-jdk16.jar -DgroupId=com.aspose.word  -DartifactId=aspose.words -Dversion=16.4.0-jdk16 -Dpackaging=jar -DgeneratePom=true
```

```xml
<dependency>
			<groupId>com.aspose.word</groupId>
			<artifactId>aspose.words</artifactId>
			<version>16.4.0-jdk16</version>
</dependency>
```

### 学习

## mybatis

### 开发

#### 1.mybatis中#和$的使用场景

1. group by 字段 ,order by 字段，表名，字段名，如果是动态的用$
2. limit用#
3. 其他的用#

#### 2.批量插入和批量更新

1. 批量插入

   ```xml
   <insert id="insertTgAfterCorrectData">
           insert into dtjc_tg_after_correct_data(
           id,
           left_low,
           right_low,
           left_point,
           right_point,
           track_distance,
           over_height,
           sj_level,
           trangle_pit,
           hor_acceleration,
           ver_acceleration,
           track_distance_rate,
           tqi,
           dcjh_id,
           file_id,
           xb,
           perid,
           updatetime
           )
           values
           <foreach item="item" index="index" collection="list" separator=",">
               (
               #{item.id},
               #{item.left_low},
               #{item.right_low},
               #{item.left_point},
               #{item.right_point},
               #{item.track_distance},
               #{item.over_height},
               #{item.sj_level},
               #{item.trangle_pit},
               #{item.hor_acceleration},
               #{item.ver_acceleration},
               #{item.track_distance_rate},
               #{item.tqi},
               #{item.dcjh_id},
               #{item.file_id},
               #{item.xb},
               #{item.perid},
               #{item.updatetime}
               )
           </foreach>
       </insert>
   ```
2. 批量更新

   1. 更新多条数据，每条数据都不一样

      背景描述：通常如果需要一次更新多条数据有两个方式，（1）在业务代码中循环遍历逐条更新。（2）一次性更新所有数据（更准确的说是一条sql语句来更新所有数据，逐条更新的操作放到数据库端，在业务代码端展现的就是一次性更新所有数据）。两种方式各有利弊，下面将会对两种方式的利弊做简要分析，主要介绍第二种方式在mybatis中的实现。

      1. 逐条实现（java实现)

         这种方式显然是最简单，也最不容易出错的，即便出错也只是影响到当条出错的数据，而且可以对每条数据都比较可控，更新失败或成功，从什么内容更新到什么内容，都可以在逻辑代码中获取。代码可能像下面这个样子：

         ```java
         updateBatch(List<MyData> datas){
             for(MyData data : datas){
                 try{
                     myDataDao.update(data);//更新一条数据，mybatis中如下面的xml文件的update
                 }
                 catch(Exception e){
                     ...//如果更新失败可以做一些其他的操作，比如说打印出错日志等
                 }
             }
         }
         
         ```

         ```xml
         <!--mybatis中update操作的实现-->
         <update>
             update mydata
             set   ...
             where ...
         </update>
         ```

         这种方式最大的问题就是效率问题，逐条更新，每次都会连接数据库，然后更新，再释放连接资源（虽然通过连接池可以将频繁连接数据的效率大大提高，抗不住数据量大），这中损耗在数据量较大的时候便会体现出效率问题。这也是在满足业务需求的时候，通常会使用上述提到的第二种批量更新的实现（当然这种方式也有数据规模的限制，后面会提到）。
      2. 逐条更新(mybatis实现)

         通过循环，依次执行多条update的sql

         前提条件:

         要实现批量更新，首先得设置mysql支持批量操作，在jdbc链接中需要附加&allowMultiQueries=true属性才行，可能会被阿里的druid给阻挡。需要上网找绕过阻挡得方案

         例如：

         ```yaml
         jdbc: mysql://localhost:3306/dbname?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
         ```

         ```xml
         <update id="updateBatch"  parameterType="java.util.List">  
             <foreach collection="list" item="item" index="index" open="" close="" separator=";">
                 update course
                 <set>
                     name=${item.name}
                 </set>
                 where id = ${item.id}
             </foreach>  
         </update>
         ```

         一条记录update一次，性能比较差，容易造成阻塞。
      3. sql批量更新(主力实现)

         1. 实际实践(传入的是List<Map<String, Object>>)

            务必注意:一定要加where条件,里面的id为需要更新的数据的id;如果不加where条件,则会全部更新,但是需要更新且有数据的更新为传递的数据,没有数据的则更新为null,此时更新出错

            ```xml
            <update id="updateChartParamByAccountAndChartid" parameterType="list">
                    update followme_parameters
                    <trim prefix="set" suffixOverrides=",">
                        <trim prefix="signal_source =case" suffix="end,">
                            <foreach collection="list" item="item" index="index">
                                <if test="item.signalSource!=null">
                                    when account=#{item.account} and chart_id=#{item.chartId}
                                     then #{item.signalSource}
                                </if>
                            </foreach>
                        </trim>
                        <trim prefix="rate =case" suffix="end,">
                            <foreach collection="list" item="item" index="index">
                                <if test="item.rate!=null">
                                    when account=#{item.account} and chart_id=#{item.chartId}
                                    then #{item.rate}
                                </if>
                            </foreach>
                        </trim>
                    </trim>
                    where id in
                    <foreach collection="list" item="item" index="index" separator="," open="(" close=")">
                        #{item.id}
                    </foreach>
                </update>
            
            ```

            另外文章的样板

            ```xml
            <update id="updateBatch" parameterType="list">
                 update course
                  <trim prefix="set" suffixOverrides=",">
                   <trim prefix="peopleId =case" suffix="end,">
                       <foreach collection="list" item="i" index="index">
                               <if test="i.peopleId!=null">
                                when id=#{i.id} then #{i.peopleId}
                               </if>
                       </foreach>
                    </trim>
                    <trim prefix=" roadgridid =case" suffix="end,">
                       <foreach collection="list" item="i" index="index">
                               <if test="i.roadgridid!=null">
                                when id=#{i.id} then #{i.roadgridid}
                               </if>
                       </foreach>
                    </trim>
            		<trim prefix="type =case" suffix="end," >
                       <foreach collection="list" item="i" index="index">
                               <if test="i.type!=null">
                                when id=#{i.id} then #{i.type}
                               </if>
                       </foreach>
                    </trim>
                    <trim prefix="unitsid =case" suffix="end," >
                        <foreach collection="list" item="i" index="index">
                                <if test="i.unitsid!=null">
                                 when id=#{i.id} then #{i.unitsid}
                                </if>
                        </foreach>
                 </trim>
                </trim>
                where
                <foreach collection="list" separator="or" item="i" index="index" >
                    id=#{i.id}
                </foreach>
            </update>
            
            ```

            [原文链接](https://blog.csdn.net/junehappylove/article/details/82215674)
         2. 下面逐步讲解

            一条sql语句来批量更新所有数据，下面直接看一下在mybatis中通常是怎么写的（去掉mybatis语法就是原生的sql语句了，所有就没单独说sql是怎么写的）

            ```xml
            <update id="updateBatch" parameterType="java.util.List">
                update mydata_table 
                set  status=
                <foreach collection="list" item="item" index="index" 
                    separator=" " open="case ID" close="end">
                    when #{item.id} then #{item.status}
                </foreach>
                where id in
                <foreach collection="list" index="index" item="item" 
                    separator="," open="(" close=")">
                    #{item.id,jdbcType=BIGINT}
                </foreach>
             </update>
            
            ```

            其中when...then...是sql中的"switch" 语法。这里借助mybatis的语法来拼凑成了批量更新的sql，上面的意思就是批量更新id在updateBatch参数所传递List中的数据的status字段。还可以使用实现同样的功能,代码如下:

            ```xml
            <update id="updateBatch" parameterType="java.util.List">
                    update mydata_table
                    <trim prefix="set" suffixOverrides=",">
                        <trim prefix="status =case" suffix="end,">
                            <foreach collection="list" item="item" index="index">
                                 when id=#{item.id} then #{item.status}
                            </foreach>
                        </trim>
                    </trim>
                    where id in
                    <foreach collection="list" index="index" item="item" separator="," open="(" close=")">
                        #{item.id,jdbcType=BIGINT}
                    </foreach>
                </update>
            ```

            属性说明

            - prefix,suffix 表示在trim标签包裹的部分的前面或者后面添加内容
            - 如果同时有prefixOverrides,suffixOverrides 表示会用prefix,suffix覆盖Overrides中的内容。
            - 如果只有prefixOverrides,suffixOverrides 表示删除开头的或结尾的xxxOverides指定的内容。

            上述代码转化成sql如下:

            ```sql
            update mydata_table 
                set status = 
                case
                    when id = #{item.id} then #{item.status}//此处应该是<foreach>展开值
                    ...
                end
                where id in (...);
            
            ```

            当然这是最简单的批量更新实现,有时候可能需要更新多个字段,那就需要将

            ```xml
            <trim prefix="status =case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                      when id=#{item.id} then #{item.status}
                 </foreach>
            </trim>
            ```

            复制拷贝多次,更改prefix和when...then...的内容即可.而如果当需要为某个字段设置默认值的时候可以使用else

            ```xml
            <trim prefix="status =case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                      when id=#{item.id} then #{item.status}
                 </foreach>
                 else default_value
            </trim>
            ```

            还有更常见的情况就是需要对要更新的数据进行判断,只有符合条件的数据才能进行更新,这种情况可以这么做:

            ```xml
            <trim prefix="status =case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                     <if test="item.status !=null and item.status != -1">
                         when id=#{item.id} then #{item.status}
                     </if>
                 </foreach>
            </trim>
            ```

            这样的话只有要更新的list中status != null && status != -1的数据才能进行status更新.其他的将使用默认值更新,而不会保持原数据不变.如果要保持原数据不变呢?即满足条件的更新,不满足条件的保持原数据不变,简单的来做就是再加一个,因为mybatis中没有if...else...语法,但可以通过多个实现同样的效果,如下:

            ```xml
            <trim prefix="status =case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                     <if test="item.status !=null and item.status != -1">
                         when id=#{item.id} then #{item.status}
                     </if>
                     <if test="item.status == null or item.status == -1">
                         when id=#{item.id} then mydata_table.status      //这里就是原数据
                     </if>
                 </foreach>
            </trim>
            ```

            整体批量更新的写法如下:

            ```xml
            <update id="updateBatch" parameterType="java.util.List">
                update mydata_table
                <trim prefix="set" suffixOverrides=",">
                    <trim prefix="status =case" suffix="end,">
                         <foreach collection="list" item="item" index="index">
                             <if test="item.status !=null and item.status != -1">
                                 when id=#{item.id} then #{item.status}
                             </if>
                             <if test="item.status == null or item.status == -1">
                                 when id=#{item.id} then mydata_table.status//原数据
                             </if>
                         </foreach>
                    </trim>
                </trim>
                where id in
                <foreach collection="list" index="index" item="item" separator="," open="(" close=")">
                    #{item.id,jdbcType=BIGINT}
                </foreach>
            </update>
            ```
      4. 批量更新(单个字段,传参list),实际是sql批量更新的简化版本而已

         1. 单个字段方法1

            ```xml
            <update id="updateByBatch" parameterType="java.util.List">
                update t_goods
                set NODE_ID=
                <foreach collection="list" item="item" index="index"
                         separator=" " open="case" close="end">
                  when GOODS_ID=#{item.goodsId} then #{item.nodeId}
                </foreach>
                where GOODS_ID in
                <foreach collection="list" index="index" item="item"
                         separator="," open="(" close=")">
                  #{item.goodsId,jdbcType=BIGINT}
                </foreach>
              </update>
            ```
         2. 单个字段方法2

            ```xml
            <update id="updateByBatch" parameterType="java.util.List">
                UPDATE
                t_goods
                SET NODE_ID = CASE
                <foreach collection="list" item="item" index="index">
                  WHEN GOODS_ID = #{item.goodsId} THEN #{item.nodeId}
                </foreach>
                END
                WHERE GOODS_ID IN
                <foreach collection="list" index="index" item="item" open="(" separator="," close=")">
                  #{item.goodsId}
                </foreach>
              </update>
            ```

            以上单字段更新实际执行：

            ```sql
            UPDATE t_goods SET NODE_ID = CASE WHEN GOODS_ID = ? THEN ? END WHERE GOODS_ID IN ( ? )
            ```
      5. sql批量更新(通过insert实现)

         传入的是List<Map<String,Object>>

         直接运行插入,如果有插入的数据转为更新该条数据

         ```xml
         <insert id="updateChartParamByAccountAndChartid">
             insert into followme_parameters
             (account,chart_id,signal_source,rate)
             values
             <foreach collection="list" separator="," index="index" item="item">
                 (#{item.account},#{item.chartId},#{item.signalSource},#{item.rate})
             </foreach>
             ON duplicate KEY UPDATE
             signal_source=values(signal_source),rate=values(rate) 
         </insert>
         ```
   2. 更新多条数据,更新的内容一样.

      1. 传map/传String

         NODE_ID从map中取出来,goodsIdList是字符串拼接好的(如下面的"1,2,5")

         ```xml
         <update id="updateByBatchPrimaryKey" parameterType="java.util.Map">
             UPDATE t_goods
             SET NODE_ID = #{nodeId}
             WHERE GOODS_ID IN (${goodsIdList})
           </update>
         ```

         实际的sql

         ```sql
         UPDATE t_goods SET NODE_ID = ? WHERE GOODS_ID IN (1,2,5);
         ```
      2. 传map/传list

         NODE_ID从map中取出来,goodsIdList是用list拼接出来的

         ```xml
         <update id="updateByBatchPrimaryKey" parameterType="java.util.Map">
             UPDATE t_goods
             SET NODE_ID = #{nodeId}
             WHERE GOODS_ID IN 
             <foreach collection="list" index="index" item="item" open="(" separator="," close=")">
               #{item.goodsId}
             </foreach>
         </update>
         ```

         实际的sql

         ```sql
         UPDATE t_goods SET NODE_ID = ? WHERE GOODS_ID IN (1,2,5);
         ```

         [原文链接](https://www.cnblogs.com/eternityz/p/12284760.html)

#### 3.Mybatis 实现if -- else --

```xml
 <choose>
                <when test="item.tdName == 'hor_acceleration'">
                    '0' as horAcceleration,
                </when>
                <otherwise>
                    hor_acceleration as horAcceleration,
                </otherwise>
 </choose>
```

#### 4.mybatis判断字符串相等时的注意事项

mybatis 映射文件中，if标签判断字符串相等，两种方式：

因为mybatis映射文件，是使用的ognl表达式，所以在判断字符串sex变量是否是字符串Y的时候，

```xml
<if test="sex=='Y'.toString()">
<if test = 'sex== "Y"'>
```

注意：不能使用

```xml
<if test="sex=='Y'">
and 1=1
</if>
```

因为mybatis会把'Y'解析为字符，所以不能这样写 会报NumberFormatException

MyBatis是使用的OGNL表达式来进行解析的，这个地方有一个坑需要注意下，单引号内有一个字符的情况下，OGNL会将其以 java 中的 char 类型进行解析，那么此时 char 类型与参数 String 类型用等号进行比较的时候结果都是false。解决方案也很简单，就是讲 test 中的单个字符用双引号括起来。

```xml
      <where>
        	/*不行*/
            <if test="qryStr=='Y'">
                and counts=1
            </if>
             /*可以*/
            <if test="qryStr=='Y'.toString()">
                and counts=1
            </if>
                /*可以*/
            <if test='qryStr=="Y"'>
                and counts=2
            </if>
        </where>
```

建议使用外部单引号，里面双引号嵌套的方式。

#### 5.mybatis自带的分页插件的使用

建一个page对象传入前台的page,和limit参数（推荐使用泛型,限定返回的参数类型，例子的话应该是Page\<Map\>）

调用dao或者service层时传入page对象

```java
Page pages = new Page(Integer.valueOf(page), Integer.valueOf(limit));
List<Map> list = dtjcXmGeneralreportService.getCxList(pages, csrwId,xlId,xb,ppbzId,Integer.valueOf(topSpeed));
```

service接口层

```java
List<Map> getCxList(Page pages, String csrwId, String xlId, String xb,String bz,Integer topSpeed);
```

service层

```java
   @Override
    public List<Map> getCxList(Page pages, String csrwId, String xlId, String xb,String bz,Integer topSpeed) {
        return dtjcXmGeneralreportMapper.getCxList(pages, csrwId,xlId,xb,bz,topSpeed);
    }
```

dao层

```java
List<Map> getCxList(@Param("pages") Page pages,@Param("csrwId") String csrwId,@Param("xlId") String xlId,@Param("xb") 
                    String xb,@Param("bz") String bz,@Param("topSpeed") Integer topSpeed);
```

同时写sql的时候不需要专门使用page,只需要正常的写sql，使用参数筛选即可

回到controller里

```java
map.put("count", pages.getTotal());
map.put("data", list);
```

至此是城轨项目的使用page方法

---

以下是mybatis-plus官方的使用参考

[原文链接](https://baomidou.com/guide/page.html)

```xml
<!-- spring xml 方式 -->
<property name="plugins">
    <array>
        <bean class="com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor">
            <property name="sqlParser" ref="自定义解析类、可以没有"/>
            <property name="dialectClazz" value="自定义方言类、可以没有"/>
            <!-- COUNT SQL 解析.可以没有 -->
            <property name="countSqlParser" ref="countSqlParser"/>
        </bean>
    </array>
</property>

<bean id="countSqlParser" class="com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize">
    <!-- 设置为 true 可以优化部分 left join 的sql -->
    <property name="optimizeJoin" value="true"/>
</bean>

```

```java
//Spring boot方式
@Configuration
@MapperScan("com.baomidou.cloud.service.*.mapper*")
public class MybatisPlusConfig {

    // 旧版
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
  
    // 最新版
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
  
}
```

- UserMapper.java 方法内容

```java
public interface UserMapper {//可以继承或者不继承BaseMapper
    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
    IPage<User> selectPageVo(Page<?> page, Integer state);
}
```

- UserMapper.xml 等同于编写一个普通 list 查询，mybatis-plus 自动替你分页

```xml
<select id="selectPageVo" resultType="com.baomidou.cloud.entity.UserVo">
    SELECT id,name FROM user WHERE state=#{state}
</select>
```

- UserServiceImpl.java 调用分页方法

```java
public IPage<User> selectUserPage(Page<User> page, Integer state) {
    // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
    // page.setOptimizeCountSql(false);
    // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
    // 要点!! 分页返回的对象与传入的对象是同一个
    return userMapper.selectPageVo(page, state);
}
```

---

#### 6. mybatis传入集合循环查询并用union组合

实例：接口

```java
/**
     * selectOverrunData:查询一个单次计划某个行别某个速度级下各个超限类型的占比
     *
     * @param testTaskId 测试任务id
     * @param xb         行别
     * @param speedLevel 速度级
     * @param type       超限还是大值
     * @param labelList  通道名称集合
     * @return
     * @author Zhangyuhan
     * @date 2021/7/8 15:03
     */
    List<Map<String, Object>> selectStatisticalInformation(@Param("testTaskId") String testTaskId,
                                                           @Param("xb") String xb,
                                                           @Param("speedLevel") Integer speedLevel,
                                                           @Param("type") String type,
                                                           @Param("labelList") List<String> labelList);
```

实例：接口对应的sql

```xml
<select id="selectStatisticalInformation" resultType="map">
        <foreach collection="labelList" item="item" index="index" separator="UNION">
        SELECT numtab.num AS VALUE,
        concat( round(( numtab.num / numtab.total ) * 100, 2),'%' ) AS NAME
        FROM
            (
            SELECT
            <if test='type == "超限"'>
                count( CASE WHEN cxlx = #{item} THEN 1 END ) AS num ,
            </if>
            <if test='type == "大值"'>
                count( CASE WHEN dzlx = #{item} THEN 1 END ) AS num,
            </if>
            count(*) AS total
            FROM
            <if test='type == "超限"'>
            cxdata_table
            </if>
            <if test='type == "大值"'>
            dzdata_table
            </if>
            WHERE
            dcjh_id = '797cb0e7de3241029b5feb6b1ffa17ca'
            AND xb = '上行'
            <if test='type == "超限"'>
            AND flag = '0'
            </if>
            ) numtab
        </foreach>
    </select>
```

#### 7.mybatis .and() 和 .or()的嵌套使用

and 里面嵌套 or 如下使用

```java
QueryWrapper<ErrorData> ew = new QueryWrapper<>();
ew.eq("dcjh_id", csrwInfo.getCsrwID()).and(wrapper -> wrapper.eq("wtlx", "0").or().eq("wtlx", "1"));// where dcjh_id = '' and (wtlx = '0' or wtlx = '1'),注意：这里是一个lambda表达式
List<ErrorData> dataList = errorDataService.list(ew);
```

#### 8.mybatis批量插入大量数据

1. 思路分析
   批量插入这个问题，我们用 JDBC 操作，其实就是两种思路吧：

   * 用一个 for 循环，把数据一条一条的插入（这种需要开启批处理）。

   * 生成一条插入 sql，类似这种 insert into user(username,address) values(‘aa’,‘bb’),(‘cc’,‘dd’)…。
     到底哪种快呢？

   我们从两方面来考虑这个问题：

   * 插入 SQL 本身执行的效率。

   * 网络 I/O。

   **先说第一种方案，就是用 for 循环循环插入：**

   * 这种方案的优势在于，JDBC 中的 PreparedStatement 有预编译功能，预编译之后会缓存起来，后面的 SQL 执行会比较快并且 JDBC 可以开启批处理，这个批处理执行非常给力。
   * 劣势在于，很多时候我们的 SQL 服务器和应用服务器可能并不是同一台，所以必须要考虑网络 IO，如果网络 IO 比较费时间的话，那么可能会拖慢 SQL 执行的速度。

   **再来说第二种方案，就是生成一条 SQL 插入：**

   * 这种方案的优势在于只有一次网络 IO，即使分片处理也只是数次网络 IO，所以这种方案不会在网络 IO 上花费太多时间。
   * 当然这种方案有好几个劣势，一是 SQL 太长了，甚至可能需要分片后批量处理；二是无法充分发挥 PreparedStatement 预编译的优势，SQL 要重新解析且无法复用；三是最终生成的 SQL 太长了，数据库管理器解析这么长的 SQL 也需要时间。
     所以我们最终要考虑的就是我们在网络 IO 上花费的时间，是否超过了 SQL 插入的时间？这是我们要考虑的核心问题。

2. 数据测试

   **2.1方案1测试**

   接下来我们来做一个简单的测试，批量插入 5 万条数据看下。

   首先准备一个简单的测试表：

   ```sql
   CREATE TABLE `user` (
     `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
     `username` varchar(255) DEFAULT NULL,
     `address` varchar(255) DEFAULT NULL,
     `password` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
   ```

   接下来创建一个 Spring Boot 工程，引入 [MyBatis](https://so.csdn.net/so/search?q=MyBatis&spm=1001.2101.3001.7020) 依赖和 MySQL 驱动，然后 application.properties 中配置一下数据库连接信息：

   ```
   spring.datasource.username=root
   spring.datasource.password=123
   spring.datasource.url=jdbc:mysql:///batch_insert?serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true
   
   大家需要注意，这个数据库连接 URL 地址中多了一个参数 **rewriteBatchedStatements**，这是核心。``
   
   MySQL JDBC 驱动在默认情况下会无视 **executeBatch()** 语句，把我们期望批量执行的一组 sql 语句拆散，一条一条地发给 MySQL 数据库，批量插入实际上是单条插入，直接造成较低的性能。将 **rewriteBatchedStatements** 参数置为 true, 数据库驱动才会帮我们批量执行 SQL。
   
   OK，这样准备工作就做好了。
   
   ## 2.1 方案一测试
   首先我们来看方案一的测试，即一条一条的插入（实际上是批处理）。
   
   首先创建相应的 mapper，如下：
   
   ```cpp
   @Mapper
   public interface UserMapper {
       Integer addUserOneByOne(User user);
   }
   
   ```

   对应的 XML 文件如下：

   ```xml
   <insert id="addUserOneByOne">
       insert into user (username,address,password) values (#{username},#{address},#{password})
   </insert>
   ```

   service 如下：

   ```java
   @Service
   public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
       private static final Logger logger = LoggerFactory.getLogger(UserService.class);
       @Autowired
       UserMapper userMapper;
       @Autowired
       SqlSessionFactory sqlSessionFactory;
   
       @Transactional(rollbackFor = Exception.class)
       public void addUserOneByOne(List<User> users) {
           SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
           UserMapper um = session.getMapper(UserMapper.class);
           long startTime = System.currentTimeMillis();
           for (User user : users) {
               um.addUserOneByOne(user);
           }
           session.commit();
           long endTime = System.currentTimeMillis();
           logger.info("一条条插入 SQL 耗费时间 {}", (endTime - startTime));
       }
   }
   ```

   补充说明：

   虽然是一条一条的插入，但是我们要开启批处理模式（BATCH），这样前前后后就只用这一个 SqlSession，如果不采用批处理模式，反反复复的获取 Connection 以及释放 Connection 会耗费大量时间，效率奇低，这种效率奇低的方式松哥就不给大家测试了。

   接下来写一个简单的测试接口看下：

   ```java
   @RestController
   public class HelloController {
       private static final Logger logger = getLogger(HelloController.class);
       @Autowired
       UserService userService;
       /**
        * 一条一条插入
        */
       @GetMapping("/user2")
       public void user2() {
           List<User> users = new ArrayList<>();
           for (int i = 0; i < 50000; i++) {
               User u = new User();
               u.setAddress("广州：" + i);
               u.setUsername("张三：" + i);
               u.setPassword("123：" + i);
               users.add(u);
           }
           userService.addUserOneByOne(users);
       }
   }
   ```

   写个简单的单元测试：

   ```java
   /**
    * 
    * 单元测试加事务的目的是为了插入之后自动回滚，避免影响下一次测试结果
    * 一条一条插入
    */
   @Test
   @Transactional
   void addUserOneByOne() {
       List<User> users = new ArrayList<>();
       for (int i = 0; i < 50000; i++) {
           User u = new User();
           u.setAddress("广州：" + i);
           u.setUsername("张三：" + i);
           u.setPassword("123：" + i);
           users.add(u);
       }
       userService.addUserOneByOne(users);
   }
   ```

   **经过测试，耗时 901 毫秒，5w 条数据插入不到 1 秒。**

   **2.2方案2测试**

   方案二是生成一条 SQL 然后插入。

   mapper 如下：

   ```java
   @Mapper
   public interface UserMapper {
       void addByOneSQL(@Param("users") List<User> users);
   }
   ```

   对应的 SQL 如下：

   ```xml
   <insert id="addByOneSQL">
       insert into user (username,address,password) values
       <foreach collection="users" item="user" separator=",">
           (#{user.username},#{user.address},#{user.password})
       </foreach>
   </insert>
   ```

   service 如下：

   ```java
   @Service
   public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
       private static final Logger logger = LoggerFactory.getLogger(UserService.class);
       @Autowired
       UserMapper userMapper;
       @Autowired
       SqlSessionFactory sqlSessionFactory;
       @Transactional(rollbackFor = Exception.class)
       public void addByOneSQL(List<User> users) {
           long startTime = System.currentTimeMillis();
           userMapper.addByOneSQL(users);
           long endTime = System.currentTimeMillis();
           logger.info("合并成一条 SQL 插入耗费时间 {}", (endTime - startTime));
       }
   }
   ```

   然后在单元测试中调一下这个方法：

   ```java
   /**
    * 合并成一条 SQL 插入
    */
   @Test
   @Transactional
   void addByOneSQL() {
       List<User> users = new ArrayList<>();
       for (int i = 0; i < 50000; i++) {
           User u = new User();
           u.setAddress("广州：" + i);
           u.setUsername("张三：" + i);
           u.setPassword("123：" + i);
           users.add(u);
       }
       userService.addByOneSQL(users);
   }
   ```

   经过测试，可以看到插入 5 万条数据耗时 1805 毫秒。

   可以看到，生成一条 SQL 的执行效率还是要差一点。

   另外还需要注意，第二种方案还有一个问题，就是当数据量大的时候，生成的 SQL 将特别的长，MySQL 可能一次性处理不了这么大的 SQL，这个时候就需要修改 MySQL 的配置或者对待插入的数据进行分片处理了，这些操作又会导致插入时间更长。

   **2.3对比分析**

   很明显，方案一更具优势。当批量插入十万、二十万数据的时候，方案一的优势会更加明显（方案二则需要修改 MySQL 配置或者对待插入数据进行分片）。

3. mp是怎么做的

   我们知道，其实 MyBatis Plus 里边也有一个批量插入的方法 saveBatch，我们来看看它的实现源码

   ```java
   @Transactional(rollbackFor = Exception.class)
   @Override
   public boolean saveBatch(Collection<T> entityList, int batchSize) {
       String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
       return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
   }
   ```

   可以看到，这里拿到的 sqlStatement 就是一个 INSERT_ONE，即一条一条插入。

   再来看 executeBatch 方法，如下：

   ```java
   public static <E> boolean executeBatch(Class<?> entityClass, Log log, Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
       Assert.isFalse(batchSize < 1, "batchSize must not be less than one");
       return !CollectionUtils.isEmpty(list) && executeBatch(entityClass, log, sqlSession -> {
           int size = list.size();
           int i = 1;
           for (E element : list) {
               consumer.accept(sqlSession, element);
               if ((i % batchSize == 0) || i == size) {
                   sqlSession.flushStatements();
               }
               i++;
           }
       });
   }
   ```

   这里注意 return 中的第三个参数，是一个 lambda 表达式，这也是 MP 中批量插入的核心逻辑，可以看到，MP 先对数据进行分片（默认分片大小是 1000），分片完成之后，也是一条一条的插入。继续查看 executeBatch 方法，就会发现这里的 sqlSession 其实也是一个批处理的 sqlSession，并非普通的 sqlSession。

   综上，MP 中的批量插入方案跟我们 2.1 小节的批量插入思路其实是一样的。入股想要批量插入大数据量的效率最高，就采用2.1的方式

#### 9.mybatis-plus里使用QueryWrapper关于时间日期比较的问题

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



### 学习

# 数据库

## mysql

### 开发

#### 1.mysql常见的函数和问题的汇总

1. 注意mysql里面关于字符串的截取下标一般都是从1开始
2. Substring('str',a,b) 注意：a是起始位置，b是要截取得长度。且下标从1开始 ，如果a是0，那么无论b是多少都返回一个空串
3. Round(num,a) num 如果为字符串，那么返回的也是数字。如果num为'a'或者'b'这种非数字类型的字符串，那么会把这种字符串当成数字0 ，并且a是0，那么就是0 ，a是1，就是0.0 。

   注意：如果num是整数(round(2234,2))，那么无论a是多少，返回的都是整数(2234)，如果num是整数型的字符串(round('2234',3))，那么返回的就是带0的小数(2234.000)。

   注意：abs(25.0)=>25.0      abs('25.0') =>25
4. INSTR（str,substr） / instr(源字符串, 目标字符串) 获取子串第一次出现的索引，如果没有找到，则返回0（下标从1开始）
5. 1.使用union all链接两个查询结果的时候，如果链接查询结果要有各自的顺序并且总结果要保留这种顺序，那么每个链接的子查询都必须两边加上（）并且在最后加上limit a,b，为了保证都各个子查询查询出全部的结果，ab的取值可以为0,10000000000000

   ```sql
   -- 例子：上行升序，下行降序
   select t.*,t.lc_str as lcStr from (
   		(SELECT
   			id,
   			xb,
   			lc,
   			cxlx,
   			remove_end_zero(round(fz,2)) as fz,    
   			remark, 
   			formatMile(lc,'m',0) as lc_str
   		FROM
   			cxdata_table
   		WHERE
   			dcjh_id = #{dcjhId}
   		  	and xb = '上行'
   			AND lc + 0 >= #{startMileage} + 0
   			AND lc + 0 < #{endMileage} + 0
   			AND flag = '0'
   		order by lc + 0 asc
   		LIMIT 0,10000000000000)
   		union all
   		(SELECT
   			id,
   			xb,
   			lc,
   			cxlx,
   			remove_end_zero(round(fz,2)) as fz,
   			remark,
   			formatMile(lc,'m',0) as lc_str
   		FROM
   			cxdata_table
   		WHERE
   			dcjh_id = #{dcjhId}
   		  and xb = '下行'
   		  AND lc + 0 >= #{startMileage} + 0
   		  AND lc + 0 < #{endMileage} + 0
   		  AND flag = '0'
   		order by lc + 0 desc
   		LIMIT 0,10000000000000)
   ) t
   ```

#### 2.是否支持别名

在mysql中

1. group by ,having ,order by 支持别名。
2. where 后面不支持别名。
3. 如果关联查询的时候给表起了别名。那么where后面如果用'表名.字段名'的话这个表名要用表的别名

#### 3.union和union all 关键字

1. 区别1：取结果的交集

   - union: 对两个结果集进行并集操作, 不包括重复行,相当于distinct, 同时进行默认规则的排序;
   - union all: 对两个结果集进行并集操作, 包括重复行, 即所有的结果全部显示, 不管是不是重复;
2. 区别2：获取结果后的操作

   - union: 会对获取的结果进行排序操作
   - union all: 不会对获取的结果进行排序操作
3. 区别3：

   - union看到结果中ID=3的只有一条

     ```sql
     select * from student2 where id < 4
     union
     select * from student2 where id > 2 and id < 6
     ```
   - union all 结果中ID=3的结果有两个

     ```sql
     select * from student2 where id < 4
     union all
     select * from student2 where id > 2 and id < 6
     ```
4. 总结

   union all只是合并查询结果，并不会进行去重和排序操作，在没有去重的前提下，使用union all的执行效率要比union高

#### 4.mysql建表的几个必备字段

    id(主键)，码表（在基础字典表中该字段是必须的)，排序（在基础字典表中该字段是必须的)，创建人，创建时间，修改时间，逻辑删除字段。

#### 5.any 和 all 关键字

```sql
A = any('a','b') 等价于 A = 'a' or A = 'b'

A = all('a','b') 等价于 A = 'a' and A = 'b'
```

    总结 ：any 相当于用or链接后面括号里的子元素，all 相当于用and链接后面括号里面的子元素

#### 6.mysql开放远程连接(5.7版本生效，8以上的没试过)

先连接到本地数据库

切换到mysql数据库

```sql
use mysql
```

使用以下命令可以更改远程连接的设置

```sql
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;
```

刷新权限

```sql
flush privileges;
```

查询user表看看是否生效，如果 '%'  'root' 在第一行证明生效了

```sql
select host,user from user;
```

![确保能连接到github](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210720111410.png)

#### 7.mysql在导入.sql文件的时候报错  1067 - Invalid default value for ‘LOCK_TIME_‘

推荐使用以下的方式永久修改

编辑mysql的配配置文件 my.cnf

在[mysqld]下面添加如下列：

```properties
sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
```

然后重启mysql服务即可

#### 8.mysql的DATE_FORMAT()函数和STR_TO_DATE()函数的常用例子

1. 常用的DATE_FORMAA()格式

   ```sql
   DATE_FORMAT(updatetime,'%Y-%m-%d') -- 把mysql的datetime格式化成2021-09-23的字符串格式
   DATE_FORMAT(updatetime,'%Y-%m-%d %H:%i:%S') -- 把mysql的datetime格式化成2021-09-07 09:30:37的字符串格式
   ```
2. 常用的STR_TO_DATE()格式

   ```sql
   STR_TO_DATE('2015-09-01 00:00:00','%Y-%m-%d %H:%i:%s') -- 把字符串转为datetimeg
   ```

#### 9.mysql区间查询的方法

1. 取交集的区间查询:两个区间段只要有交集就查出来

   ```xml
   <if test='(startTime != "" and startTime != null) and (endTime == null or endTime == "")'>
       AND csrw.endtime >= #{startTime}
   </if>
   
   <if test='(startTime == "" or startTime == null) and (endTime != null and endTime != "")'>
       AND csrw.createtime <= #{endTime}
   </if>
   
   <if test='(startTime != "" and startTime != null) and (endTime != null and endTime != "")'>
       AND !(csrw.endtime < #{startTime} OR csrw.endtime > #{startTime})
   </if>
   ```

#### 10.mysql比较date或者datetime

1. 单个的比较可以直接使用> < 或者= 来比较，但是当两个值的组合与另两个值的组合进行比较的时候，可以使用**UNIX_TIMESTAMP()**函数

   ```sql
       	SELECT
   			*
           FROM
               dtjc_jh_jdjh
           WHERE
               xm_id = #{xmId}
           ORDER BY
               (UNIX_TIMESTAMP( start_time ) + UNIX_TIMESTAMP( end_time ))/ 2;
   ```

   如上就是根据**起始时间和终止时间的中间值**进行比较。其中start_time和end_time都是datetime类型

#### 11.mysql关于create_time和update_time的策略

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

### 学习

#### 1.mysql使用索引十诫

- 什么情况下要用索引
  - 主键自带主键索引
  - 唯一约束自带唯一索引
  - 外键自带外键索引
  - 查询条件用到的字段需要
  - 排序用的的字段
  - 分组用到的字段
- 什么情况下不能用索引
  - 数据量较少时不用建索引。
  - 频繁更新字段不能建索引
  - 索引的选择性（字段的值尽量复杂且尽量分布不平均)
  - where条件查询用不到的字段不用建索引

#### 2.mysql优化索引十诫（附口诀)

- 全值匹配我最爱
- 最佳左前缀法则(如果索引引了多列，要遵守最左前缀法则。指的是查询从索引的最左前列开始并且不跳过索引中的列)
- 不在索引列上做任何操作（计算，函数，（自动or手动)类型转换），会导致索引失效而转向全表扫描
- 存储引擎不能使用索引中范围条件右边的列
- 尽量使用覆盖索引（只访问索引的查询（索引列和查询列一致)),减少select *
- mysql在使用!=或者<>时候无法使用索引而转向全表扫描
- is null,is not null也无法使用索引
- like以通配符开头('%abc')mysql索引失效会变成全表扫描的操作。解决办法：使用覆盖索引
- 字符串不加单引号导致索引失效
- 少用or,用or连接时会索引失效

**口诀**

**全职匹配我最爱，最左前缀要遵守；
带头大哥不能死，中间兄弟不能断；
索引列上少计算，范围之后全失效；
LIKE 百分写最右，覆盖索引不写*；
不等空值还有OR，索引影响要注意；
VAR 引号不可丢，SQL 优化有诀窍。**

#### 3.mysql排序优化（为排序使用索引)

![请确认能链接到github](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210810170647.png)

#### 4.mysql show profile功能

1. 开启Show Profile功能，默认该功能是关闭的，使用前需开启。

   ```sql
   show variables like 'profiling';
   set profiling = on;
   ## 执行一部分sql后(默认保留15条)
   show profiles;
   ## duration是持续时间
   ## 针对特定的sql进行诊断
   show profile cpu,block io for query Query_ID;/*Query_ID为#3步骤中show profiles列表中的Query_ID*/
   ```
2. show profile的常用查询参数。

   ①ALL：显示所有的开销信息。

   ②BLOCK IO：显示块IO开销。

   ③CONTEXT SWITCHES：上下文切换开销。

   ④CPU：显示CPU开销信息。

   ⑤IPC：显示发送和接收开销信息。

   ⑥MEMORY：显示内存开销信息。

   ⑦PAGE FAULTS：显示页面错误开销信息。

   ⑧SOURCE：显示和Source_function，Source_file，Source_line相关的开销信息。

   ⑨SWAPS：显示交换次数开销信息。
3. 日常开发需注意的结论。（出现下述结论都需要优化)

   ①converting HEAP to MyISAM：查询结果太大，内存不够，数据往磁盘上搬了。

   ②Creating tmp table：创建临时表。先拷贝数据到临时表，用完后再删除临时表。

   ③Copying to tmp table on disk：把内存中临时表复制到磁盘上，危险！！！

   ④locked。
4. 总结

   1.show profile默认是关闭的，并且开启后只存活于当前会话，也就说每次使用前都需要开启。

   2.通过show profiles查看sql语句的耗时时间，然后通过show profile命令对耗时时间长的sql语句进行诊断。

   3.注意show profile诊断结果中出现相关字段的含义，判断是否需要优化sql语句。

   4.可更多的关注MySQL官方文档，获取更多的知识。

#### 5.mysql的myisam的读写锁(表锁)

```sql
lock table emp read;
lock table emp write;

```

myisam是写锁调度优于读锁调度,所以mysiam要偏读（因为写会阻塞其他线程对当前表的任何操作)

myisam执行select时会给所有涉及的表增加读锁。执行增删改时会给所有涉及到的表增加写锁

表读锁，当前session只能读当前表，对其他表任何操作都做不了，其他session能做任何操作，只是对有读锁的表的增删改会阻塞

表写锁，当前session只能对当前表做增删改查，对其他表任何操作都做不了，其他session对有写锁的表的任何操作都会堵塞，但是对其他的表可以做任何操作

```sql
show open tables; --查看哪些表被锁了
show status like 'table%'; --分析表的锁定状况
```

#### 6.mysql的innodb的读写锁(行锁)

session1更新某一行时,且未提交。session2读到的是旧数据。直到ession1提交。session2才能读到新数据

session1更新某一行时,且未提交。当session2同时也更新这一行时，阻塞。直到ession1提交。session2才能更新完成。注意：session2更新其他行的数据不受影响

---

**注意**

innode引擎默认是行锁。但是出现以下情况的时候，行锁还是会变成表锁

即：更新时where后面的条件没有使用上索引。包括字段上本身没有索引或者有索引但是sql写的不严谨导致索引失效，此时即使是innodb引擎它在更新的时候还是会锁住整张表

---

行锁的状态查看命令

```sql
show status like 'innodb_row_lock%'
--出现的参数依次往下分别是:
--当前正在等待的锁的数量
--从服务器启动到现在等待锁的总的时间长度
--每次等待所花的平均时间
--等待的最长的一次时间
--服务启动到现在总共等待锁的次数
```

#### 7.mysql8的安装

1. 查看是否有安装过mysql

   ```shell
   rpm -qa | grep -i mysql
   ```
2. 删除mysql

   ```shell
   yum -y remove MySQL-*
   yum -y remove MySQL
   ```

   一般用rpm -e 的命令删除mysql,这样表面上删除了mysql,可是mysql的一些残余程序仍然存在,并且通过第一步的方式也查找不到残余,而yum命令比较强大,可以完全删除mysql.(ps:用rpm删除后再次安装的时候会提示已经安装了,这就是rpm没删除干净的原因)
3. 把所有出现的目录统统删除

   ```shell
   find / -name mysql
   ```

   查找mysql的一些目录，把所有出现的目录删除，可以使用rm -rf 路径，删除时请注意，一旦删除无法恢复。
4. 删除配置文件

   ```shell
   rm -rf /etc/my.cnf
   ```
5. 删除mysql的默认密码

   ```shell
   rm -rf /root/.mysql_sercret
   ```

   删除mysql的默认密码,如果不删除,以后安装mysql这个sercret中的默认密码不会变,使用其中的默认密码就可能会报类似Access denied for user ‘root@localhost’ (using password:yes)的错误.

---

五步完成之后，这样mysql就全部删除干净了，若没安装过mysql可忽略以上步骤

1. 配置Mysql 8.0安装源

   ```shell
   sudo rpm -Uvh https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
   ```
2. 安装Mysql 8.0

   ```shell
   sudo yum --enablerepo=mysql80-community install mysql-community-server
   ```

   提示下载插件选择：y

   看到complet(完毕)就是安装完啦
3. 启动mysql服务

   ```shell
   sudo service mysqld start
   ```

   显示如下：

   启动完成
4. 查看mysql服务运行状态

   ```shell
   service mysqld status
   ```
5. 查看root临时密码

   安装完mysql之后，使用下列命令生成一个临时的密码让root用户登录

   ```shell
   grep "A temporary password" /var/log/mysqld.log
   ```
6. 更改临时密码

   输入：

   ```shell
   mysql -uroot -p
   ```

   在Enter password：后面输入临时密码
   登录成功
   输入：

   ```sql
   ALTER USER 'root'@'localhost' IDENTIFIED BY '123456Aa?';
   ```

   会提示：ERROR 1819 (HY000): Your password does not satisfy the current policy requirements(密码不符合当前策略)

   - 方案1: 设置符合策略的密码(大小写字母+数据+符号，8位)
   - 方案2:密码策略改简单一点

   方案2设置方式

   先看看当前的密码验证策略
   输入：

   ```sql
   SHOW VARIABLES LIKE 'validate_password.%';
   ```

   策略说明

   - validate_password.length 是密码的最小长度，默认是8，我们把它改成6
     输入：

     ```sql
     set global validate_password.length=6;
     ```
   - validate_password.policy 验证密码的复杂程度，我们把它改成0
     输入：

     ```sql
     set global validate_password.policy=0;
     ```
   - validate_password.check_user_name 用户名检查，用户名和密码不能相同，我们也把它关掉

     输入：

     ```sql
     set global validate_password.check_user_name=off;
     ```
   - 再执行修改密码的命令

     输入：

     ```sql
     ALTER USER ‘root’@‘localhost’ IDENTIFIED BY ‘12345’;
     ```

     密码设成功
     用mysql客户连接报不允许连接的错误，那是因为没开通远程访问的权限
7. 配置远程访问

   输入：

   ```sql
   GRANT ALL ON *.* TO 'root'@'%';
   flush privileges;
   ```

   报错：

   mysql> GRANT ALL ON . TO ‘root’@’%’;
   ERROR 1410 (42000): You are not allowed to create a user with GRANT

   看下默认MySQL用户：
   输入：

   ```sql
   use mysql;
   ```

   输入：

   ```sql
   select host, user, authentication_string, plugin from user;
   ```

   发现root的host是localhost，不是%，可以加个host是%的root账号：
   输入：

   ```sql
   CREATE USER ‘root’@’%’ IDENTIFIED BY ‘123456Aa?’;
   ```

   再查下用户

   输入：

   ```sql
   select host, user, authentication_string, plugin from user;
   ```

   可以看到已经新增了host为%的root用户

   输入：

   ```sql
   GRANT ALL ON *.* TO 'root'@'%';
   flush privileges;
   ```

   配置成功

---

**如果客户端连接mysql报错，并且其他配置都正常的情况下**

原因可能是mysql8的加密方式规则不一样，是caching_sha2_password

需要加密方式改成mysql_native_password就行了

语法:

ALTER USER ‘[用户名]’@’%’ IDENTIFIED WITH mysql_native_password BY ‘[密码]’;

输入：

```sql
ALTER USER ‘root’@’%’ IDENTIFIED WITH mysql_native_password BY ‘123456Aa?’;
```

加密方式以及改成了mysql_native_password

#### 8.mysql主从复制的搭建

0. 做主从的前提

   - 两台服务器的防火墙都开放了各自mysql的服务端口（下面以默认的3306为例子）
   - 从库无法同步主库之前的数据。如果主库之前有数据，那么先把主库的数据导入到从库中。保证两台服务器在做主从复制之前的数据一致性
   - 尽量保证两台服务器的my.cnf文件只有server-id不同。其他的配置都相同
1. 修改主服务器的配置

   ```shell
   vi /etc/my.cnf
   ```

   ```shell
   [mysqld]
   # 启用主从配置(主服务器)
   
   # 主服务器id
   server-id=1
   
   # 二进制日志
   log-bin=mysqlbin
   
   # 设置忽略复制的数据库
   # binlog-ignore-db=mysql
   
   # 设置需要复制的数据库
   # binlog-do-db=dtjc           
   ```
2. 重启mysql服务器

   ```shell
   service mysqld restart
   ```

   mysqld 无效的话把mysqld换成mysql
3. 运行

   ```sql
   mysql> show master status;
   +-----------------+----------+--------------+------------------+------------------------------------------+
   | File            | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set                        |
   +-----------------+----------+--------------+------------------+------------------------------------------+
   | mysqlbin.000003 |      883 |              |                  | e730104b-113f-11eb-9739-000c2972b171:1-7 |
   +-----------------+----------+--------------+------------------+------------------------------------------+
   1 row in set (0.01 sec)
   ```

   后续需要使用**file**和**position**这两个字段
4. 为从服务器生成专门的账号用来做主从复制,同时赋予做从服务器的权限

   ```sql
   create user 'repl'@'%' identified by '123456Aa?';
   grant replication slave,replication client on *.* to 'repl'@'%';
   flush privileges;
   ```
5. 修改从服务器的配置

   ```shell
   vi /etc/my.cnf
   ```

   ```shell
   [mysqld]
   # 启用主从配置(主服务器)
   
   # 从服务器id
   server-id=2
   
   # 二进制日志
   log-bin=mysqlbin
   
   # 设置忽略复制的数据库
   # binlog-ignore-db=mysql
   
   # 设置需要复制的数据库
   # binlog-do-db=dtjc  
   ```
6. 重启mysql服务

   ```sql
   service mysqld restart
   ```

   mysqld 无效的话把mysqld换成mysql
7. slave节点测试repl用户远程连接mater节点

   ```sql
   mysql -h192.168.220.10 -P3306 -urepl -p123456Aa?
   ```

   链接成功，即可进行下一步，否则要排错
8. 退出master节点的登陆，登陆本机的mysql,运行以下命令

   ```sql
    change master to master_host='192.168.220.10',master_port=3306,master_user='repl',master_password='12345Aa?',master_log_file='mysqlbin.000003',master_log_pos=883;
   ```

   **master_log_file就是主服务器的file字段，883就是主服务器的position字段**
9. 如果第8步的mysql没有报错的话，查看slave状态

   ```sql
   mysql> show slave status\G
   *************************** 1. row ***************************
                  Slave_IO_State: Waiting for source to send event
                     Master_Host: 192.168.220.10
                     Master_User: repl
                     Master_Port: 3306
                   Connect_Retry: 60
                 Master_Log_File: mysqlbin.000003
             Read_Master_Log_Pos: 196
                  Relay_Log_File: localhost-relay-bin.000002
                   Relay_Log_Pos: 323
           Relay_Master_Log_File: mysqlbin.000003
                Slave_IO_Running: Yes
               Slave_SQL_Running: Yes
                 Replicate_Do_DB:
             Replicate_Ignore_DB:
              Replicate_Do_Table:
          Replicate_Ignore_Table:
         Replicate_Wild_Do_Table:
     Replicate_Wild_Ignore_Table:
                      Last_Errno: 0
                      Last_Error:
                    Skip_Counter: 0
             Exec_Master_Log_Pos: 196
                 Relay_Log_Space: 536
                 Until_Condition: None
                  Until_Log_File:
                   Until_Log_Pos: 0
              Master_SSL_Allowed: No
              Master_SSL_CA_File:
              Master_SSL_CA_Path:
                 Master_SSL_Cert:
               Master_SSL_Cipher:
                  Master_SSL_Key:
           Seconds_Behind_Master: 0
   Master_SSL_Verify_Server_Cert: No
                   Last_IO_Errno: 0
                   Last_IO_Error:
                  Last_SQL_Errno: 0
                  Last_SQL_Error:
     Replicate_Ignore_Server_Ids:
                Master_Server_Id: 1
                     Master_UUID: e730104b-113f-11eb-9739-000c2972b171
                Master_Info_File: mysql.slave_master_info
                       SQL_Delay: 0
             SQL_Remaining_Delay: NULL
         Slave_SQL_Running_State: Replica has read all relay log; waiting for more updates
              Master_Retry_Count: 86400
                     Master_Bind:
         Last_IO_Error_Timestamp:
        Last_SQL_Error_Timestamp:
                  Master_SSL_Crl:
              Master_SSL_Crlpath:
              Retrieved_Gtid_Set:
               Executed_Gtid_Set: e730104b-113f-11eb-9739-000c2972b171:1-3
                   Auto_Position: 0
            Replicate_Rewrite_DB:
                    Channel_Name:
              Master_TLS_Version:
          Master_public_key_path:
           Get_master_public_key: 0
               Network_Namespace:
   1 row in set, 1 warning (0.00 sec)
   ```

   如果

   ```sql
                Slave_IO_Running: Yes
               Slave_SQL_Running: Yes
   ```

   那么主从配置就搭建好了

   如果Slave_IO_Running或者Slave_SQL_Running有任意一个不是Yes的话，搭建失败

   运行以下命令,停止主从。然后从最开始一步步排错

   ```sql
   stop slave;
   reset slave all;
   ```

## redis

### 开发

#### 1.redisTemplate存储zset的写法

```java
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Map<String, Double>>> tuples = new HashSet<>();

        int cycleNum = dataShowMapper.getTrackDynamicGeometryDataNum(csrwId, xb) / 10000 + 1;// 循环次数

        CountDownLatch countDownLanch = new CountDownLatch(cycleNum);

        ExecutorService executor = Executors.newFixedThreadPool(cycleNum > 4 ? 4 : cycleNum);

        for (int i = 0; i < cycleNum; i++) {
            int start = i * 10000;
            int num = 10000;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Map<String, Double>> dataListTemp = dataShowMapper.getWaveEchartsCorrectDataNoSparse(csrwId, xb, "0", "0", start, num);
                        for (Map<String, Double> dataMap : dataListTemp) {
                            ZSetOperations.TypedTuple<Map<String, Double>> typedTuple = new DefaultTypedTuple<>(dataMap, dataMap.get("kms"));
                            tuples.add(typedTuple);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLanch.countDown();
                    }

                }
            });
        }

        try {
            countDownLanch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        if (tuples.size() > 0) {
            zSetOperations.add(key, tuples);
            redisTemplate.expire(key, 3, TimeUnit.HOURS);
        }

```

### 学习

# 服务器

## linux

### 通用

#### 1.根据端口号查询占用pid

```shell
netstat -nap|grep 8080
tcp6       0      0 :::8081                 :::*                    LISTEN      16996/java
kill -9 16996
```

#### 2.创建shell脚本并且运行

* 创建文件,编辑内容并且保存

  ```shell
  vim hello.sh
  ```
* 提权

  ```shell
  chmod u+x hello.sh
  ```
* 执行

  ```shell
  bash hello.sh
  ```

#### 3.后台运行命令

    如果使用nohup命令提交作业，那么在缺省情况下该作业的所有输出都被重定向到一个名为nohup.out的文件中，除非另外指定	了输出文件：

```shell
  nohup command > myout.file 2>&1 &
```

  在上面的例子中，输出被重定向到myout.file文件中。

```shell
  nohup command >/dev/null 2>&1 &
```

  上面的例子是不输出日志文件。

#### 4.开放端口，查看端口占用及根据端口占用杀掉进程

1. 开放端口

   ```shell
   firewall-cmd --zone=public --add-port=5672/tcp --permanent   # 开放5672端口
   firewall-cmd --zone=public --remove-port=5672/tcp --permanent  #关闭5672端口
   firewall-cmd --reload   # 配置立即生效
   ```
2. 查看防火墙所有开放的端口

   ```shell
   firewall-cmd --zone=public --list-ports
   ```
3. 关闭防火墙

   如果要开放的端口太多，嫌麻烦，可以关闭防火墙，安全性自行评估

   ```shell
   systemctl stop firewalld.service
   ```
4. 查看防火墙状态

   ```shell
    firewall-cmd --state
   ```
5. 查看和监听端口

   ```shell
   netstat -lnpt
   ```

   ![图片示意图](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210708231956.png)
6. 检查端口被哪个进程占用

   ```shell
   netstat -lnpt |grep 5672
   ```

   ![图片示意](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210708232118.png)
7. 查看进程的详细信息

   ```shell
   ps 6832
   ```

   ![图片示意](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210708232827.png)
8. 中止进程

   ```shell
   kill -9 6832
   ```

[原文链](https://www.cnblogs.com/heqiuyong/p/10460150.htm)

#### 5.查看命令所在的源文件位置

直接使用下面这条命令跟随符号链接来以获取实际的可执行文件：

```javascript
readlink -f `which java`
```

`readlink` 命令会跟随一个符号链接。我在 `which java` 的外侧使用 `readlink` 将会使用 `which java` 的输出来替换要检查的符号链接，这被称之为命令替换。因此，在这个实例中，上面的命令大体上相当于 `readlink -f /usr/bin/java` 。

在我的示例中，可执行文件的位置是 `/usr/lib/jvm/java-11-openjdk-amd64/bin/java` 。

### centos

#### 1.Linux下文档类型转PDF乱码解决方式

在Linux系统下进行文本类型转PDF时出现乱码。

![问题示意图](https://raw.githubusercontent.com/Takatsukun/study/main/img/20210708223956.png)

解决方案：

1. 安装fontconfig

   安装命令：

   yum –y install fontconfig
2. 在/usr/share/fonts目录下新建一个目录chinese

   操作命令：

   cd /usr/share/fonts

   mkdir chinese

   cd chinese
3. 修改文件夹权限

   操作命令：

   chmod -R 755 /usr/share/fonts/chinese/
4. 将本地字体上传至服务器

   操作步骤：

   1. 将C:\Windows\Fonts目录下的字体拷贝到一个新建文件夹（因为文件夹权限无法直接上传，所以需要创建一个新建文件夹）
   2. 将需要的字体上传到服务器的/usr/share/fonts/chinese目录下
5. 安装ttmkfdir

   安装命令：

   yum -y install ttmkfdir

   ttmkfdir -e /usr/share/X11/fonts/encodings/encodings.dir
6. 修改fonts.conf配置文件

   操作命令：

   vi /etc/fonts/fonts.conf

   ```xml
    <!-- Font directory list -->
   <dir>/usr/share/fonts</dir>
   <dir>/usr/share/X11/fonts/Type1</dir> <dir>/usr/share/X11/fonts/TTF</dir> <dir>/usr/local/share/fonts</dir>
   <dir prefix="xdg">fonts</dir>
   <dir>/usr/share/fonts/chinese</dir> #这里是你要添加的路径
   <!-- the following element will be removed in the future -->
   <dir>~/.fonts</dir>
   ```
7. 刷新Liunx字体缓存

   操作命令：

   mkfontdir

   mkfontscale

   fc-cache –fv

   fc-list :lang=ZH
8. 重启服务器(完成配置)

[原文链接](https://blog.csdn.net/weixin_45606229/article/details/111060060)

---

#### 2.Centos7下配置openOffice

1. 下载tar.gz包。下载地址：http://www.openoffice.org/zh-cn/ (需要下载rpm格式的)
2. 通过xftp上传到linux中。我的目录在/opt/openoffice中
3. 解压文件：tar -zxvf Apache_OpenOffice_4.1.6_Linux_x86-64_install-rpm_zh-CN.tar.gz，解压后进入zh-CN目录中。
4. cd RPMS/ 里面都是rpm文件，我们需要安装这些文件
5. 安装rpm文件： rpm -ivh *.rpm
6. 进入desktop-integration/目录：cd desktop-integration/
7. 安装openoffice:rpm -ivh openoffice4.1.6-redhat-menus-4.1.6-9790.noarch.rpm
8. 安装成功后会在/opt下出现一个openoffice4文件。
9. 启动服务

   ```she
   /opt/openoffice4/program/soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
   ```
10. 查看启动状态

    ```shell
    ps -ef|grep openofficenetstat -lnp |grep 8100
    ```

#### 3.查看后台运行的java -jar项目的端口号，并杀死该进程

```shell
lsof -i:8088Kill -9 pid
```

#### 4.用windowd的cmd向linux服务器上传文件

示例代码

```shel
scp -P 221 D:\hussar-web-2.2.0.war root@123.123.122.138:/opt/tomcat8/webapps/
```

城轨项目的使用的上传war包用：

```ssh
scp -P 221 D:\Work\workProject\dtjc\target\hussar-web-2.2.0.war root@123.123.122.138:/opt/tomcat8/webapps/
```

说明：

其中 221是端口，123.123.122.138是ip，root是登陆用户，D:\hussar-web-2.2.0.war是文件路径,/opt/tomcat8/webapps/是服务器存储上传文件的路径

***网上拷贝***

---

1、上传本地文件到服务器

```
scp -P 221 /path/filename username@servername:/path/
```

例如scp /var/www/test.php root@192.168.0.101:/var/www/ 把本机/var/www/目录下的test.php文件上传到192.168.0.101这台服务器上的/var/www/目录中

2、从服务器下载文件

下载文件我们经常使用wget，但是如果没有http服务，如何从服务器上下载文件呢？

```
scp -P 221 username@servername:/path/filename /var/www/local_dir（本地目录）
```

例如scp root@192.168.0.101:/var/www/test.txt 把192.168.0.101上的/var/www/test.txt 的文件下载到/var/www/local_dir（本地目录）

3、从服务器下载整个目录

```
scp -P 221 -r username@servername:/var/www/remote_dir/（远程目录） /var/www/local_dir（本地目录）
```

例如:scp -r root@192.168.0.101:/var/www/test /var/www/

4、上传目录到服务器

```
scp -P 221 -r local_dir username@servername:remote_dir
```

例如：scp -r test root@192.168.0.101:/var/www/ 把当前目录下的test目录上传到服务器的/var/www/ 目录

---

#### 5.linux查看日志最后几行

```shell
tail -n 50 wx.log
```

示例：查看/var/log/boot.log，只显示最后一行。则执行

```shell
tail -n 1  /var/log/boot.log
```

```she
tail -n 1000：显示最后1000行tail -n +1000：从1000行开始显示，显示1000行以后的head -n 1000：显示前面1000行
```

 [原文链接](https://www.cnblogs.com/keta/p/9627227.html)

#### 7.查看cpu核心数

查看物理cpu数目

```shell
cat /proc/cpuinfo | grep "physical id" | sort | uniq | wc -l
```

查看每个物理cpu里的核数

```shell
cat /proc/cpuinfo| grep "cpu cores"| uniq
```

查看cpu逻辑核心数（cpu数量*每个cpu的核数）

```shell
cat /proc/cpuinfo| grep "processor"| wc -l
```

[原文链接](https://blog.csdn.net/qq_38880380/article/details/79638252)

#### 8.查看网址

示例:查看本地的nginx是否启动

```she
 curl 127.0.0.1:80
```

#### 9.解决centos8下python命令失效的问题

执行

```shell
ln -s /usr/bin/python3.6 /usr/bin/python
```

[参考地址](https://blog.csdn.net/have_a_cat/article/details/118191281)

### ubuntu

#### 1.ubuntu安装nginx（nginx基础操作命令）

```bash
# 切换至root用户
sudo su root
apt-get install nginx
```

查看nginx是否安装成功

```shell
nginx -v
```

启动nginx

```shell
service nginx start #方式1
/usr/sbin/nginx #方式2
```

结束nginx

```shell
service nginx stop #方式1
/usr/sbin/nginx -s stop #方式2
```

重启nginx

```shell
service nginx reload #方式1
/usr/sbin/nginx -s reload #方式2
```

**注意：nginx方式1和方式2不能互相调用**

nginx文件安装完成之后的文件位置：

- /usr/sbin/nginx：主程序
- /etc/nginx：存放配置文件
- /usr/share/nginx：存放静态文件
- /var/log/nginx：存放日志

#### 2.ubuntu切换为root账号

- Ubuntu的默认root密码是随机的，即每次开机都有一个新的root密码。可以在终端输入命令 sudo passwd，然后输入当前用户的密码，回车.
- 终端会提示输入新的密码并确认，此时的密码就是root新密码。修改成功后，输入命令 su root，再输入新的密码就成功切换到root帐号了

#### 3.向ubuntu传输文件显示permission denied

scp 默认不允许使用root账号传输文件.如果想用root账号传输文件，在两端服务器

0、使用第二步切换为root账号

1、修改sshd配置文件

```bash
vi /etc/ssh/sshd_config
```

2、找到PermitRootLogin，把前面的#去掉，并且改为yes

```bash
PermitRootLogin yes
```

3、重启sshd服务

```shell
service ssh start
```

4、使用root账户远程登陆服务器并传输文件即可。注意root密码是在第二步自己设置的

#### 4.ubuntu中国大陆镜像源

```shell
http://mirrors.aliyun.com/ubuntu
```

#### 5.设置静态ip

- ubuntu_server18

  ```shell
  vim /etc/netplan/50-cloud-init.yaml
  ```

  配置如下,注意格式 冒号后边有个空格

  ```yaml
  network:
      ethernets:
          ens33:
              dhcp4: false
              addresses: [192.168.37.188/24]
              gateway4: 192.168.37.2
              nameservers:
                      addresses: [114.114.114.114,8.8.8.8]
      version: 2
  ```

  编辑好 最后保存配置文件，执行命令重启网络服务生效

  ```shell
  sudo netplan apply
  ```

#### 6.显示没有firewall-cmd命令

```shell
apt-get update
apt-get install firewalld
```

#### 7.rabbitmq-server基础操作命令

前提：通信端口默认是5672端口，如果远程连接，别忘记先在防火墙上开启该端口

查看运行状态

\# service rabbitmq-server status

启动

\#service rabbitmq-server stop

停止

#service rabbitmq-server start

重启rabbitmq服务

\#service rabbitmq-server restart

查看log文件

\#cd /var/log/rabbitmq/

#vim ***.log

查看已有插件列表

\#rabbitmq-plugins list

安装插件

\#rabbitmq-plugins enable rabbitmq_management(这里以安装web管理客户端为例子，该客户端的端口是15672.别忘记在防火墙开启端口)

编写配置文件（这里以开放外部访问为例子）

#cd /etc/rabbitmq/
#vim rabbitmq.config

向rabbitmq.config文件中写入如下内容：

[{rabbit, [{loopback_users, []}]}].

## windows

### 开发

#### 1.查看端口，查看进程，关闭进程

以8088端口为例：

```shell
netstat  -aon|findstr "8088"
```

最后一列是pid。假设为5544

然后根据pid查询相应进程

```shell
tasklist|findstr "5544"
```

第一列是程序名称，假设为java.exe

最后关闭进程

```shell
taskkill /f /t /im java.exe
```

直接使用命令关闭进程可能关不了，那么可以在任务管理器中直接找到该进程关闭。

#### 2.根据端口直接关闭进程

以8088端口为例：

```shell
netstat  -aon|findstr "8088"
```

最后一列是pid。假设为5544

```shell
taskkill /pid 5544 -t -f
```

## nginx

### 开发

#### 1.解决单页面应用，刷新后出现404或者404的错误

在location里加上  try_files  $uri $uri/ /index.html;

具体配置如下：

```shell
server {
    listen       81;
    server_name  project_mall;

    #charset koi8-r;

    #access_log  logs/host.access.log  main;

    location / {
    root   study/project_mall;
    index  index.html index.htm;
    # 解决单页面应用刷新出现403或者404错误
    try_files  $uri $uri/ /index.html;
    }
}
```

#### 2.nginx配置文件下载（城轨的应用是配置apk下载）

1. 修改配置文件
   ```ng
   server {
           listen       80;
           server_name  app.com.cn;
    
           location /app {
               alias /var/www/app;
               autoindex on;
               default_type application/octet-stream;
           }
       }
   ```

   **alias指定虚拟目录**

   **autoindex on;打开目录浏览功能** 

   **default_type application/octet-stream;设置相应的MIME类型**

   **当Url路径中包含`/app/`时，MIME类型会被重置为`application/octet-stream`**

2. 重新加载生效 

   ```shell
   nginx -t
   nginx -s reload
   ```

   

# 项目

## 项目构建的一些总结

1. 规范父pom的版本

2. 规范子项目的结构，目录，名命
3. 构建完整的代码生成器
4. 统一返回结果
5. 统一异常处理
6. 整合统一的日志
7. 多环境配置(dev,test.prod)

## git

### 开发

#### 1.在.gitignore中添加新的规则对已经被git管理了的文件不生效的解决办法

**解决办法：清理下git缓存即可**

执行以下命令

```shell
#删除git缓存
git rm -r --cached . 
git add .
git commit -m 'update .gitignore'
```

### 学习

## github

### 开发

#### 1.github搜索自己想要的项目

github上搜索的例子

in:name springboot forks:>4000 language:java stars:>4000 pushed:>2020-01-01

in:readme springboot mybatis forks:>4000 language:java stars:>4000 pushed:>2020-10-01

关键词

in:name xxx

in:description xxx

in:readme xxx

starts:>2000

fork:>3000

size:>=5000 注意：单位是k

pushed:>2020-01-01

language:xxx

user:xxx

搜索的方式可以组合,叠加，用空格分开条件

[更多高级搜索](https://github.com/search/advanced)

### 学习

# 工具

## 1.前后端分别判断登陆设备的方法

后端

```java
// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
// 字符串在编译时会被转码一次,所以是 "\\b"
// \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
private static final String PHONE_REG = "\\b(ip(hone|od)|android|opera m(ob|in)i"
    + "|windows (phone|ce)|blackberry"
    + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
    + "|laystation portable)|nokia|fennec|htc[-_]"
    + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";// 手记的正则表达式
private static final String TABLE_REG = "\\b(ipad|tablet|(Nexus 7)|up.browser"
    + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";// 拼版的正则表达式

// 移动设备正则匹配：手机端、平板
private static final Pattern PHONE_PAT = Pattern.compile(PHONE_REG, Pattern.CASE_INSENSITIVE);
private static final Pattern TABLE_PAT = Pattern.compile(TABLE_REG, Pattern.CASE_INSENSITIVE);

/**
     * 登陆设备认证。判断前台请求的设备是pc，pad还是phone
     *
     * @param userAgent 用户代理信息
     * @return
     */
public static String checkLoginDeviceType(String userAgent) {
    if (null == userAgent) {
        userAgent = "";
    }
    // 匹配
    Matcher matcherPhone = PHONE_PAT.matcher(userAgent);
    Matcher matcherTable = TABLE_PAT.matcher(userAgent);
    if (matcherPhone.find()) {
        return PHONE_TYPE;
    } else if (matcherTable.find()) {
        return PAD_TYPE;
    } else {
        return PC_TYPE;
    }
}
```

前端（没经过项目测试，只是记录别人写的代码,而且好像只能区分移动端和桌面，不能区分移动端是pad还是phone)

```js
//智能检测登陆设备类型
var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {//移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/) || u.indexOf('iPad') > -1, //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}

if(browser.versions.mobile){
    //移动端
}else {
    //pc端
}
```

## 2.一个常用的javaweb返回体工具类

```java
/**
 * 返回体工具
 */
public class DtoResult<T> implements Serializable {

    public static final int STATUS_CODE_SUCCESS = 0;
    public static final int STATUS_CODE_ERROR = 500;

    private int code;
    private String message;
    private T data;

    public DtoResult() {
        this.setCode(0);
    }

    public DtoResult(T data) {
        this();
        this.data = data;
    }

    public DtoResult(Integer code, String message, T data) {
        this.setCode(code);
        this.message = message;
        this.data = data;
    }

    public DtoResult(Integer code, String message) {
        this.setCode(code);
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static <T> DtoResult<T> ok() {
        return new DtoResult<T>(0, "操作成功", null);
    }

    public static <T> DtoResult<T> ok(T data) {
        return new DtoResult<T>(0, "操作成功", data);
    }

    public static <T> DtoResult<T> ok(String message, T data) {
        return new DtoResult<T>(0, message, data);
    }

    public static <T> DtoResult<T> error() {
        return new DtoResult<T>(500, "数据异常");
    }

    public static <T> DtoResult<T> error(String message) {
        return new DtoResult<T>(500, message);
    }

    public static <T> DtoResult<T> error(Integer code, String message) {
        return new DtoResult<T>(code, message);
    }

    public T getData() {
        return this.data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
```
