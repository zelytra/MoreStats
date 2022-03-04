/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.morestats.utils;

import fr.zelytra.morestats.MoreStats;
import org.bukkit.Bukkit;

public enum Message {
    PLAYER_PREFIX("§8[§6MoreStats§8]§r "),
    CONSOLE_PREFIX("§8[§6MoreStats§8]§r ");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static String getHelp(String command) {
        return "§9[§bHisteria§9]§r §cWrong command syntax. Please refer to /" + command + " help.";
    }

    public static void startUpMessage() {
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "     __ ");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "|\\/|(_  ");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "|  |__) ");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "        ");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "                                   ");
        Bukkit.getConsoleSender().sendMessage("§8|   §bby Zelytra");
        Bukkit.getConsoleSender().sendMessage("§8|   " + MoreStats.version);
        Bukkit.getConsoleSender().sendMessage("§8|   ");

    }


}
