package com.crystalneko.ctlibPublic;


import com.crystalneko.ctlibPublic.env.libraries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.crystalneko.ctlibPublic.lang.message.getMSG;

public class base {
    public static String lang;
    public static String loader;
    public base(String lang){
        this.lang = lang;
        //创建必要目录
        createPath();
        if(loader ==null || !loader.equalsIgnoreCase("fabric")) {
            //加载依赖
            libraries.load("mysql", "mysql-connector-java", "8.0.28");
            libraries.load("com.sun.mail", "javax.mail", "1.6.2");
            libraries.load("commons-codec", "commons-codec", "1.15");
            libraries.load("org.xerial", "sqlite-jdbc", "3.41.2.2");
            libraries.load("org.jetbrains.kotlin", "kotlin-stdlib", "2.0.0-Beta1");
        }

    }


    private void createPath(){
        //-----------------------------------------------------创建基础目录---------------------------------------------
        Path ctlib = Paths.get("ctlib/");
        if(!Files.exists(ctlib)) {
            try {
                Files.createDirectory(ctlib); // 创建目录
            } catch (IOException e) {
                System.out.println(getMSG("UnableToCreatePath") + e.getMessage());
            }
        }
        //--------------------------------------------------------创建数据目录------------------------------------------
        Path data = Paths.get("ctlib/data/");
        if(!Files.exists(data)){
            try {
                Files.createDirectory(data);
            } catch (IOException e){
                System.out.println(getMSG("UnableToCreatePath") + e.getMessage());
            }
        }
        //----------------------------------------------------创建依赖目录----------------------------------------------
        Path libraries = Paths.get("ctlib/libraries/");
        if(!Files.exists(libraries)){
            try {
                Files.createDirectory(libraries);
            } catch (IOException e){
                System.out.println(getMSG("UnableToCreatePath") + e.getMessage());
            }
        }
        //----------------------------------------------------创建缓存目录---------------------------------------------
        Path tempDir = Paths.get("ctlib/tempDir/");
        if(!Files.exists(libraries)){
            try {
                Files.createDirectory(libraries);
            } catch (IOException e){
                System.out.println(getMSG("UnableToCreatePath") + e.getMessage());
            }
        }
    }
    private void cleanTemp(){
        Path tempDir = Paths.get("ctlib/tempDir/");
        if(Files.exists(tempDir)){
            try {
                Files.delete(tempDir);
            } catch (IOException e) {
                System.out.println(getMSG("UnableToRemovePath")+e);
            }
        }
    }
}
