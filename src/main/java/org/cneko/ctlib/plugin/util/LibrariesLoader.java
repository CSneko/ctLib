package org.cneko.ctlib.plugin.util;

import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibrariesLoader {
    public static LibraryManager manager;
    public static Map<String,Boolean> isLoad = new ConcurrentHashMap<>();
    public static void setManager(final LibraryManager manager){
        LibrariesLoader.manager = manager;
        LibrariesLoader.manager.addMavenCentral();
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
