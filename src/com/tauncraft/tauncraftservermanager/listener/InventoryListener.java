package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * Listener für das Interagieren mit einem Inventar duch einen Spieler
 * 
 * @author Terradominik
 * @version 0.2
 */
public class InventoryListener implements Listener {

    public TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public InventoryListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Wird ausgeführt, sobald ein Inventar durch einen Spieler geöffnet wird
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryType it = event.getView().getType();
        if ((it == InventoryType.DISPENSER || it == InventoryType.DROPPER) 
                && !DispenseListener.forbiddenWorlds.contains(event.getPlayer().getWorld())
                && !event.getPlayer().hasPermission("taunsm.*")
                && !event.getPlayer().hasPermission("taunsm.disp")){
            event.getView().close();
        }
    }
}