package com.crystalneko.ctlib;

import com.crystalneko.ctlib.chat.chatPrefix;
import org.bukkit.plugin.java.JavaPlugin;

public final class CtLib extends JavaPlugin {
    private chatPrefix ChatPrefix;

    @Override
    public void onEnable() {
        //初始化聊天监听器
        ChatPrefix = new chatPrefix(this);

    }


    @Override
    public void onDisable() {

    }
}
