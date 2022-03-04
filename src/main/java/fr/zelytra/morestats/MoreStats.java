package fr.zelytra.morestats;

import fr.zelytra.morestats.manager.bungee.PluginMessage;
import fr.zelytra.morestats.manager.bungee.Server;
import fr.zelytra.morestats.manager.logs.LogType;
import fr.zelytra.morestats.manager.logs.Logs;
import fr.zelytra.morestats.manager.mysql.MySQL;
import fr.zelytra.morestats.manager.stats.PlayerData;
import fr.zelytra.morestats.manager.stats.ServerData;
import fr.zelytra.morestats.manager.stats.builder.StatsListener;
import fr.zelytra.morestats.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class MoreStats extends JavaPlugin {

    public static MySQL mySQL;
    public static boolean log = true;
    public static String version = "v1.0";
    public static Server server;

    private static MoreStats instance;
    private static Logs logs;

    public static MoreStats getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Message.startUpMessage();

        server = new Server();
        logs = new Logs();
        mySQL = new MySQL();

        regPluginMessage();
        regScrapingTask();

        log("MoreStats successfully start", LogType.INFO);
    }

    /**
     * Method which call all scraping methods
     */
    private void regScrapingTask() {

        //Register scraping listener
        List<StatsListener> statsListeners = new ArrayList<>();
        statsListeners.add(new PlayerData());
        statsListeners.add(new ServerData());

        //Init scraping table
        for (StatsListener listener : statsListeners)
            listener.initTable();

        Bukkit.getScheduler().runTaskTimerAsynchronously(getInstance(), () -> {
            for (StatsListener listener : statsListeners)
                listener.scrap();
        }, 0, 60 * 20);

        log("Scraping task has been started ! (Big brother is watching you)", LogType.INFO);

    }

    @Override
    public void onDisable() {

        if (mySQL.isConnected())
            mySQL.closeConnection();
    }

    public static void log(String msg, LogType type) {
        if (log) {
            MoreStats.getInstance().getServer().getConsoleSender().sendMessage(Message.CONSOLE_PREFIX.getMsg() + type.color + msg);
        }
        logs.log(msg, type);

    }

    private void regPluginMessage() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
    }
}
