package com.crystalneko.ctlibPublic.lang;

import static com.crystalneko.ctlibPublic.base.lang;

public class message {
    public static String getMSG(String key){
        if(lang.equalsIgnoreCase("en_us")){
            if(key.equalsIgnoreCase("UnableToCreatePath")){return "Unable to create path:";}
            if(key.equalsIgnoreCase("UnableToRemovePath")){return "Unable to remove path:";}
            if(key.equalsIgnoreCase("ConnectToMysql")){return "Connect to mysql database successfully!";}
            if(key.equalsIgnoreCase("UnConnectToMysql")){return "Unable to connect mysql database";}
        }
        if(lang.equalsIgnoreCase("zh_cn")){
            if(key.equalsIgnoreCase("UnableToCreatePath")){return "无法创建目录:";}
            if(key.equalsIgnoreCase("UnableToRemovePath")){return "无法删除目录:";}
            if(key.equalsIgnoreCase("ConnectToMysql")){return "成功连接mysql数据库!";}
            if(key.equalsIgnoreCase("UnConnectToMysql")){return "无法连接至mysql数据库";}
        }
        return null;
    }
}
