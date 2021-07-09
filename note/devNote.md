**张雨晗の研究ノート**

**邮箱：1355166049@qq.com或zhangyuhannerv@gmail.com**

---

[toc]
# 前端
## layui

# 后端

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