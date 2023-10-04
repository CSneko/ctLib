package com.crystalneko.ctlib.command;

import com.crystalneko.ctlib.CtLib;
import com.crystalneko.ctlib.ecomony.playerEcomony;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ctEcomonyCommand implements CommandExecutor {
    private CtLib plugin;
    public ctEcomonyCommand(CtLib plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(player.hasPermission("ct.ecomony.command")) {
            if (args[0].equalsIgnoreCase("add") && args.length == 3) {
                //添加余额
                if(playerEcomony.addEcomony(String.valueOf(player.getUniqueId()), StringToInt(args[2], player))){
                    player.sendMessage("§a成功设置余额");
                } else {
                    player.sendMessage("§c设置余额失败");
                }
            } else if(args[0].equalsIgnoreCase("sub") && args.length == 3){
                //减少余额
                if(playerEcomony.subEcomony(String.valueOf(player.getUniqueId()), StringToInt(args[2], player))){
                    player.sendMessage("§a成功设置余额");
                }else {
                   player.sendMessage("§c设置余额失败");
                }
            } else if(args[0].equalsIgnoreCase("set") && args.length == 3){
                //设置余额
                if(playerEcomony.setEcomony(String.valueOf(player.getUniqueId()), StringToInt(args[2], player))){
                    player.sendMessage("§a成功设置余额");
                } else {
                    player.sendMessage("§c设置余额失败");
                }
            }else if(args[0].equalsIgnoreCase("get") && args.length == 2){
                //获取余额
                player.sendMessage("§a玩家"+args[1]+"的余额为§b"+playerEcomony.getEcomony(String.valueOf(player.getUniqueId())));
            }
            else if(args[0].equalsIgnoreCase("help")){
                player.sendMessage("§b/cteco帮助\n§a/cteco add <玩家名称> <值> §b为玩家添加余额\n§a/cteco sub <玩家名称> <值> §b为玩家减少余额\n§a/cteco set <玩家名称> <值> §b为玩家设置余额\n§a/cteco get <玩家名称> <值> §b获取玩家的余额");
            }
        } else {player.sendMessage("你没有权限执行该命令");}
        return true;
    }

    //判断数值是否能转换成整数
    private int StringToInt(String value,Player player){
        try{
            int num = Integer.parseInt(value);
            return num;
        }catch (NumberFormatException e){
            player.sendMessage("输入的值有误，请输入一个整数");
            return 0;
        }
    }
}
