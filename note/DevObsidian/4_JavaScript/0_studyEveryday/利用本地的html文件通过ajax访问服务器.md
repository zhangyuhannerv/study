-   在chrome的快捷方式上右键属性，选中快捷方式tab，在目标栏的最后添加以下参数，然后重启chrome，用来测试的文件就放在下面配置的data-dir里
    
	注意：每个--前面都有一个空格
    
    注意：服务器必须开启跨域访问
	```xml
	   --user-data-dir="C:\Users\13551\Desktop" --test-type --disable-web-security
	```
	