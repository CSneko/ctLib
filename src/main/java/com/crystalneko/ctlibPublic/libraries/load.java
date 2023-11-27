package com.crystalneko.ctlibPublic.libraries;

import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;

public class load {
    private static LibraryManager manager;
    public load(final LibraryManager manager){
        load.manager = manager;
        manager.addMavenCentral();
    }

    public static void loadLib(String groupId, String artifactId, String version) {
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
