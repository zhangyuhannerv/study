```shell
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

-   /usr/sbin/nginx：主程序
    
-   /etc/nginx：存放配置文件
    
-   /usr/share/nginx：存放静态文件
    
-   /var/log/nginx：存放日志