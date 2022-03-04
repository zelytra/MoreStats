package fr.zelytra.morestats.manager.bungee.listener;

import fr.zelytra.morestats.manager.bungee.PMessage;
import fr.zelytra.morestats.manager.bungee.SubChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerCountUpdater implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        new PMessage(SubChannel.PLAYER_COUNT, null, new String[]{"ALL"});
    }
}
