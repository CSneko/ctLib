## 关于
这是一个简易的库，主要用于操作SQLite,Json,Yaml以及网络请求，支持以下平台:
Fabric,Quilt,Forge,NeoForge,Bukkit,Spigot,Paper,Purpur,Folia

**该模组/插件需要至少java11才能运行**
## 导入
你可以使用modrinth maven来导入它

Gralde
```groovy
repositories {
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
    }
}

dependencies {
implementation "maven.modrinth:ctlibmod:0.1.4"
}
```

Maven
```xml
<repositories>
    <repository>
        <id>Modrinth</id>
        <url>https://api.modrinth.com/maven</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>maven.modrinth</groupId>
        <artifactId>ctlibmod</artifactId>
        <version>0.1.4</version>
    </dependency>
</dependencies>

```
