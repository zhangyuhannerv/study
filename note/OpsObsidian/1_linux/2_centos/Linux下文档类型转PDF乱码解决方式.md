在Linux系统下进行文本类型转PDF时出现乱码。
![问题示意图](https://cdn.jsdelivr.net/gh/Zhangyuhannerv/picture-host-1@main/20210708223956.png)

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

8. 重启服务器(完成配置)（注意：第7步完成之后如果生效了。那么就不用之行第8步了）

[原文链接](https://blog.csdn.net/weixin_45606229/article/details/111060060)
