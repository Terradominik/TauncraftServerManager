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
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Listener für das "Dispensen" eines Dispenser oder Droppers
 * 
 * @author Terradominik
 * @version 0.2
 */
public class DispenseListener implements Listener {

    public TauncraftServerManager plugin;
    public static Set<World> forbiddenWorlds = new HashSet<>();

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public DispenseListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
        forbiddenWorlds.add(plugin.getServer().getWorld("HC-Welt"));
    }

    /**
     * Wird ausgeführt, sobald eine Item "dispensed" wird
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockDispense(BlockDispenseEvent event) {
        Block b = event.getBlock();
        if ((b.getType() == Material.DISPENSER || b.getType() == Material.DROPPER) 
                && !forbiddenWorlds.contains(b.getWorld())) {
            Dispenser disp = (Dispenser) b.getState();
            InventoryHolder ih = (InventoryHolder) b.getState();
            ih.getInventory().addItem(event.getItem());
        }
    }
}