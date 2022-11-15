进入到放这个jar包的文件夹，运行cmd

然后根据下面的例子手动自己可以尝试着添加
```bash
mvn install:install-file -Dfile=aspose-words-16.4.0-jdk16.jar -DgroupId=com.aspose.word  -DartifactId=aspose.words -Dversion=16.4.0-jdk16 -Dpackaging=jar -DgeneratePom=true
```

```xml
<dependency>
			<groupId>com.aspose.word</groupId>
			<artifactId>aspose.words</artifactId>
			<version>16.4.0-jdk16</version>
</dependency>
```