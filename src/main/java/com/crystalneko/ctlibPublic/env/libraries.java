package com.crystalneko.ctlibPublic.env;

import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;

import java.util.HashMap;
import java.util.Map;

public class libraries {
    private static LibraryManager manager;
    private static Map<String,Boolean> isLoad = new HashMap<>();
    public libraries(final LibraryManager manager){
        libraries.manager = manager;
        manager.addMavenCentral();
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
