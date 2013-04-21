package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Listener für das Bewegen eines Spielers
 * 
 * @author Terradominik
 * @version 0.2
 */
public class MoveListener implements Listener {

    public TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public MoveListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Wird ausgeführt, sobald sich ein Spieler bewegt
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        Block b = event.getPlayer().getLocation().add(0, -1, 0).getBlock();
        if (b.getType() == Material.ENDER_STONE && b.getData() == 15) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,5*20,5));
        }
    }
}