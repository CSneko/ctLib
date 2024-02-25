package org.cneko.ctlib.bootstrap;

import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.sql.sqlite;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Logger;

public class FabricBootstrap implements ModInitializer {
    public static String lang;
    public static Logger logger = Logger.getLogger("ctLib");
    @Override
    public void onInitialize() {
        //------------------------------------------------------初始化-------------------------------------------------
        base.loader = "fabric";
        //运行基础配置
        lang = "en_us";
        new base(lang);
        //连接sqlite数据库
        /*this.sqlite = new sqlite("ctlib/playerData.db");
        try {
            sqlite.createConnection();
        } catch (SQLException e) {
            logger.severe("Unable to connect to sqlite:"+e);
        }*/
    }
}
