package com.crystalneko.ctlibPublic.inGame;

import java.util.*;

import static org.cneko.ctlib.common.util.ChatPrefix.privatePrefixes;

import static org.cneko.ctlib.common.util.ChatPrefix.prefixes;

/**
 * 这个类是有关于聊天前缀的类，用于添加前缀，删除前缀，创建私有或公共前缀
 */
@Deprecated
public class chatPrefix {


    /**
     * 这是玩家聊天前缀设置,用于添加公共前缀
     *
     * @param prefix     聊天前缀
     */
    public static void addPublicPrefix(String prefix) {
        prefixes = Arrays.copyOf(prefixes, prefixes.length + 1);
        prefixes[prefixes.length - 1] = prefix;
    }

    /**
     * 这是玩家聊天前缀设置,用于减去公共前缀（前提是已经被添加过）
     *
     * @param prefixName 聊天前缀名称（String）,这是个唯一的值，用于定义你的聊天前缀
     */
    public static void subPublicPrefix(String prefixName) {
        List<String> prefixList = new ArrayList<>(Arrays.asList(prefixes));
        prefixList.remove(prefixName);
        prefixes = prefixList.toArray(new String[0]);
    }

    /**
     * 为玩家创建一个私有前缀
     *
     * @param name        玩家名称
     * @param prefixValue 前缀的值
     */
    public static void addPrivatePrefix(String name, String prefixValue) {
        privatePrefixes.computeIfAbsent(name, k -> new ArrayList<>()).add(prefixValue);
    }

    /**
     * 为玩家删除一个私有前缀
     *
     * @param name        需要传入的玩家参数
     * @param prefixValue 前缀的值
     */
    public static void subPrivatePrefix(String name, String prefixValue) {
        List<String> playerPrefixes = privatePrefixes.get(name);
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
     * @param name 玩家参数
     * @return [前缀a§f§r][前缀b§f§r],如果玩家没有前缀则返回"[无前缀]",如果没有任何前缀值则返回[无任何前缀]
     */
    public static String getPrivatePrefix(String name) {
        List<String> playerPrefixes = privatePrefixes.get(name);
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
