package com.crystalneko.ctlibfabric;

import com.crystalneko.ctlibPublic.sql.sqlite;
import net.fabricmc.api.ModInitializer;

import java.nio.file.Path;


public class CtLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        //------------------------------------------------------初始化-------------------------------------------------

        //连接sqlite数据库
        new sqlite("ctlib/data.db");
    }
}
