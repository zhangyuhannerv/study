配置文件
```shell
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

修改完配置后，重新加载生效
```shell
nginx -t
nginx -s reload
```