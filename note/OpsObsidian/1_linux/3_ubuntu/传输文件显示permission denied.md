scp 默认不允许使用root账号传输文件.如果想用root账号传输文件，在两端服务器

0、使用第二步切换为root账号

1、修改sshd配置文件

```shell
vi /etc/ssh/sshd_config
```

2、找到PermitRootLogin，把前面的#去掉，并且改为yes

```bash
PermitRootLogin yes
```

3、重启sshd服务

```bash
service ssh start
```

4、使用root账户远程登陆服务器并传输文件即可。注意root密码是在第二步自己设置的