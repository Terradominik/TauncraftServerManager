package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listener für das Platzieren und Zerstören von Blöcken durch einen Spieler
 * 
 * @author Terradominik
 * @version 0.2
 */
public class BlockListener implements Listener {

    private TauncraftServerManager plugin;
    
    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public BlockListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Wird ausgeführt, sobald ein Block durch einen Spieler zerstört wird
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(cancelCheck(event.getPlayer()));
    }

    /**
     * Wird ausgeführt, sobald ein Block durch einen Spieler platziert wird
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(cancelCheck(event.getPlayer()));
    }

    /**
     * Überprüft ob der Spieler die Nötigen Permissions hat
     * @param spieler Der Spieler dessen Rechte überprüft werden sollen
     */
    public boolean cancelCheck(Player spieler) {
        if (!(spieler.hasPermission("taunsm.world." + spieler.getWorld().getName().toLowerCase())
                || spieler.hasPermission("taunsm.world.*")
                || spieler.hasPermission("taunsm.*") || spieler.isOp())) {
            return true;
        }
        return false;
    }
}