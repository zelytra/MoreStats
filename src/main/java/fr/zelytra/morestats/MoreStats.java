package fr.zelytra.morestats;

import fr.zelytra.morestats.manager.bungee.PluginMessage;
import fr.zelytra.morestats.manager.bungee.Server;
import fr.zelytra.morestats.manager.logs.LogType;
import fr.zelytra.morestats.manager.logs.Logs;
import fr.zelytra.morestats.manager.mysql.MySQL;
import fr.zelytra.morestats.utils.Message;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoreStats extends JavaPlugin {

    public static MySQL mySQL;
    public static boolean log = true;
    public static String version = "v1.0";
    public static Server server;

    public static MoreStatsAPI statsAPI;

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
        statsAPI = new MoreStatsAPI();

        regPluginMessage();

        log("MoreStats successfully start", LogType.INFO);
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

    public static MoreStatsAPI getStatsAPI() {
        return statsAPI;
    }
}
