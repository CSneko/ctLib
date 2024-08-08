package org.cneko.ctlib.bootstrap;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.cneko.ctlib.Meta;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "ctlib", name = "ctLib", version = "0.1.0",
        url = "https://github.com/csneko/ctlib", description = "ctLib", authors = {"CrystalNeko"})
public class VelocityBootstrap {
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    @Inject
    private PluginManager pluginManager;
    @Inject
    public VelocityBootstrap(ProxyServer server, Logger logger, @DataDirectory Path description){
        this.server = server;
        this.logger = logger;
        this.dataDirectory = description;
    }
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Meta.INSTANCE.setSlf4jLogger(logger);
        //初始化load
        /*final LibraryManager libraries = new VelocityLibraryManager<>(
                logger, dataDirectory, pluginManager, this);
        LibrariesLoader.setManager(libraries);*/
    }
}
