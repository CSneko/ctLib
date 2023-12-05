package com.crystalneko.ctlibfabric;

import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.sql.sqlite;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Logger;


public class CtLibFabric implements ModInitializer {
    public static CtLibFabric ModInit;
    public static String lang;
    public static Logger logger = Logger.getLogger("ctLib");
    private sqlite sqlite;
    @Override
    public void onInitialize() {
        //------------------------------------------------------初始化-------------------------------------------------
        Path libPath = Path.of("ctlib/libraries");
        if(!Files.exists(libPath)){try{Files.createDirectory(libPath);}catch(IOException e){logger.warning("Unable to create path:"+e);}}
        base.loader = "fabric";
        //运行基础配置
        lang = "en_us";
        new base(lang);
        //连接sqlite数据库
        this.sqlite = new sqlite("ctlib/playerData.db");
        try {
            sqlite.createConnection();
        } catch (SQLException e) {
            logger.severe("Unable to connect to sqlite:"+e);
        }
    }

}
