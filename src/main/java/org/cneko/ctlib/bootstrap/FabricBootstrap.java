package org.cneko.ctlib.bootstrap;

import net.fabricmc.api.ModInitializer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricBootstrap implements ModInitializer {
    public static Logger logger = Logger.getLogger("ctLib");
    @Override
    public void onInitialize() {
        // 创建必要的目录
        Path dataFolder = Path.of("ctlib");
        if (!Files.exists(dataFolder)) {
            try {
                Files.createDirectory(dataFolder);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }

    }
}
