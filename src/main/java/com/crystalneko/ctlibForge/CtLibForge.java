package com.crystalneko.ctlibForge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.sql.sqlite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Logger;

@Mod("ctlib")
public class CtLibForge {
    public static String lang;
    public static Logger logger = Logger.getLogger("ctLib");
    private sqlite sqlite;
    public CtLibForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::setup);
    }


    private void setup(final FMLCommonSetupEvent event) {
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
        logger.info("All done!");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }
}
