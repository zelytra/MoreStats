/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.morestats.manager.mysql;

import fr.zelytra.morestats.MoreStats;
import fr.zelytra.morestats.manager.logs.LogType;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.Map;

public class MySQL {
    private Connection connection;
    private Statement statement;
    private Object synchroObject = new Object();
    private boolean isConnected = false;

    /**
     * MySQL api
     */

    public MySQL() {
        connect();
    }

    public boolean isConnected() {
        return isConnected;
    }

    private void connect() {
        try {
            SQLConfiguration configuration = new SQLConfiguration();
            this.connection = DriverManager.getConnection("jdbc:mysql://" + configuration.getHost()
                            + ":" + configuration.getPort()
                            + "/" + configuration.getDatabase()
                    , configuration.getUserName()
                    , configuration.getPassword());
            this.statement = this.connection.createStatement();
            this.isConnected = true;
            MoreStats.log("Connected to dataBase !", LogType.INFO);
        } catch (SQLException exception) {
            MoreStats.log("Failed to connect to DB, shutting down server", LogType.ERROR);
            this.isConnected = false;
            Bukkit.shutdown();
        }
    }

    public void update(String sql) {

        try {
            synchronized (synchroObject) {
                checkConnection();
                statement.executeUpdate(sql);
            }
        } catch (Exception e) {
            MoreStats.log("SQL Update error:\nRequest: " + sql, LogType.ERROR);
            e.printStackTrace();
        }

    }

    public void update(String tableName, Map<String, Object> value) {

        StringBuilder action = new StringBuilder("INSERT INTO `" + tableName + "` (`time`,");

        // Concat columne value
        for (var column : value.entrySet())
            action.append("`" + column.getKey() + "`,");

        // Removing exedent of "," and closing column list
        action.deleteCharAt(action.length() - 1).append(")");
        action.append(" VALUE (NOW(),");

        // Concat value data
        for (var data : value.entrySet()) {
            if (data.getValue() instanceof String)
                action.append("'" + data.getValue() + "',");
            else
                action.append(data.getValue() + ",");
        }

        // Removing exedent of "," and closing column list
        action.deleteCharAt(action.length() - 1).append(");");

        update(action.toString());

    }

    private void checkConnection() {
        try {

            if (connection != null && !connection.isValid(2)) {
                MoreStats.log("Lost connection to DB, try to reconnecting", LogType.WARN);
                this.isConnected = false;
                connect();
            }

        } catch (SQLException ignored) {
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    public ResultSet query(String sql) {

        try {
            synchronized (synchroObject) {
                checkConnection();
                return statement.executeQuery(sql);
            }
        } catch (Exception e) {
            MoreStats.log("SQL Querry error:\nRequest: " + sql, LogType.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
