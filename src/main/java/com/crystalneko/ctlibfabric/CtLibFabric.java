package com.crystalneko.ctlibfabric;

import com.crystalneko.ctlibPublic.base;
import com.crystalneko.ctlibPublic.libraries.load;
import com.crystalneko.ctlibPublic.sql.sqlite;
import com.crystalneko.ctlibfabric.libby.FabricLibraryManager;
import net.byteflux.libby.LibraryManager;
import net.fabricmc.api.ModInitializer;
import com.crystalneko.ctlibPublic.libraries.download;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.util.Optional;

import static com.crystalneko.ctlib.CtLib.load;
import static net.minecraft.text.Text.translatable;


public class CtLibFabric implements ModInitializer {
    public static CtLibFabric ModInit;
    public static String lang;

    @Override
    public void onInitialize() {
        //------------------------------------------------------初始化-------------------------------------------------
        //初始化Lib加载器
        ModInit = this;
        //LibraryManager libraryManager = new FabricLibraryManager();
        //load = new load(libraryManager);

        // 检查目标libMod是否已加载
        Optional<ModContainer> targetModOptional = FabricLoader.getInstance().getModContainer("csnklib");
        ModContainer targetMod = targetModOptional.isPresent() ? targetModOptional.get() : null;
        boolean isModLoaded = (targetMod != null);
        if(!isModLoaded) {
            try {
                download.downloadFile("https://w.csk.asia/res/lib/csnklibFabric.jar", "mods/", "CSNKLib.jar");
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        //运行基础配置
        lang = "en_us";
        //new base(lang);
        //连接sqlite数据库
        new sqlite("ctlib/playerData.db");
    }

    public static CtLibFabric getInit(){
        return ModInit;
    }
}
