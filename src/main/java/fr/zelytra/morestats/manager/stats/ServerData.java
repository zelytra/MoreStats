package fr.zelytra.morestats.manager.stats;

import fr.zelytra.morestats.MoreStats;
import fr.zelytra.morestats.manager.logs.LogType;
import fr.zelytra.morestats.manager.mysql.MySQL;
import fr.zelytra.morestats.manager.stats.builder.StatsListener;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerData implements StatsListener {

    private final static String tableName = "ServerData";

    private final static List<String> columnData = new ArrayList<>();

    {
        columnData.add("server"); // Server Name
        columnData.add("playerCount"); // Player counts on the server
    }


    @Override
    public void scrap() {

        if (Bukkit.getOnlinePlayers().size() <= 0) return;

        MySQL mySQL = MoreStats.mySQL;
        Map<String, Object> queryData = new HashMap<>();

        for (String column : columnData) {
            switch (column) {
                case "server" -> queryData.put(column, MoreStats.server.getServerName());
                case "playerCount" -> queryData.put(column, Bukkit.getOnlinePlayers().size());
            }
        }

        mySQL.update(tableName, queryData);
    }

    @Override
    public void initTable() {
        try {

            MySQL mySQL = MoreStats.mySQL;
            ResultSet result = mySQL.query("SHOW TABLES LIKE '" + tableName + "';");

            if (result.next()) return;
            result.close();

            MoreStats.log("Creating ServerStat table...", LogType.INFO);

            //Init table data
            mySQL.update("create table " + tableName +
                    "(" +
                    "time timestamp null," +
                    "server varchar(25) null," +
                    "playerCount int default 0 null" +
                    ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
