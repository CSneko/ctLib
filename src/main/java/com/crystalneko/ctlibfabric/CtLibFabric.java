package com.crystalneko.ctlibfabric;

import com.crystalneko.ctlibfabric.sql.sqlite;
import net.fabricmc.api.ModInitializer;

import java.sql.SQLException;

public class CtLibFabric implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        //------------------------------------------------------初始化-------------------------------------------------
        //连接sqlite数据库
        try {
            sqlite.createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
