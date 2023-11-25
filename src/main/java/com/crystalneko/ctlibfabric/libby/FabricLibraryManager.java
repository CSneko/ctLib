package com.crystalneko.ctlibfabric.libby;

import com.crystalneko.ctlibfabric.CtLibFabric;
import net.fabricmc.loader.api.FabricLoader;
import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.logging.adapters.SLF4JLogAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;


public class FabricLibraryManager extends LibraryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FabricLibraryManager.class);
    public static CtLibFabric ModInit;

    public FabricLibraryManager() {
        super(new SLF4JLogAdapter(LOGGER), FabricLoader.getInstance().getGameDir().resolve("mods"));
        ModInit = CtLibFabric.getInit();
    }

    @Override
    protected void addToClasspath(Path file) {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader instanceof URLClassLoader) {
                Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURLMethod.setAccessible(true);
                addURLMethod.invoke(contextClassLoader, file.toUri().toURL());
            } else {
                LOGGER.error("Unsupported ClassLoader type: {}", contextClassLoader.getClass().getName());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to add {} to classpath", file, e);
        }
    }

}



