**张雨晗の研究ノート**

**邮箱：1355166049@qq.com或zhangyuhannerv@gmail.com**

---

[toc]

# 后端

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

### 
