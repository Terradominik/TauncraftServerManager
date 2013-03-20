package com.tauncraft.tauncraftservermanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author
 * Dominik
 */
public class TestPlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAsynchChatEvent(AsyncPlayerChatEvent event) {
        Player spieler = event.getPlayer();
        spieler.setDisplayName(ChatColor.DARK_GREEN + spieler.getName());
        event.setFormat(ChatColor.BLUE + "[TestChat] %1$s: %2$s");
    }
}
