**张雨晗の研究ノート**

**邮箱：1355166049@qq.com或zhangyuhannerv@gmail.com**

---

[toc]
# 前端
## layui

### 开发

#### 1.删除动态表格鼠标悬浮背景颜色变色的效果

```css
/*删除鼠标悬浮背景变色效果*/
/*其中#cxDataQdTjSxDiv是数据表格table容器的id,格式如下*/
#CxDataQdTjSxDiv .layui-table tbody tr:hover,
#CxDataQdTjSxDiv .layui-table thead tr,
#CxDataQdTjSxDiv .layui-table[lay-even] tr:nth-child(even) {
    background-color: transparent !important;
}
```

``` html
<div id="CxDataQdTjSxDiv" class="tableContainer">
    <label class="tableTitle">区段超限情况统计（上行）</label>
    <table id="CxDataQdTjSxTab" class="layui-table"></table>
</div>
```

### 学习

# 后端

## java

### 开发

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

mysql的DateTime对应着java中的timeStamp类型。存储的时候如何将java中的timeStamp转换为mysql中的DateTime

```java
 java.util.Date date = new java.util.Date();   // 获取一个Date对象
 Timestamp timeStamp = new Timestamp(date.getTime());  // 给对象赋值该值插入就行了
```

[**Java：String和Date、Timestamp之间的转换**](https://www.cnblogs.com/mybloging/p/8067698.html)

1. String与Date（java.util.Date）互转

   1. String -> Date

      ``` java
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

   2.  Timestamp -> String

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

      

### 学习

## maven

### 开发

#### 关于项目得jar包都正常引用了，但是build时就是提示jar包不存在的解决办法

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

### 学习

### mybatis

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
   			AND lc + 0 &gt;= #{startMileage} + 0
   			AND lc + 0 &lt; #{endMileage} + 0
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
   		  AND lc + 0 &gt;= #{startMileage} + 0
   		  AND lc + 0 &lt; #{endMileage} + 0
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

​	id(主键)，码表（在基础字典表中该字段是必须的)，排序（在基础字典表中该字段是必须的)，创建人，创建时间，修改时间，逻辑删除字段。

#### 5.any 和 all 关键字

```sql
A = any('a','b') 等价于 A = 'a' or A = 'b'

A = all('a','b') 等价于 A = 'a' and A = 'b'
```

​	总结 ：any 相当于用or链接后面括号里的子元素，all 相当于用and链接后面括号里面的子元素

### 学习

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
### centos
#### 开发
##### 1.Linux下文档类型转PDF乱码解决方式

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

6. 刷新Liunx字体缓存

   操作命令：

   mkfontdir

   mkfontscale

   fc-cache –fv

   fc-list :lang=ZH

7. 重启文件服务器(完成配置)

[原文链接](https://blog.csdn.net/weixin_45606229/article/details/111060060  )

---

##### 2.Centos7下配置openOffice

1. 下载tar.gz包。下载地址：http://www.openoffice.org/zh-cn/ (需要下载rpm格式的)

2. 通过xftp上传到linux中。我的目录在/opt/openoffice中

3. 解压文件：tar -zxvf Apache_OpenOffice_4.1.6_Linux_x86-64_install-rpm_zh-CN.tar.gz，解压后进入zh-CN目录中。

4.  cd RPMS/ 里面都是rpm文件，我们需要安装这些文件 

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
    ps -ef|grep openoffice
    netstat -lnp |grep 8100
    ```

##### 3.查看后台运行的java -jar项目的端口号，并杀死该进程

```shell
lsof -i:8088
Kill -9 pid
```

##### 4.用windowd的cmd向linux服务器上传文件

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

---

##### 5.linux查看日志最后几行

```shell
tail -n 50 wx.log
```

示例：查看/var/log/boot.log，只显示最后一行。则执行

```shell
tail -n 1  /var/log/boot.log
```

```she
tail -n 1000：显示最后1000行
tail -n +1000：从1000行开始显示，显示1000行以后的
head -n 1000：显示前面1000行
```

 [原文链接](https://www.cnblogs.com/keta/p/9627227.html)

##### 6.Centos 7 开发端口

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

[原文链接](https://www.cnblogs.com/heqiuyong/p/10460150.htm)




#### 学习
# 项目