package com.crystalneko.ctlib;

import com.crystalneko.ctlib.command.ctEcomonyCommand;
import com.crystalneko.ctlib.command.ctPrefixCommand;
import com.crystalneko.ctlib.ecomony.playerEcomony;
import com.crystalneko.ctlib.event.onPlayerJoin;
import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.libraries.load;
import com.crystalneko.ctlibPublic.sql.sqlite;
import com.crystalneko.ctlibPublic.File.YamlConfiguration;
import com.crystalneko.ctlibPublic.sql.mysql;
import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

public final class CtLib extends JavaPlugin {
    public static YamlConfiguration config;
    private mysql mysql;
    private onPlayerJoin playerJoin;
    private playerEcomony playerEcomony;
    private sqlite sqlite;
    public static CtLib plugin;
    public static load load;
    public static String lang;
    @Override
    public void onEnable() {
        lang = "en_us";
        plugin = this;
        //初始化load
        LibraryManager libraryManager;
        libraryManager = new BukkitLibraryManager(this);
        load = new load(libraryManager);
        //运行基础配置
        new base(lang);
        //创建配置文件
        checkAndSaveResource("config.yml");
        //配置文件变量
        Path configPath = Path.of("plugins/ctLib/config.yml");
        try {
            config = new YamlConfiguration(configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //创建sqlite连接
        try {
            this.sqlite = new sqlite(config.getString("sqlite.path"));
            sqlite.createConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
        // -------------------------------------------------------------初始化------------------------------------------------------

        if(getConfig().getBoolean("mysql.enable")) {
            //创建mysql连接
            this.mysql = new mysql(config);
            getCommand("ctecomony").setExecutor(new ctEcomonyCommand(this));
            this.playerEcomony = new playerEcomony(this);
        }
        //注册监听器
        this.playerJoin = new onPlayerJoin(this);

        // -------------------------------------------------------------注册命令----------------------------------------------------
            getCommand("ctprefix").setExecutor(new ctPrefixCommand(this));
    }


    @Override
    public void onDisable() {

    }

































































































































    private void checkAndSaveResource(String filePath) {
        if (!isFileExists(filePath)) {
            saveResource(filePath, false);
        } else {
        }
    }

    private boolean isFileExists(String filePath) {
        File file = new File(getDataFolder(), filePath);
        return file.exists() && file.isFile();
    }
}
