
package com.tauncraft.tauncraftservermanager;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

/**
 * Noch nicht Fertig! (Ränge werden mit Enums gelöst)
 * @author Dominik
 */

public class RangManager {
    private static Map<String, Rang> rangMap;
    private Plugin plugin;

    public RangManager(Plugin plugin) {
        this.plugin = plugin;
        //plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
    }
    
    public static Map<String, Rang> getRangMap() {
        return rangMap;
    }
    
    public static Rang getRangColor(Player spieler) {
        //return rangMap.get(spieler.getName());
        return Rang.ADMIN;
    }
    /**
    private class PlayerJoinListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            Player spieler = event.getPlayer();
            if (spieler.hasPermission("rang.admin")) rangMap.put(spieler.getName(), Rang.ADMIN);
            else if (spieler.hasPermission("rang.mod")) rangMap.put(spieler.getName(), Rang.MOD);
            else if (spieler.hasPermission("rang.special")) rangMap.put(spieler.getName(), Rang.SPECIAL);
            else if (spieler.hasPermission("rang.abau")) rangMap.put(spieler.getName(), Rang.ALPHA_BAU_TEAM);
            else if (spieler.hasPermission("rang.bau")) rangMap.put(spieler.getName(), Rang.BAU_TEAM);
            else if (spieler.hasPermission("rang.spieler")) rangMap.put(spieler.getName(), Rang.SPIELER);
            else if (spieler.hasPermission("rang.neu")) rangMap.put(spieler.getName(), Rang.NEU);
        }
    }
    */
    
    
}
