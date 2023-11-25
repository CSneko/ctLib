package com.crystalneko.ctlibPublic;


import com.crystalneko.ctlibPublic.libraries.load;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class base {

    public base(){

        //创建必要目录
        createPath();
        //加载依赖
        load.loadLib("mysql","mysql-connector-java","8.0.26");
        load.loadLib("com.sun.mail","javax.mail","1.6.2");
        load.loadLib("commons-codec","commons-codec","1.15");
        load.loadLib("org.xerial","sqlite-jdbc","3.34.0");
        load.loadLib("org.xerial","sqlite-jdbc","3.34.0");
        load.loadLib("org.jetbrains.kotlin","kotlin-stdlib","2.0.0-Beta1");

    }


    private void createPath(){
        //-----------------------------------------------------创建基础目录---------------------------------------------
        Path ctlib = Paths.get("ctlib/");
        if(!Files.exists(ctlib)) {
            try {
                Files.createDirectory(ctlib); // 创建目录
            } catch (IOException e) {
                System.out.println("目录创建失败：" + e.getMessage());
            }
        }
        //--------------------------------------------------------创建数据目录------------------------------------------
        Path data = Paths.get("ctlib/data/");
        if(!Files.exists(data)){
            try {
                Files.createDirectory(data);
            } catch (IOException e){
                System.out.println("目录创建失败：" + e.getMessage());
            }
        }
        //----------------------------------------------------创建依赖目录----------------------------------------------
        Path libraries = Paths.get("ctlib/libraries/");
        if(!Files.exists(libraries)){
            try {
                Files.createDirectory(libraries);
            } catch (IOException e){
                System.out.println("目录创建失败：" + e.getMessage());
            }
        }
        //----------------------------------------------------创建缓存目录---------------------------------------------
        Path tempDir = Paths.get("ctlib/tempDir/");
        if(!Files.exists(libraries)){
            try {
                Files.createDirectory(libraries);
            } catch (IOException e){
                System.out.println("目录创建失败：" + e.getMessage());
            }
        }
    }
    private void cleanTemp(){
        Path tempDir = Paths.get("ctlib/tempDir/");
        if(Files.exists(tempDir)){
            try {
                Files.delete(tempDir);
            } catch (IOException e) {
                System.out.println("Unable to delete temp dir:"+e);
            }
        }
    }
}
