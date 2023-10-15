package com.crystalneko.ctlibfabric.sql;

import com.crystalneko.ctlib.CtLib;

import java.sql.*;
import java.util.ArrayList;

public class mysql {
    /**
     * mysql写入方法
     * 变量mysqlconnection为mysql连接
     */
    private static CtLib plugin;
    public static Connection mysqlconnection;
    public mysql(CtLib plugin){
        this.plugin = plugin;
        try {
            mysqlconnection = createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("成功连接到数据库");
        // 创建并启动异步任务
        Thread reconnectionThread = new Thread(mysql::reconnect);
        reconnectionThread.start();
    }
    public static void reconnect() {
        while (true) {
            try {
                mysqlconnection = createConnection();
                System.out.println("成功连接到数据库");
                // 暂停1小时
                Thread.sleep(3600000);
            } catch (SQLException | InterruptedException e) {
                System.out.println("无法连接到数据库");
                e.printStackTrace();
                // 等待一段时间后重试
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //mysql连接方法
    public static Connection createConnection() throws SQLException {
        // 解析MySQL配置
        String mysqlHost = plugin.getConfig().getString("mysql.host");
        int mysqlPort = plugin.getConfig().getInt("mysql.port");
        String mysqlDatabase = plugin.getConfig().getString("mysql.database");
        String mysqlUsername = plugin.getConfig().getString("mysql.username");
        String mysqlPassword = plugin.getConfig().getString("mysql.password");
        String mysqltime = plugin.getConfig().getString("mysql.time");
        String mysqlchar = plugin.getConfig().getString("mysql.char");
        Boolean mysqlusessl = plugin.getConfig().getBoolean("mysql.useSSL");
        try {
            Class.forName(plugin.getConfig().getString("mysql.drive"));
            System.out.println("成功加载mysql驱动");
        } catch (ClassNotFoundException e) {
            System.out.println("加载mysql驱动失败");
            e.printStackTrace();
        }

        // 构建MySQL连接字符串
        String mysqlConnectionUrl = "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase+ "?user="+mysqlUsername+ "&password="+mysqlPassword+"password&characterEncoding="+ mysqlchar +"&useSSL="+ mysqlusessl +"&serverTimezone="+mysqltime;
        return DriverManager.getConnection(mysqlConnectionUrl, mysqlUsername, mysqlPassword);
    }
    /**
     * 保存数据到数据库
     *
     * @param tableName 表名
     * @param columnName 列名
     * @param data      要保存的数据
     */
    public static void saveData(String tableName, String columnName, String data) {
        // 检查表是否存在，如果不存在则创建表
        if (!isTableExists(tableName)) {
            createTable(tableName);
        }
        addColumn(tableName, columnName);

        String query = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";

        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {
            statement.setString(1, data);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /**
     *检查数据库的某个列中是否有某个值
     *
     * @param tableName 表名
     * @param columnName 列名
     * @param value 要检查的值
     */
    public static boolean checkValueExists(String tableName, String columnName, String value) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {

            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return (count > 0);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }
    /**
     *在tableName表中，需要更改columnName,当满足whereName = data时，则执行update
     * @param tableName 表名
     * @param columnName 要更改的列（如: balance）
     * @param whereName 要更改的条件列 (如：username)
     * @param whereValue 要更改的条件值 (如：CrystalNeko)
     * @param columnValue 更改后的值(如：123)
     */
    public static void saveDataWhere(String tableName, String columnName, String whereName, String whereValue, String columnValue) {
        // 检查表是否存在，如果不存在则创建表
        if (!isTableExists(tableName)) {
            createTable(tableName);
        }

        String query = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE " + whereName + " = ?";
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {
            statement.setString(1, columnValue);
            statement.setString(2, whereValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /**
     * 读取某个特定的值
     * @param tableName 表名
     * @param columnName 列名
     * @param whereName 条件列名
     * @param whereValue 条件值
     * @return 查询结果
     */
    public static String getColumnValue(String tableName, String columnName, String whereName, String whereValue) {
        String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + whereName + "=?";
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {
            statement.setString(1, whereValue);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(columnName);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * 读取表中某个列的的所有值
     * @param tableName 表名
     * @param columnName 列名
     * @return 得到的数组
     */
    public static String[] readAllValueInAColumn(String tableName,String columnName){
        String query = "SELECT " + columnName + " FROM " + tableName;
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            ArrayList<String> names = new ArrayList<>();

            while (result.next()) {
                String name = result.getString(columnName);
                names.add(name);
            }

            // 将ArrayList转换为数组
            String[] namesArray = new String[names.size()];
            names.toArray(namesArray);
            return namesArray;
        } catch (SQLException e) {
            System.out.println(e);
            return new String[]{};
        }
    }
    /**
     * 删除一整行的数据
     * @param tableName 表名
     * @param whereColumn 条件所在列
     * @param whereValue 条件值
     * @return 是否成功删除
     */
    public static Boolean deleteLine(String tableName,String whereColumn,String whereValue){
        String query = "DELETE FROM " + tableName + " WHERE " + whereColumn + " = ?";
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {
            statement.setString(1, whereValue);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }






    /**
     * 检查表是否存在
     *
     * @param tableName 表名
     * @return 表是否存在
     */
    public static boolean isTableExists(String tableName) {
        try  {
            DatabaseMetaData metaData = mysqlconnection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * 创建表
     *
     * @param tableName 表名
     */
    public static void createTable(String tableName) {
        String query = "CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT)";
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * 检查列是否存在
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 列是否存在
     */
    private static boolean isColumnExists(String tableName, String columnName) {
        try {
            DatabaseMetaData metaData = mysqlconnection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, columnName);
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * 添加列
     *
     * @param tableName  表名
     * @param columnName 列名
     */
    public static void addColumn(String tableName, String columnName) {
        if (checkColumnExists(tableName, columnName)) {
            return;
        }

        String query = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT";
        try (PreparedStatement statement = mysqlconnection.prepareStatement(query)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public static boolean checkColumnExists(String tableName, String columnName) {
        try (ResultSet resultSet = mysqlconnection.getMetaData().getColumns(null, null, tableName, columnName)) {
            return resultSet.next();  // 如果结果集有下一个元素，则表示列存在
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
