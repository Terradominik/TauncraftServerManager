package com.tauncraft.tauncraftservermanager;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Verwaltet die Automatischen Chat Nachrichten
 * 
 * @author Terradominik
 * @version 0.2
 */
public class AutoMessageManager {
    private TauncraftServerManager plugin;
    private List<String> messages;
    private long period;
    
    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager
     */
    public AutoMessageManager(TauncraftServerManager plugin) {
        this.plugin = plugin;
        
        messages = plugin.getConfig().getStringList("automessages");
        Collections.shuffle(messages);
        try {
            period = Long.parseLong(plugin.getConfig().getString("automessages-interval"));
        } catch (NumberFormatException nfe) {
            period = 10;
        }
        period *= 20*60;
        this.broadcastMessages();
    }
    
    private void broadcastMessages() {
        new BukkitRunnable() {
            int rand = new Random().nextInt(messages.size());
            @Override
            public void run() {
                plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', messages.get(rand)));
                rand++;
                if (rand == messages.size()) rand = 0;
            }
        }.runTaskTimer(plugin, 0L, period);
    }
}
