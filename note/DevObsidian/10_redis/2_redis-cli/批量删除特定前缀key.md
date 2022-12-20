```shell
redis-cli --scan --pattern "ops-coffee-*" | xargs -L 2000 redis-cli del
```
其中xargs -L指令表示xargs一次读取的行数，也就是每次删除的key数量，一次读取太多xargs会报错