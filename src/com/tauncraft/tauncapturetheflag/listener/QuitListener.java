package com.tauncraft.tauncapturetheflag.listener;

import com.tauncraft.tauncapturetheflag.TaunCaptureTheFlag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    public TaunCaptureTheFlag plugin;

    public QuitListener(TaunCaptureTheFlag plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    }
}
