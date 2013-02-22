package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author Terradominik
 * @version 2012-02-22
 */
public class BlockListener implements Listener {

    public TauncraftServerManager plugin;

    public BlockListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Ünberprüft ob der Spieler Blöcke zerstören darf
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(cancelCheck(event.getPlayer()));
    }

    /**
     * Überprüft ob der Spieler Blöcke platzieren darf
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(cancelCheck(event.getPlayer()));
    }

    /**
     * Überprüft ob der Spieler die nötigen Permissions hat
     *
     * @param spieler
     * @return
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