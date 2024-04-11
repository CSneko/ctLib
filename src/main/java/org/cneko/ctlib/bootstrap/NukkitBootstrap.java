package org.cneko.ctlib.bootstrap;

import cn.nukkit.plugin.PluginBase;
import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.NukkitLibraryManager;
import org.cneko.ctlib.plugin.util.LibrariesLoader;

public class NukkitBootstrap extends PluginBase {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        // 初始化load
        LibraryManager libraryManager;
        libraryManager = new NukkitLibraryManager(this);
        LibrariesLoader.setManager(libraryManager);
        // 加载依赖项
        LibrariesLoader.load("org.xerial", "sqlite-jdbc", "3.41.2.2");
        LibrariesLoader.load("org.jetbrains.kotlin", "kotlin-stdlib", "2.0.0-Beta1");

    }

    @Override
    public void onDisable() {
    }
}
