# ctLib
ct系列插件的前置
## 使用方法:
如果你是玩家或服务器管理员，只需要放入`plugins`或`mods`文件夹即可，无需在意版本，因为它不会受到Minecraft版本的影响
## 配置文件 (`config.yml`)
```yaml
sqlite:
  #sqlite路径（基于服务器根目录）
  path: "ctlib/playerData.db"
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
## 注意事项:
- 请不要删除`ctlib`文件夹及其文件夹内的任何文件，因为这回导致依赖于该插件的mod/插件数据丢失!
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
## API
#### 各端API包名:
- 多端通用: `com.crystalneko.ctlibPublic`
- Fabric: `com.crystalneko.ctlibfabric`
- Forge: `com.crystalneko.ctlibForge`
- Bukkit: `com.crystalneko.ctlib`
- Velocity: `com.crystalneko.ctlibVelocity`
### 调用示例
```java
import com.crystalneko.ctlibPublic.env.libraries;
import com.crystalneko.ctlibPublic.inGame.chatPrefix;
public class example{
    //从maven中心仓库下载并加载库文件示例
    public void loadLib(){
        //该示例中，"mysql"为groupId,"mysql-connector-java"为artifactId，"8.0.26"为版本号
        libraries.load("mysql", "mysql-connector-java", "8.0.26");
    }
    //聊天前缀示例(假设player为玩家)
    public void chat(Player player){
        //添加一个公共前缀
        chatPrefix.addPublicPrefix("前缀");
        //删除一个公共前缀
        chatPrefix.subPublicPrefix("前缀");
        //为玩家创建一个私有前缀
        chatPrefix.addPrivatePrefix(player.getName(),"私有前缀");
        //为玩家删除一个私有前缀
        chatPrefix.subPrivatePrefix(player.getName(),"私有前缀");
        //获取所有公共前缀
        String publicPrefix = chatPrefix.getAllPublicPrefixValues();
        //获取私有前缀
        String privatePrefix = chatPrefix.getPrivatePrefix(player.getName());
    }
}
```
