package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.SpielerListe;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    public TauncraftServerManager plugin;

    public QuitListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player spieler = event.getPlayer();
        SpielerListe.remove(spieler);
        event.setQuitMessage(ChatColor.DARK_GRAY + spieler.getName() + " hat den Server verlassen");
    }
}
