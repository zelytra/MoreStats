package fr.zelytra.morestats;

import fr.zelytra.morestats.manager.logs.LogType;
import fr.zelytra.morestats.manager.stats.PlayerData;
import fr.zelytra.morestats.manager.stats.ServerData;
import fr.zelytra.morestats.manager.stats.builder.StatsListener;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class MoreStatsAPI {

    private boolean removeDemoScrapper = false;
    private final List<StatsListener> statsListeners = new ArrayList<>();

    public MoreStatsAPI() {
        //Register basic scraping listener
        statsListeners.add(new PlayerData());
        statsListeners.add(new ServerData());

        startListener();
    }

    /**
     * Register custom scraping method
     */
    public void register(StatsListener scrapper) {
        //Init scrapper table
        scrapper.initTable();

        statsListeners.add(scrapper);
        MoreStats.log(scrapper.getName() + " has been register to scraper listener", LogType.INFO);
    }

    private void startListener() {

        Bukkit.getScheduler().runTaskTimerAsynchronously(MoreStats.getInstance(), () -> {
            for (StatsListener listener : statsListeners)
                listener.scrap();
        }, 0, 60 * 20);

        MoreStats.log("Scraping task has been started ! (Big brother is watching you)", LogType.INFO);

    }


}
