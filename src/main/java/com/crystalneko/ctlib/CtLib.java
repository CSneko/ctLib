package com.crystalneko.ctlib;

import com.crystalneko.ctlib.command.ctEcomonyCommand;
import com.crystalneko.ctlib.ecomony.playerEcomony;
import com.crystalneko.ctlib.event.onPlayerJoin;
import com.crystalneko.ctlib.sql.sqlite;
import com.crystalneko.ctlibPublic.File.YamlConfiguration;
import com.crystalneko.ctlibPublic.sql.mysql;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

public final class CtLib extends JavaPlugin {
    private mysql mysql;
    private onPlayerJoin playerJoin;
    private playerEcomony playerEcomony;
    private sqlite sqlite;
    @Override
    public void onEnable() {
        //创建配置文件
        checkAndSaveResource("config.yml");

        //创建sqlite连接
        try {
            this.sqlite = new sqlite(this);
            sqlite.createConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
        // -------------------------------------------------------------初始化------------------------------------------------------

        if(getConfig().getBoolean("mysql.enable")) {
            Path configPath = Path.of("plugins/ctLib/config.yml");
            YamlConfiguration config = null;
            try {
                config = new YamlConfiguration(configPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //创建mysql连接
            this.mysql = new mysql(config);
            new com.crystalneko.ctlib.sql.mysql(this);
            getCommand("ctecomony").setExecutor(new ctEcomonyCommand(this));
            this.playerEcomony = new playerEcomony(this);
        }
        //注册监听器
        this.playerJoin = new onPlayerJoin(this);

        // -------------------------------------------------------------注册命令----------------------------------------------------

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
