/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.morestats.manager.bungee;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.zelytra.morestats.MoreStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class PMessage {
    private BukkitTask task;

    public PMessage(@NotNull SubChannel channel, Player player, String args[]) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel.getName());

        if (args != null)
            for (String arg : args)
                out.writeUTF(arg);
        else {
            out.writeUTF("null");
        }


        if (player == null) {
            task = Bukkit.getScheduler().runTaskTimer(MoreStats.getInstance(), () -> {
                Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                if (p != null) {
                    p.sendPluginMessage(MoreStats.getInstance(), "BungeeCord", out.toByteArray());
                    task.cancel();
                }

            }, 0L, 60L);
        } else {
            player.sendPluginMessage(MoreStats.getInstance(), "BungeeCord", out.toByteArray());
        }

    }


}
