# 快速搭建 v2ray

## v2ray 一键脚本安装

```
bash <(curl -sL https://raw.githubusercontent.com/hijkpw/scripts/master/goV2.sh)
```

执行之后出现以下内容 ，说明安装成功：

![img](https://ubuntu520.com/images/install_v2ray.png)

记住端口号和 UUID ，客户端配置的时候需要用到，当然，你也可以使用如下命令查看配置：

> cat /etc/v2ray/config.json

## 使用 v2ray

### 同步服务器的时间

```
yum -y install ntpdate
ntpdate ntp1.aliyun.com
```

### 开启 v2ray

输入以下命令开启：

```
systemctl enable v2ray
systemctl start v2ray
```

复制好你定义的 port 和 id，等会在客户端中使用。

## 解决 v2ray invalid user: VMessAEAD可

修改文件：

```
vi /etc/systemd/system/v2ray.service
```

将 Execstart 这一行改为：

```
ExecStart=/usr/bin/env v2ray.vmess.aead.forced=false /usr/bin/v2ray/v2ray -config /etc/v2ray/config.json
```

按下 `Esc` 然后输入 `:wq` 保存退出

## 重启v2ray：

```
systemctl daemon-reload
systemctl restart v2ray
```

# 客户端使用 v2ray

## Windows 使用 v2ray

搭建好了之后可以在你的系统使用了，到 [v2ray 客户端下载](https://github.com/v2ray/v2ray-core/releases) ，安装后填入你刚刚在服务器中得到的ip、uuid、port后开启运行即可。

## macos 使用 v2ray

下载[v2rayx](https://github.com/Cenmrev/V2RayX/releases), 接着打开菜单下的 Config， 将填入你刚刚在服务器中得到的ip、uuid、port填入后开启运行即可。

## Linux 用户

在客户端按照上述方法安装 v2ray ，安装完之后将 config.json 文件更改为上述的内容，将服务器的配置信息更改后运行即可。

配置如下：

```
{
  "log": {
    "loglevel": "info"
  },
  "inbounds": [
    {
      "port": 1080,
      "protocol": "socks",
      "sniffing": {
        "enabled": true,
        "destOverride": [
          "http",
          "tls"
        ]
      },
      "settings": {
        "udp": true
      }
    },
    {
      "port": 8080,
      "protocol": "http",
      "sniffing": {
        "enabled": true,
        "destOverride": [
          "http",
          "tls"
        ]
      }
    }
  ],
  "outbounds": [
    {
      "tag": "proxy-vmess",
      "protocol": "vmess",
      "settings": {
        "vnext": [
          {
            "address": "你的服务器ip地址",
            "port": 你的v2ray端口,
            "users": [
              {
                "id": "你的uuid",
                "alterId": 4
              }
            ]
          }
        ]
      }
    },
    {
      "tag": "direct",
      "settings": {},
      "protocol": "freedom"
    }
  ],
  "dns": {
    "server": [
      "8.8.8.8",
      "1.1.1.1"
    ]
  },

  "routing": {
    "domainStrategy": "IPOnDemand",
    "rules": [
      {
        "type": "field",
        "outboundTag": "proxy-vmess"
      },
      {
        "type": "field",
        "domain": [
          "geosite:cn"
        ],
        "outboundTag": "direct"
      },
      {
        "type": "field",
        "outboundTag": "direct",
        "ip": [
          "geoip:cn",
          "geoip:private"
        ]
      }
    ]
  }
}
```

# 运行效果

访问一下 Youtube，1080p 超高清，很顺畅不卡顿：

![youtube](https://ubuntu520.com/images/ss7.png)

youtube

## v2ray 使用相关问题

- 客户端突然无法访问了
  解决：https://github.com/v2ray/v2ray-core/issues/1871
  （更换端口）

# V2ray 相关

## v2ray 官网

- [V2Ray 的使用手册](https://www.v2ray.com/)

## v2ray GitHub

- [v2ray 源码](https://github.com/v2ray/v2ray-core)