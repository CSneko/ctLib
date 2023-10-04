package com.crystalneko.ctlib.event;

import com.crystalneko.ctlib.CtLib;
import com.crystalneko.ctlib.sql.mysql;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {
    private String[] columName = {"Ecomony","uuid"};
    private CtLib plugin;
    public onPlayerJoin(CtLib plugin){
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //初始化玩家经济余额
        //获取玩家uuid
        String uuid = String.valueOf(player.getUniqueId());
        String[] columnValue = {"0","uuid"};
        //写入uuid
        if(!mysql.checkValueExists("ctEcomonyCommand","uuid",uuid)) {
            mysql.saveData("ctEcomonyCommand", "uuid", uuid);
        }
        mysql.saveDataWhere("ctEcomonyCommand",columName[0],"uuid",uuid,columnValue[0]);
    }
}
