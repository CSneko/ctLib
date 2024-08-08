package org.cneko.ctlib.bootstrap;


import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.LibraryManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.cneko.ctlib.Meta;
import org.cneko.ctlib.plugin.util.LibrariesLoader;

public class BukkitBootstrap extends JavaPlugin {
    @Override
    public void onEnable() {
        // 设置Meta的Logger
        Meta.INSTANCE.setDefaultLogger(getLogger());
        // 初始化load
        LibraryManager libraryManager;
        libraryManager = new BukkitLibraryManager(this);
        LibrariesLoader.setManager(libraryManager);
        // 加载依赖项
        LibrariesLoader.load("org.xerial", "sqlite-jdbc", "3.41.2.2","org.sqlite.Collation");
        LibrariesLoader.load("org.jetbrains.kotlin", "kotlin-stdlib", "2.0.0-Beta1","kotlin.unit");
    }
}
