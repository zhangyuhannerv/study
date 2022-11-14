查看物理cpu数目

```shell
cat /proc/cpuinfo | grep "physical id" | sort | uniq | wc -l
```

查看每个物理cpu里的核数

```shell
cat /proc/cpuinfo| grep "cpu cores"| uniq
```

查看cpu逻辑核心数（cpu数量*每个cpu的核数）

```shell
cat /proc/cpuinfo| grep "processor"| wc -l
```

[原文链接](https://blog.csdn.net/qq_38880380/article/details/79638252)