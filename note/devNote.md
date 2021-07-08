**张雨晗の研究ノート**

**邮箱：1355166049@qq.com或zhangyuhannerv@gmail.com**

---

[toc]
# 前端
## layui

# 后端
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