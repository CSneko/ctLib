package org.cneko.ctlib.common.util.meta;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.util.logging.Logger;

public interface PluginMeta {
    /**
     * 获取插件数据文件夹
     * @return 插件数据文件夹
     */
    @NotNull
    File getDataFolder();

    /**
     * 获取日志输出器
     * @return 日志输出器
     */
    @NotNull
    Logger getLogger();

    /**
     * 获取插件信息
     * @return 插件信息
     */
    @NotNull
    Description getDescription();
    /**
     * 获取服务器信息
     * @return 服务器信息
     */
    @NotNull
    Server getServer();

    interface Description{
        /**
         * 获取插件名称
         *
         * @return 插件名称
         */
        @NotNull
        String getName();
        /**
         * 获取插件版本
         *
         * @return 插件版本
         */
        @Nullable
        String getVersion();

        /**
         * 获取插件描述
         * @return 插件描述
         */
        @Nullable
        String getDescription();
        /**
         * 获取插件网站
         * @return 插件网站
         */
        @Nullable
        String getWebsite();
        /**
         * 获取插件作者
         *
         * @return 插件作者
         */
        @Nullable
        String[] getAuthors();

    }
    interface Server{
        enum GameMode{
            SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR, UNKNOWN, OTHER
        }

        /**
         * 是否开启在线模式
         * @return 是否开启在线模式
         */
        boolean isOnlineMode();
        /**
         * 获取Minecraft版本
         * @return Minecraft版本
         */
        @Nullable
        String getVersion();

        /**
         * 获取服务器名称
         * @return 服务器名称
         */
        @Nullable
        String getName();

        /**
         * 获取服务器MOTD
         * @return 服务器MOTD
         */
        @Nullable
        String getMotd();

        /**
         * 获取在线玩家数量
         * @return 在线玩家数量
         */
        int getPlayerAmount();

        /**
         * 获取最大玩家数量
         * @return 最大玩家数量
         */
        int getMaxPlayers();

        /**
         * 获取服务器游戏模式
         * @return 服务器游戏模式
         */
        @Nullable
        GameMode getServerGamemode();
    }
}
