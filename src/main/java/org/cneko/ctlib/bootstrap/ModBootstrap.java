package org.cneko.ctlib.bootstrap;

import org.cneko.ctlib.Meta;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class ModBootstrap {
    public static void main(String[] args) {
        // 创建必要的目录
        Path dataFolder = Path.of("ctlib");
        if (!Files.exists(dataFolder)) {
            try {
                Files.createDirectory(dataFolder);
            } catch (Exception e) {
                Meta.INSTANCE.getDefaultLogger().log(Level.SEVERE, e.getMessage());
            }
        }

        // 设置LibraryManger
        /*LibraryManager libraryManager;
        libraryManager = new LibrariesLoader.Manger();
        LibrariesLoader.setManager(libraryManager);*/
    }
}
