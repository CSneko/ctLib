package com.crystalneko.ctlibPublic.env;

import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;
import org.cneko.ctlib.plugin.util.LibrariesLoader;

import static org.cneko.ctlib.plugin.util.LibrariesLoader.manager;
import static org.cneko.ctlib.plugin.util.LibrariesLoader.isLoad;

public class libraries {

    public libraries(final LibraryManager manager){
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
