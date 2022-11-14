1.    
    下载tar.gz包。下载地址：[http://www.openoffice.org/zh-cn/](http://www.openoffice.org/zh-cn/) (需要下载rpm格式的)
    
2.  通过xftp上传到linux中。我的目录在/opt/openoffice中
    
3.  解压文件：tar -zxvf Apache_OpenOffice_4.1.6_Linux_x86-64_install-rpm_zh-CN.tar.gz，解压后进入zh-CN目录中。
    
4.  cd RPMS/ 里面都是rpm文件，我们需要安装这些文件
    
5.  安装rpm文件： rpm -ivh *.rpm
    
6.  进入desktop-integration/目录：cd desktop-integration/
    
7.  安装openoffice:rpm -ivh openoffice4.1.6-redhat-menus-4.1.6-9790.noarch.rpm
    
8.  安装成功后会在/opt下出现一个openoffice4文件。
    
9.  启动服务
    ```shell
     /opt/openoffice4/program/soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
	```

10. 查看启动状态
	```shell
	ps -ef|grep openofficenetstat -lnp |grep 8100
	```
