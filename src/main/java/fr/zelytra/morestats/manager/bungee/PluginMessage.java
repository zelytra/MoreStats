/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.morestats.manager.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.zelytra.morestats.MoreStats;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class PluginMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] bytes) {
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }
        final ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        final SubChannel subChannel = SubChannel.get(in.readUTF());

        switch (subChannel) {
            case SERVER_NAME:
                MoreStats.server.setServerName(in.readUTF());
                break;
        }

    }


}
