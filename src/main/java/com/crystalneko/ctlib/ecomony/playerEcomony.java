package com.crystalneko.ctlib.ecomony;

import com.crystalneko.ctlib.CtLib;
import com.crystalneko.ctlib.sql.mysql;

public class playerEcomony {
    private CtLib plugin;
    /**
     * 有关经济的类，数据存储在mysql里面
     */
    private String[] columName = {"uuid","Ecomony"};
    public playerEcomony(CtLib plugin){
        this.plugin = plugin;
        //创建表用于存储经济
        addSqliteColum();
    }

    /**
     * 用于为玩家添加余额
     * @param uuid 玩家的UUID
     * @param value 添加的值
     * @return 是否成功添加（成功则返回true）
     */
    public static Boolean addEcomony(String uuid, int value){
        if(mysql.checkValueExists("ctEcomony","uuid",uuid)){
            //获取玩家的经济
            int Ecomony = Integer.parseInt(mysql.getColumnValue("ctEcomony","Ecomony","uuid",uuid));
            //添加值
            Ecomony = Ecomony + value;
            //将值写入数据库
            mysql.saveDataWhere("ctEcomony","Ecomony","uuid",uuid, String.valueOf(Ecomony));
            return true;
        }else {
            return false;
        }
    }
    /**
     * 用于为玩家减少余额
     * @param uuid 玩家的UUID
     * @param value 减少的值
     * @return 是否成功减少（成功则返回true）
     */
    public static Boolean subEcomony(String uuid, int value){
        if(mysql.checkValueExists("ctEcomony","uuid",uuid)){
            //获取玩家的经济
            int Ecomony = Integer.parseInt(mysql.getColumnValue("ctEcomony","Ecomony","uuid",uuid));
            //减少值
            Ecomony = Ecomony - value;
            //将值写入数据库
            mysql.saveDataWhere("ctEcomony","Ecomony","uuid",uuid, String.valueOf(Ecomony));
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取玩家的余额
     * @param uuid 玩家的uuid
     * @return 玩家的余额值
     */
    public static int getEcomony(String uuid){
        if(mysql.checkValueExists("ctEcomony","uuid",uuid)){
            //获取玩家的经济
            int Ecomony = Integer.parseInt(mysql.getColumnValue("ctEcomony","Ecomony","uuid",uuid));
            return Ecomony;
        }else {
            return 0;
        }
    }

    /**
     * 设置玩家的余额
     * @param uuid 玩家的uuid
     * @param Value 要设置的值
     * @return 是否设置成功（成功则返回true）
     */
    public static Boolean setEcomony(String uuid,int Value){
        if(mysql.checkValueExists("ctEcomony","uuid",uuid)){
            //将值写入数据库
            mysql.saveDataWhere("ctEcomony","Ecomony","uuid",uuid, String.valueOf(Value));
            return true;
        }else {
            return false;
        }
    }




    private void addSqliteColum(){
        int i =0;
        while (i <= 1) {
            mysql.addColumn("ctEcomony", columName[i]);
            i ++;
        }
    }
}
