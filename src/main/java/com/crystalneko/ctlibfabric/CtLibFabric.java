package com.crystalneko.ctlibfabric;

import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.sql.sqlite;
import net.fabricmc.api.ModInitializer;


public class CtLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        //------------------------------------------------------初始化-------------------------------------------------
        //运行基础配置
        new base();
        //连接sqlite数据库
        new sqlite("ctlib/data.db");
    }
}
