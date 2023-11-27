package com.crystalneko.ctlibfabric.libby;


import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.JDKLogAdapter;
import net.byteflux.libby.LibraryManager;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.logging.Logger;



public class FabricLibraryManager extends LibraryManager {

    private final URLClassLoaderHelper classLoader;

    public FabricLibraryManager(String directoryName) {
        super(new JDKLogAdapter(Logger.getLogger("ctLibLibraryLoader")), Path.of("ctlib/libraries/"),directoryName);
        ClassLoader CLoader = this.getClass().getClassLoader();
        URLClassLoader urlClassLoader = null;
        if (CLoader instanceof URLClassLoader) {
            urlClassLoader = (URLClassLoader) CLoader;
        } else {
            URL[] urls = new URL[0];
            urlClassLoader = new URLClassLoader(urls, CLoader);
        }
        classLoader = new URLClassLoaderHelper(urlClassLoader,this);
    }

    @Override
    protected void addToClasspath(Path file) {
        this.classLoader.addToClasspath(file);
    }
}



