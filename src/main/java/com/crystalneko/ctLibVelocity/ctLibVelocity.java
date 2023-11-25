package com.crystalneko.ctLibVelocity;

import com.crystalneko.ctlibPublic.File.YamlConfiguration;
import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.libraries.load;
import com.crystalneko.ctlibPublic.sql.mysql;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.VelocityLibraryManager;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Plugin(id = "ctlib", name = "ctLib", version = "${version}",
        url = "https://w.csk.asia", description = "ctLib", authors = {"CrystalNeko"})
public class ctLibVelocity {
    public static YamlConfiguration config;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private mysql mysql;
    public static ctLibVelocity plugin;
    @Inject
    private PluginManager pluginManager;

    @Inject
    public ctLibVelocity(ProxyServer server, Logger logger,@DataDirectory Path description) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = description;

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        plugin = this;
        //初始化load
        final LibraryManager libraries = new VelocityLibraryManager<>(
                        logger, dataDirectory, pluginManager, this);
        new load(libraries);
        //运行基础配置
        new base();
        //判断是否存在配置文件
        if (!Files.exists(Paths.get("plugins/ctLib"))) {
            try {
                Files.createDirectories(Paths.get("plugins/ctLib"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        File configFile = new File("plugins/ctLib/config.yml");
        if(!configFile.exists()){
            copyConfig();
        }
        Path configPath = Path.of("plugins/ctLib/config.yml");
        //获取配置文件
        try {
            config = new YamlConfiguration(configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //初始化mysql
        if(config.getBoolean("mysql.enable")){
            this.mysql = new mysql(config);
        }

    }
    public void copyConfig() {
        String resourcePath = "/config.yml";

        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.out.println("无法找到资源文件");
                return;
            }

            // 获取插件的数据文件夹路径
            Path pluginDataFolder = Path.of("plugins/ctLib");

            // 将资源文件复制到插件数据文件夹下的ctlib目录
            Path outputPath = pluginDataFolder.resolve("config.yml");
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("资源文件复制成功");

        } catch (IOException e) {
            System.out.println("无法复制资源文件" + e);
        }


    }
}