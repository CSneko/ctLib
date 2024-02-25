package com.crystalneko.ctlibPublic.sql;


import com.crystalneko.ctlibPublic.File.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static org.cneko.ctlib.common.util.LocalDataBase.Connections.sqlite;

@Deprecated
public class sqlite {

    public sqlite(String path){
    }

    public static void saveData(String tableName, String columnName, String data) {
        sqlite.saveData(tableName, columnName, data);
    }

    public static boolean checkValueExists(String tableName, String columnName, String value) {
        return sqlite.checkValueExists(tableName, columnName, value);
    }
    public static void saveDataWhere(String tableName, String columnName, String whereName, String whereValue, String columnValue) {
        sqlite.saveDataWhere(tableName, columnName, whereName, whereValue, columnValue);
    }
    public static String getColumnValue(String tableName, String columnName, String whereName, String whereValue) {
        return sqlite.getColumnValue(tableName, columnName, whereName, whereValue);
    }
    public static String[] readAllValueInAColumn(String tableName,String columnName){
        return sqlite.readAllValueInAColumn(tableName,columnName);
    }
    public static Boolean deleteLine(String tableName,String whereColumn,String whereValue){
        return sqlite.deleteLine(tableName,whereColumn,whereValue);
    }
    public static boolean isTableExists(String tableName) {
        return sqlite.isTableExists(tableName);
    }

    public static void createTable(String tableName) {
        sqlite.createTable(tableName);
    }

    private static boolean isColumnExists(String tableName, String columnName) {
        return sqlite.isColumnExists(tableName, columnName);
    }

    public static void addColumn(String tableName, String columnName) {
        sqlite.addColumn(tableName, columnName);
    }
    public static boolean checkColumnExists(String tableName, String columnName) {
        return sqlite.checkColumnExists(tableName, columnName);
    }



}
