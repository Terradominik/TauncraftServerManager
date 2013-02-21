package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 
 * @author Dominik
 */
public class BlockListener implements Listener {

    public TauncraftServerManager plugin;

    public BlockListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
    }
}