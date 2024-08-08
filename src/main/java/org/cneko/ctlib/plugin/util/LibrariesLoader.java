package org.cneko.ctlib.plugin.util;

import com.alessiodp.libby.Library;
import com.alessiodp.libby.LibraryManager;
import org.cneko.ctlib.common.network.PingTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibrariesLoader {
    public static LibraryManager manager;
    public static Map<String,Boolean> isLoad = new ConcurrentHashMap<>();
    public static void setManager(final LibraryManager manager){
        LibrariesLoader.manager = manager;
        LibrariesLoader.manager.addMavenCentral();
        String netUrl = "http://mirrors.163.com/maven/repository/maven-public/";
        if(PingTest.simplePing(netUrl) <= 300) {
            addRepository(netUrl);
        }
    }

    public static void addRepository(String url){
        LibrariesLoader.manager.addRepository(url);
    }

    public static void load(String groupId,String artifactId,String version,String tryClass){
        try{
            Class.forName(tryClass);
        }catch (ClassNotFoundException e){
            // 没有这个类，加载
            load(groupId,artifactId,version);
        }
    }

    public static void load(String groupId,String artifactId,String version){
        String longName = groupId + artifactId;
        //如果没有加载过，则进行加载
        if(!isLoad.containsKey(longName) || !isLoad.get(longName)){
            loadLib(groupId,artifactId,version);
            isLoad.put(longName,true);
        }
    }
    private static void loadLib(String groupId, String artifactId, String version) {
        if (manager == null) {
            throw new IllegalStateException("LibraryManager is not initialized");
        }
        final Library library = Library.builder()
                .groupId(groupId)
                .artifactId(artifactId)
                .version(version)
                .build();

        manager.loadLibrary(library);
    }


}
