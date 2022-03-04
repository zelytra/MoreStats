package fr.zelytra.morestats.manager.stats;

import fr.zelytra.morestats.MoreStats;
import fr.zelytra.morestats.manager.logs.LogType;
import fr.zelytra.morestats.manager.mysql.MySQL;
import fr.zelytra.morestats.manager.stats.builder.StatsListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PlayerData implements StatsListener {

    private final static String tableName = "PlayerData";
    private final static Map<String, Statistic> columnData = new HashMap<>();

    private boolean firstScrap = false;

    {
        columnData.put("name", null);
        columnData.put("timePlayed", Statistic.PLAY_ONE_MINUTE); //Tick played
        columnData.put("playerKill", Statistic.PLAYER_KILLS);
        columnData.put("death", Statistic.DEATHS);
        //columnData.put("totalBlockMined", Statistic.MINE_BLOCK);
    }

    public PlayerData() {
    }


    @Override
    public void scrap() {

        MySQL mySQL = MoreStats.mySQL;
        Map<String, Object> queryData = new HashMap<>();

        for (Player player : Bukkit.getOnlinePlayers())
            extractPlayerData(mySQL, queryData, player);

        if (!firstScrap) {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
                extractPlayerData(mySQL, queryData, offlinePlayer);
            firstScrap = true;
        }


    }

    private void extractPlayerData(MySQL mySQL, Map<String, Object> queryData, OfflinePlayer player) {
        for (var dataMap : columnData.entrySet()) {

            // Special case
            switch (dataMap.getKey()) {
                case "name":
                    queryData.put(dataMap.getKey(), player.getName());
                    break;
                case "timePlayed":
                    queryData.put(dataMap.getKey(), getHourFromTick(player.getStatistic(dataMap.getValue())));
                    break;
                default:
                    queryData.put(dataMap.getKey(), player.getStatistic(dataMap.getValue()));
                    break;

            }
        }
        mySQL.update(tableName, queryData);
    }

    private double getHourFromTick(long tick) {
        return (tick / 20) / 3600.0;
    }

    @Override
    public void initTable() {
        try {

            MySQL mySQL = MoreStats.mySQL;
            ResultSet result = mySQL.query("SHOW TABLES LIKE '" + tableName + "';");

            if (result.next()) return;
            result.close();

            MoreStats.log("Creating PlayerData table...", LogType.INFO);

            //Init table data
            mySQL.update("create table " + tableName +
                    "(" +
                    "time timestamp null," +
                    "name varchar(25) null," +
                    "timePlayed double null," +
                    "playerKill int default 0 null," +
                    "death int default 0 null," +
                    "totalBlockMined int default 0 null" +
                    ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
