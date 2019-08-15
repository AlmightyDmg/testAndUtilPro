
# testAndUtilPro
Java开发中常用的工具类，包括但不限于对word、excel、html、pdf、网络文件等的操作

# 功能介绍
## 1.操作Excel
### （1）Excel转PDF
### （2）替换Excel中的值
## 2.操作图片
### （1）对图片进行高保真压缩
### （2）生成二维码
### （3）获得网络图片
### （4）图片转PDF
## 3.操作网络文件
## 4.操作pdf
## 5.操作Word文件 
### （1）Word转HTML
### （2）Word转PDF



其中一些jar包可能在maven仓库中引用不到，可以将本地的jar导入到本地maven仓库中，jar包在somejars文件夹当中，导入方法如下：
## 1.首先到maven的bin目录下D:\mySoftWare\maven\download\apache-maven-3.3.9\bin  (这是我的maven的bin路径)
## 2.执行命令：
mvn install:install-file -Dfile=C:\Users\xxx\Desktop\aspose-words-15.8.0-jdk16.jar -DgroupId=com.aspose.words -DartifactId=aspose-words-jdk16 -Dversion=15.8.0 -Dpackaging=jar

参照你在pom.xml中的依赖修改上述命令

```
<dependency>
    <groupId>com.aspose.words</groupId>
    <artifactId>aspose-words-jdk16</artifactId>
    <version>15.8.0</version>
</dependency>
```
## 3.刷新项目即可


