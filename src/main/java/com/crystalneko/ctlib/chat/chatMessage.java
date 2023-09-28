package com.crystalneko.ctlib.chat;

import com.crystalneko.ctlib.CtLib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import static org.bukkit.Bukkit.getServer;

public class chatMessage implements Listener {
    private CtLib ctLib;
    public chatPrefix ChatPrefix;
    public chatMessage(CtLib ctLib,chatPrefix ChatPrefix){
        this.ctLib = ctLib;
        this.ChatPrefix = ChatPrefix;
        //注册玩家聊天监听器
        getServer().getPluginManager().registerEvents(this, ctLib);
    }
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        //定义玩家和消息变量
        Player player = event.getPlayer();
        String message = event.getMessage();
        //获取前缀值
        String prefixes = ChatPrefix.convertArray(ChatPrefix.PrefixesValue);
        // 修改消息的格式并重新发送
        event.setFormat(prefixes + player.getDisplayName() + " >> §7" + message);
    }
}
