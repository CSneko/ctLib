# ctLib
ct系列插件的前置
## 你只需要下载并放入服务器的插件文件夹就行了,当然，它同样支持Fabric1.20.2
## 配置文件 (`config.yml`)
```yaml
sqlite:
  #sqlite路径（基于服务器根目录）
  path: "playerData.db"
mysql:
  #服务器域名
  host: 127.0.0.1
  #服务器端口
  port: 3306
  #数据库名
  database: dbName
  #用户名
  username: example
  #密码
  password: password
  #服务器时区
  time: GMT
  #sql编码
  char: UTF-8
  #是否使用ssl
  useSSL: true
```
## 添加依赖(maven)
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <dependency>
        <groupId>com.github.CSneko</groupId>
        <artifactId>ctLib</artifactId>
        <version>Tag</version>
    </dependency>
</dependency>
```
gradlew:
```Groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
    implementation 'com.github.CSneko:ctLib:Tag'
}
```
## 由于每次更新变化较大，因此暂时不建议将其设置为插件必须依赖
