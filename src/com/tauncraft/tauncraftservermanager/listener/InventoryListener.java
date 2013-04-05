package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

/**
 * @author Terradominik
 * @version 2012-04-05
 */
public class InventoryListener implements Listener {

    public TauncraftServerManager plugin;

    public InventoryListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Macht unlimitierte Dropper
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