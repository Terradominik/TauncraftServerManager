package com.tauncraft.tauncraftservermanager;

import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author
 * Dominik
 */
public class PlayerChat {

    private Plugin plugin;
    private String chatName;
    private String format;
    private Set<String> spielerListe;
    
    public PlayerChat(Plugin plugin, String chatName, String[] formatierung, Set<String> spielerListe) {
        this.plugin = plugin;
        this.spielerListe = spielerListe;
        
        format = "<f0><chatName><f1><rangfarbe><spieler><f2><nachricht>";
        format.replace("<f0>", formatierung[0]);
        format.replace("<f1>", formatierung[1]);
        format.replace("<f2>", formatierung[2]);
        format.replace("<chatName>", chatName);
        format.replace("<rangfarbe>", "%3$s");
        format.replace("<message>", "%1$s");
        format.replace("<nachricht>", "%2$s");
        
        plugin.getServer().getPluginManager().registerEvents(new PlayerChatListener(), plugin);
    }
    
    private class PlayerChatListener implements Listener {
        
        @EventHandler
        public void onPlayerAsynchChatEvent(AsyncPlayerChatEvent event) {
            Player spieler = event.getPlayer();
            if (spielerListe.contains(spieler.getName())) {
                Set<Player> recipients = event.getRecipients();
                recipients.clear();
                for (String target : spielerListe) {
                    recipients.add(plugin.getServer().getPlayer(target));
                }
                event.setFormat(String.format(format, "%1$s", "%2%s", RangManager.getRangColor(spieler)));
            }
        }
    }
}
