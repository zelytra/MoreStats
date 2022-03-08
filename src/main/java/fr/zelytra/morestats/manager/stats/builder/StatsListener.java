package fr.zelytra.morestats.manager.stats.builder;

public interface StatsListener {

    /**
     * Scraper task execution. Call everytime the scrapper handler need to retrieve and send data's
     * For grafana exploitation, it need at least one column with timestamp data
     *
     * This method is executed in async and need to be optimized to not affect server performances
     */
    void scrap();

    /**
     * Method call when registering the scrapper to init SQL table
     * You need to handle the creation of the table and also if the table already exist do nothing
     * <p>
     * See README for mor detail of basic scrapper sample
     */
    void initTable();

    /**
     * @return the name of the scrapper, for logging utilities
     */
    String getName();

}
