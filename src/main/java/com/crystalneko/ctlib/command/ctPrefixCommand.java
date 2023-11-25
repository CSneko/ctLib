package com.crystalneko.ctlib.command;

import com.crystalneko.ctlib.CtLib;
import com.crystalneko.ctlibPublic.inGame.chatPrefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ctPrefixCommand implements CommandExecutor {
    private CtLib plugin;
    public ctPrefixCommand(CtLib plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)| sender.hasPermission("ct.prefix")) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§b/ctpf帮助\n§a/ctpf add <玩家名称> <值> §b为玩家添加前缀\n§a/ctpf sub <玩家名称> <值> §b为玩家删除前缀\n§a/ctpf get <玩家名称>§b获取玩家的前缀\n§a/ctpf public add <值> §b添加公共前缀\n§a/ctpf public sub <值> §b删除公共前缀\n§a/ctpf public get <值> §b获取公共前缀");
            }else if(args[0].equalsIgnoreCase("add")){
                chatPrefix.addPrivatePrefix(args[1],args[2]);
                sender.sendMessage("§a成功为玩家§6" + args[1] + "§a添加前缀§e" + args[2]);
            } else if (args[0].equalsIgnoreCase("sub")) {
                chatPrefix.subPrivatePrefix(args[1],args[2]);
                sender.sendMessage("§a成功为玩家§6" + args[1] + "§a删除前缀§e" + args[2]);
            } else if (args[0].equalsIgnoreCase("get")) {
                sender.sendMessage("§a玩家§6" + args[1] + "§a拥有前缀: " + chatPrefix.getPrivatePrefix(args[1]));
            } else if (args[0].equalsIgnoreCase("public") | args[0].equalsIgnoreCase("pb")) {
                if(args[1].equalsIgnoreCase("add")){
                    chatPrefix.addPublicPrefix(args[2]);
                    sender.sendMessage("§a成功添加公共前缀§e" + args[2]);
                } else if (args[1].equalsIgnoreCase("sub")) {
                    chatPrefix.subPublicPrefix(args[2]);
                    sender.sendMessage("§a成功删除公共前缀§e" + args[2]);
                } else if (args[1].equalsIgnoreCase("get")) {
                    sender.sendMessage("§当前公共前缀:§e" +chatPrefix.getAllPublicPrefixValues());
                }
            }
        }else {sender.sendMessage("§c你没有权限使用这个命令!");}
        return true;
    }
}
