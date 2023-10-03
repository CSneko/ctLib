# ctLib
ct系列插件的前置
## 你只需要下载并放入服务器的插件文件夹就行了
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
## 下面是一些API的调用方法
### 经济系统
```java
//导入API
import com.crystalneko.ctlib.ecomony.playerEcomony;

//获取玩家的uuid
String uuid = String.valueOf(player.getUniqueId());
//为玩家添加5的余额
addEcomony(uuid, 5);
//为玩家减少5的余额
subEcomony(uuid, 5);
//获取玩家余额
int value = getEcomony(uuid);
//设置玩家的余额为10
setEcomony(uuid,10);
```
