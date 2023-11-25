package com.crystalneko.ctlibPublic.libraries;

import java.io.File;
import java.nio.file.Path;

import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.relocation.Relocation;

public class load {
    private static LibraryManager manager;
    public load(final LibraryManager manager){
        this.manager = manager;
        manager.addMavenCentral();
    }

    public static void loadLib(String groupId, String artifactId, String version) {
        final Library library = Library.builder()
                .groupId(groupId)
                .artifactId(artifactId)
                .version(version)
                .build();

        manager.loadLibrary(library);
    }




}
