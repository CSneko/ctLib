package org.cneko.ctlib.bootstrap;


import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.LibraryManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.cneko.ctlib.plugin.util.LibrariesLoader;

import static org.cneko.ctlib.common.util.LocalDataBase.Connections.sqlite;

public class BukkitBootstrap extends JavaPlugin {
    @Override
    public void onEnable() {
        // 初始化load
        LibraryManager libraryManager;
        libraryManager = new BukkitLibraryManager(this);
        LibrariesLoader.setManager(libraryManager);
        // 加载依赖项
        LibrariesLoader.load("org.xerial", "sqlite-jdbc", "3.41.2.2");
        LibrariesLoader.load("org.jetbrains.kotlin", "kotlin-stdlib", "2.0.0-Beta1");
    }
    @Override
    public void onDisable(){
        // 断开数据库连接
        sqlite.disconnect();
    }
}
