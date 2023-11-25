package com.crystalneko.ctlibPublic.libraries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class lib {
    private static Map<String, Boolean> loaded = new HashMap<>();

    public static void loadJar(String[] urls, String[] names) {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (isLoaded(name)) {
                continue; // 如果已经加载则跳过
            }

            Path jarFile = Path.of("ctlib/libraries/" + name + ".jar");
            // 如果文件存在则加载，否则下载后加载
            if (!Files.exists(jarFile)) {
                try {
                    System.out.println("Downloading libraries \"" + name + "\"");
                    download.downloadFile(urls[i], "ctlib/libraries/", name + ".jar");
                } catch (IOException e) {
                    System.out.println("Unable to download libraries \"" + name + "\" :" + e);
                    continue;
                }
            }
            try {
                String path = "ctlib/libraries/" + name + ".jar";
                //load.loadLib(new String[]{path});
                loaded.put(name, true);
                System.out.println("Load libraries " + name + " successfully!");
            } catch (Exception e) {
                System.out.println("Unable to load libraries \"" + name + "\" :" + e);
            }
        }
    }


    /**
     * 加载依赖项
     * @param pack 依赖的groupID
     */
    /*public static void load(String pack){
        if(isLoaded(pack)){return;}
        //判断依赖是否存在
        if(download.isDownload(pack) && loaded.get(pack) == null){
            try {
                //加载依赖
                load.loadLib(download.getJarPath(new String[]{pack}));
                System.out.println("Load libraries "+pack+" successfully!");
            } catch (Exception e) {
                System.out.println("Unable to load libraries："+ e);
            }
        } else if (!download.isDownload(pack)) {
            download.downloadJar(pack);
            try {
                load.loadLib(download.getJarPath(new String[]{pack}));
            } catch (Exception e) {
                 System.out.println("Unable to load libraries："+ e);
            }
        }
        //写入map
        loaded.put(pack,true);

    }
    public static void loadJar(String pack){
        if(isLoaded(pack)){return;}
        //判断依赖是否存在
        if(download.isDownload(pack) && loaded.get(pack) == null){
            try {
                //加载依赖
                load.loadLib(download.getJarPath(pack));
            } catch (Exception e) {
                System.out.println("Unable to load libraries："+ e);
            }
        }

    }
    */


    /**
     * 判断依赖是否已经加载
     * @param pack 包名
     * @return 已经加载则返回true,否则返回false
     */
    public static Boolean isLoaded(String pack){
        return loaded.get(pack) != null;
    }
}
