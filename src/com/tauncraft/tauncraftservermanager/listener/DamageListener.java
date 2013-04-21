package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Listener für das Bewegen eines Spielers
 * 
 * @author Terradominik
 * @version 0.2
 */
public class DamageListener implements Listener {

    public TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public DamageListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Wird ausgeführt, sobald sich ein Spieler bewegt
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL &&
                event.getEntity() instanceof Player &&
                event.getEntity().getWorld() == plugin.getServer().getWorld("Spawn-Welt")){
            event.setCancelled(true);
        }
    }
}