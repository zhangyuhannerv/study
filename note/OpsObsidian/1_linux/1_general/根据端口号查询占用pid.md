```shell
netstat -nap|grep 8080
tcp6       0      0 :::8081                 :::*                    LISTEN      16996/java
kill -9 16996
```