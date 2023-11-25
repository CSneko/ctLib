package com.crystalneko.ctlib.chat;

import org.bukkit.entity.Player;

import java.util.*;
import static com.crystalneko.ctlibPublic.inGame.chatPrefix.prefixes;
import static com.crystalneko.ctlibPublic.inGame.chatPrefix.privatePrefixes;

/**
 * 这个类是有关于聊天前缀的类，用于添加前缀，删除前缀，创建私有或公共前缀
 * 目前已经转移至 com.crystalneko.ctlibPublic.inGame.chatPrefix
 */
public class chatPrefix {




    /**
     * 为玩家创建一个私有前缀
     *
     * @param player      需要传入的玩家参数
     * @param prefixValue 前缀的值
     */
    public static void addPrivatePrefix(Player player, String prefixValue) {
        privatePrefixes.computeIfAbsent(player.getName(), k -> new ArrayList<>()).add(prefixValue);
    }

    /**
     * 为玩家删除一个私有前缀
     *
     * @param player      需要传入的玩家参数
     * @param prefixValue 前缀的值
     */
    public static void subPrivatePrefix(Player player, String prefixValue) {
        List<String> playerPrefixes = privatePrefixes.get(player.getName());
        if (playerPrefixes != null) {
            playerPrefixes.remove(prefixValue);
        }
    }

    /**
     * 获取所有公共前缀
     *
     * @return [前缀a§f§r][前缀b§f§r]
     */
    public static String getAllPublicPrefixValues() {
        StringBuilder result = new StringBuilder();
        for (String prefix : prefixes) {
            result.append("[").append(prefix).append("§f§r]");
        }
        return result.toString();
    }

    /**
     * 获取私有前缀
     *
     * @param player 玩家参数
     * @return [前缀a§f§r][前缀b§f§r],如果玩家没有前缀则返回"[无前缀]",如果没有任何前缀值则返回[无任何前缀]
     */
    public static String getPrivatePrefix(Player player) {
        List<String> playerPrefixes = privatePrefixes.get(player.getName());
        if (playerPrefixes != null && !playerPrefixes.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (String prefix : playerPrefixes) {
                result.append("[").append(prefix).append("§f§r]");
            }
            return result.toString();
        } else {
            return "[§a无前缀§f§r]";
        }
    }
}
