package com.tauncraft.tauncapturetheflag.listener;

import com.tauncraft.tauncapturetheflag.TaunCaptureTheFlag;
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

    public TaunCaptureTheFlag plugin;

    public BlockListener(TaunCaptureTheFlag plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
    }
}