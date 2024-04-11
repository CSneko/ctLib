package org.cneko.ctlib.plugin.util.meta;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cneko.ctlib.common.util.meta.ModMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;

public class BukkitMeta implements ModMeta {
    private final JavaPlugin plugin;
    private final ModMeta.Description description;
    private final ModMeta.Server server;
    public BukkitMeta(JavaPlugin plugin) {
        this.plugin = plugin;
        this.description = new BukkitDescription(plugin);
        this.server = new BukkitServer();
    }

    @Override
    public File getDataFolder() {
        return plugin.getDataFolder();
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }

    @Override
    public @NotNull Description getDescription() {
        return description;
    }


    @Override
    public @NotNull Server getServer() {
        return null;
    }
    public static class BukkitDescription implements ModMeta.Description {
        private final JavaPlugin plugin;
        public BukkitDescription(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        @Nullable
        @Override
        public String getName() {
            return plugin.getName();
        }

        @Nullable
        @Override
        public String getVersion() {
            return plugin.getDescription().getVersion();
        }

        @Nullable
        @Override
        public String getDescription() {
            return plugin.getDescription().getDescription();
        }

        @Nullable
        @Override
        public String getWebsite() {
            return plugin.getDescription().getWebsite();
        }

        @Nullable
        @Override
        public String[] getAuthors() {
            return plugin.getDescription().getAuthors().toArray(new String[100]);
        }
    }
    public static class BukkitServer implements ModMeta.Server {
        @Override
        public boolean isOnlineMode() {
            return Bukkit.getOnlineMode();
        }

        @Override
        public String getVersion() {
            return Bukkit.getVersion();
        }

        @Override
        public String getName() {
            return Bukkit.getName();
        }

        @Nullable
        @Override
        public String getMotd() {
            return Bukkit.getMotd();
        }

        @Override
        public int getPlayerAmount() {
            return Bukkit.getOnlinePlayers().size();
        }


    }
}
