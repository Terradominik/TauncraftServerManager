package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.Chat;
import com.tauncraft.tauncraftservermanager.SpielerListe;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener für das Verlassen eines Spielers vom Server
 * 
 * @author Terradominik
 * @version 0.2
 */
public class QuitListener implements Listener {

    public TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public QuitListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Wird ausgeführt, sobald ein Spieler den Server verlässt
     * @param event Das Event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player spieler = event.getPlayer();
        SpielerListe.remove(spieler);
        TaunPlayer tp = TaunPlayer.get(spieler);
        for (Chat c : Chat.getChats()) {
            c.removePlayer(spieler);
        }
        tp.save();
        event.setQuitMessage(ChatColor.YELLOW + spieler.getName() + " hat den Server verlassen");
    }
}
