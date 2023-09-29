package com.crystalneko.ctlib.chat;


import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 这个类是有关于聊天前缀的类，用于添加前缀，删除前缀，创建私人或公共前缀
 */
public  class chatPrefix{

    private static String[] Prefixes =new String[1000];
    public static String[][] PrefixesValue = {{"0"}};
    private static int PrefixesNumber = 0;
    private static int deleteValueNumber = -1;
    public static String[][] privatePrefix = new String[2][10000];
    public static int privatePrefixNumber = 0;


    /**
     * 这是玩家聊天前缀设置,用于添加公共前缀
     * @param prefixName 聊天前缀名称（String）,这是个唯一的值，用于定义你的聊天前缀
     * @param prefix 聊天前缀的内容（String[]）,可以为多个，用于进行变换
     */
    public static void addPublicPrefix(String prefixName, String[] prefix){
        //将Prefixes的第PrefixesNumber项设置为传入的前缀名
        Prefixes[PrefixesNumber] = prefixName;
        //将PrefixesValue的值设置为传入的前缀内容
        PrefixesValue[PrefixesNumber] = prefix;
        //使PrefixesNumber加1，用于定义下一个变量应该存储在何处
        PrefixesNumber ++;
    }
    /**
     * 这是玩家聊天前缀设置,用于减去公共前缀（前提是已经被添加过）
     * @param prefixName 聊天前缀名称（String）,这是个唯一的值，用于定义你的聊天前缀
     */
    public static void subPublicPrefix(String prefixName){
        //从变量中减去项
        Prefixes = deleteValue(Prefixes,prefixName);
        //将值去除
        PrefixesValue = deleteValueWithNumber(PrefixesValue,deleteValueNumber);
        //将PrefixesNumber减1，用于定义下一个变量应该存储在何处
        PrefixesNumber --;
    }

    /**
     * 为玩家创建一个私有前缀
     * @param player 需要传入的玩家参数
     * @param prefixValue 前缀的值
     */
    public static void addPrivatePrefix(Player player,String prefixValue){
        //获取玩家名称
        String playerName  = player.getDisplayName();
        //将前缀和玩家名称写入数组
        privatePrefix[0][privatePrefixNumber] = prefixValue;
        privatePrefix[1][privatePrefixNumber] = playerName;
        privatePrefixNumber ++;
    }
    /**
     * 为玩家删除一个私有前缀
     * @param player 需要传入的玩家参数
     * @param prefixValue 前缀的值
     */
    public static void subPrivatePrefix(Player player,String prefixValue){
        //获取玩家名称
        String playerName  = player.getName();
        //将前缀和玩家名称从数组删除
        privatePrefix[0] = deleteValue(privatePrefix[0],prefixValue);
        privatePrefix[1] = deleteValueWithNumberButNoMany(privatePrefix[1],deleteValueNumber);
    }

    /**
     * 获取私有前缀
     * @param player 玩家参数
     * @return [前缀a§f§r][前缀b§f§r],如果玩家没有前缀则返回"notPrefix",如果没有任何前缀值则返回notAnyPrefix
     */
    public static String getPrivatePrefix(Player player) {
        if (privatePrefixNumber > 0) {
            int[] index = getArrayIndexes(privatePrefix[1], player.getName());
            if (index[0] != -1) {
                String[] indexValue = getValuesByIndices(privatePrefix[0], index);
                StringBuilder result = new StringBuilder();
                for (String str : indexValue) {
                    result.append("[").append(str).append("§f§r]");
                }
                return result.toString();
            } else {
                return "notPrefix";
            }
        } else {
            return "notAnyPrefix";
        }
    }
    //减去数组的某一项
    private static String[] deleteValue(String[] arr,String itemToRemove){
        int index = -1; // 初始化索引为-1，表示未找到要减去的项
        // 遍历数组，找到要减去的项的索引
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(itemToRemove)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            String[] newArr = new String[arr.length - 1]; // 创建新的数组，长度减1
            int newIndex = 0; // 新数组的索引
            // 复制除了要减去的项之外的其他项到新数组中
            for (int i = 0; i < arr.length; i++) {
                if (i != index) {
                    newArr[newIndex] = arr[i];
                    newIndex++;
                }
            }
            //将减去的项索引设置成全局变量
            deleteValueNumber = index +1;
            return newArr;
        } else {
            deleteValueNumber = 99999;
            return arr;
        }
    }
    //减去数组中特定的项
    private static String[][] deleteValueWithNumber(String[][] arr,int index){
        for (int i = index; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1]; // 将后面的项依次向前移
        }
        arr = Arrays.copyOf(arr, arr.length - 1); // 删除最后一项
        return arr;
    }
    private static String[] deleteValueWithNumberButNoMany(String[] arr,int index){
        for (int i = index; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1]; // 将后面的项依次向前移
        }
        arr = Arrays.copyOf(arr, arr.length - 1); // 删除最后一项
        return arr;
    }
    //获取多个特定索引的值
    private static String[] getValuesByIndices(String[] array, int[] indices) {
        String[] result = new String[indices.length];
        for (int i = 0; i < indices.length; i++) {
            result[i] = array[indices[i]];
        }
        return result;
    }


    //将数组进行转换
    private static String convertArray(String[][] arr) {
        StringBuilder sb = new StringBuilder();

        for (String[] sublist : arr) {
            for (String item : sublist) {
                sb.append("[").append(item).append("§f§r]");
            }
        }

        String result = sb.toString();
        return result;
    }
    //获取数组内的索引（如果值有多个）
    public static int[] getArrayIndexes(String[] array, String playerName) {
        int[] index = new int[]{-1};
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                continue; // 跳过null元素
            }
            if (array[i].equals(playerName)) {
                index[0] = i;
                break;
            }
        }
        return index;
    }

}
