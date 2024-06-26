package org.cneko.ctlib.common.util;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class LocalDataBase {
    public static class Connections{
        public static Sqlite sqlite;
        static {
            try {
                sqlite = new Sqlite("ctlib/playerData.db");
            } catch (SQLException e) {
                 System.out.println(e.getMessage());
            }
        }
    }
    public static interface Sql{
        void executeSQL(String sql);
        void disconnect();
        void saveData(String tableName, String columnName, String data);
        boolean checkValueExists(String tableName, String columnName, String value);
        void saveDataWhere(String tableName, String columnName, String whereName, String whereValue, String columnValue);
        String getColumnValue(String tableName, String columnName, String whereName, String whereValue);
        String[] readAllValueInAColumn(String tableName, String columnName);
        boolean deleteLine(String tableName, String whereColumn, String whereValue);
        boolean isTableExists(String tableName);
        void createTable(String tableName);
        boolean isColumnExists(String tableName, String columnName);
        void addColumn(String tableName, String columnName);
        boolean checkColumnExists(String tableName, String columnName);
        ResultSet executeSQLWithParams(String sql, String[] replaces);
    }
    public static class Sqlite implements Sql{
        public Connection connection;
        public String sqlPath;

        public Sqlite(String path) throws SQLException {
            sqlPath =path;
            this.connection =createConnection();
        }

        // 创建数据库连接
        private Connection createConnection() throws SQLException {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            File sqliteFile = new File(sqlPath);
            if (!sqliteFile.exists()) {
                File parentDir = sqliteFile.getParentFile();
                if (parentDir != null) {
                    parentDir.mkdirs();
                }
                try {
                    sqliteFile.createNewFile();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            String  connectionUrl = "jdbc:sqlite:" + sqlPath;
            connection = DriverManager.getConnection( connectionUrl);
            return connection;
        }

        // 自定义 SQL 语句执行
        public void executeSQL(String sql) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 断开数据库连接
        public void disconnect() {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        /**
         * 保存数据到数据库
         *
         * @param tableName 表名
         * @param columnName 列名
         * @param data      要保存的数据
         */
        public void saveData(String tableName, String columnName, String data) {
            // 检查表是否存在，如果不存在则创建表
            if (!isTableExists(tableName)) {
                createTable(tableName);
            }
            addColumn(tableName, columnName);

            String query = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";

            try (PreparedStatement statement =  connection.prepareStatement(query)) {
                statement.setString(1, data);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        /**
         *检查数据库的某个列中是否有某个值
         *
         * @param tableName 表名
         * @param columnName 列名
         * @param value 要检查的值
         */
        public  boolean checkValueExists(String tableName, String columnName, String value) {
            String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
            try (PreparedStatement statement =  connection.prepareStatement(query)) {

                statement.setString(1, value);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return (count > 0);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
        public  void saveDataWhere(String tableName, String columnName, String whereName, String whereValue, String columnValue) {
            // 检查表是否存在，如果不存在则创建表
            if (!isTableExists(tableName)) {
                createTable(tableName);
            }

            String query = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE " + whereName + " = ?";
            try (PreparedStatement statement =  connection.prepareStatement(query)) {
                statement.setString(1, columnValue);
                statement.setString(2, whereValue);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
        public  String getColumnValue(String tableName, String columnName, String whereName, String whereValue) {
            String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + whereName + "=?";
            try (PreparedStatement statement =  connection.prepareStatement(query)) {
                statement.setString(1, whereValue);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(columnName);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        /**
         * 读取表中某个列的的所有值
         * @param tableName 表名
         * @param columnName 列名
         * @return 得到的数组
         */
        public  String[] readAllValueInAColumn(String tableName,String columnName){
            String query = "SELECT " + columnName + " FROM " + tableName;
            try (PreparedStatement statement =  connection.prepareStatement(query)) {
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
                System.out.println(e.getMessage());
                return new String[]{};
            }
        }
        /**
         * 删除一整行的数据
         *
         * @param tableName   表名
         * @param whereColumn 条件所在列
         * @param whereValue  条件值
         * @return 是否成功删除
         */
        public boolean deleteLine(String tableName, String whereColumn, String whereValue){
            String query = "DELETE FROM " + tableName + " WHERE " + whereColumn + " = ?";
            try (PreparedStatement statement =  connection.prepareStatement(query)) {
                statement.setString(1, whereValue);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        /**
         * 检查表是否存在
         *
         * @param tableName 表名
         * @return 表是否存在
         */
        public  boolean isTableExists(String tableName) {
            try  {
                DatabaseMetaData metaData =  connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, tableName, null);
                return resultSet.next();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        /**
         * 创建表
         *
         * @param tableName 表名
         */
        public  void createTable(String tableName) {
            String query = "CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT)";
            try (PreparedStatement statement =  connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * 检查列是否存在
         *
         * @param tableName  表名
         * @param columnName 列名
         * @return 列是否存在
         */
        public boolean isColumnExists(String tableName, String columnName) {
            try {
                DatabaseMetaData metaData =  connection.getMetaData();
                ResultSet resultSet = metaData.getColumns(null, null, tableName, columnName);
                return resultSet.next();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        /**
         * 添加列
         *
         * @param tableName  表名
         * @param columnName 列名
         */
        public void addColumn(String tableName, String columnName) {
            if (checkColumnExists(tableName, columnName)) {
                return;
            }

            String query = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT";
            try (PreparedStatement statement =  connection.prepareStatement(query)) {

                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        public  boolean checkColumnExists(String tableName, String columnName) {
            try (ResultSet resultSet =  connection.getMetaData().getColumns(null, null, tableName, columnName)) {
                return resultSet.next();  // 如果结果集有下一个元素，则表示列存在
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        /**
         * 执行SQL语句
         *
         * @param sql      SQL语句
         * @param replaces 替换参数
         * @return 结果集
         */
        public ResultSet executeSQLWithParams(String sql, @Nullable String[] replaces) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                if (replaces != null) {
                    for (int i = 0; i < replaces.length; i++) {
                        statement.setString(i + 1, replaces[i]); // PreparedStatement 的索引从 1 开始
                    }
                }

                if (sql.trim().toLowerCase().startsWith("select")) {
                    // 如果是查询操作，则返回 ResultSet 对象
                    return statement.executeQuery();
                } else {
                    // 否则执行更新操作
                    statement.executeUpdate();
                    // 关闭 PreparedStatement，避免资源泄漏
                    statement.close();
                    return null;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        
    }
}
