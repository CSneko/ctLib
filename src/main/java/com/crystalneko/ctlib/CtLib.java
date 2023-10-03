package com.crystalneko.ctlib;

import com.crystalneko.ctlib.sql.sqlite;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class CtLib extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("国庆&中秋快乐！");
        //创建sqlite连接
        try {
            sqlite.createConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    @Override
    public void onDisable() {

    }
}
