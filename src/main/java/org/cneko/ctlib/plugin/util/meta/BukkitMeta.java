package org.cneko.ctlib.plugin.util.meta;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cneko.ctlib.common.util.meta.PluginMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;


public class BukkitMeta implements PluginMeta {
    private final JavaPlugin plugin;
    private final PluginMeta.Description description;
    private final PluginMeta.Server server;
    public BukkitMeta(JavaPlugin plugin) {
        this.plugin = plugin;
        this.description = new BukkitDescription(plugin);
        this.server = new BukkitServer();
    }

    @Override
    public @NotNull File getDataFolder() {
        return plugin.getDataFolder();
    }

    @Override
    public @NotNull Logger getLogger() {
        return plugin.getLogger();
    }

    @Override
    public @NotNull Description getDescription() {
        return description;
    }
    @Override
    public @NotNull Server getServer() {
        return server;
    }
    public JavaPlugin getPlugin() {
        return plugin;
    }
    public static class BukkitDescription implements PluginMeta.Description {
        private final JavaPlugin plugin;
        public BukkitDescription(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public @NotNull String getName() {
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

        @Override
        public String @javax.annotation.Nullable [] getAuthors() {
            return plugin.getDescription().getAuthors().toArray(new String[100]);
        }
    }
    public static class BukkitServer implements PluginMeta.Server {
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

        @Override
        public int getMaxPlayers() {
            return Bukkit.getMaxPlayers();
        }


        @Nullable
        @Override
        public GameMode getServerGamemode() {
            switch (Bukkit.getDefaultGameMode()){
                case SURVIVAL:
                    return GameMode.SURVIVAL;
                case CREATIVE:
                    return GameMode.CREATIVE;
                case ADVENTURE:
                    return GameMode.ADVENTURE;
                case SPECTATOR:
                    return GameMode.SPECTATOR;
                default:
                    return GameMode.UNKNOWN;
            }
        }


    }
}
