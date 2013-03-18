package com.tauncraft.tauncraftservermanager;

import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

/**
 * PlayerChat Klasse
 *
 * @author Terradominik | raffi287
 * @version 2012-02-22
 */
public class PlayerChat {

    private Plugin plugin;
    private String chatName;
    private String format;
    private Set<String> spielerSet;
    
    /**
     * @param plugin        Die Instanz, des jeweiligen Plugins zum aufrufen der Plugin Methoden
     * @param chatName      Der Anzeige Name des Chats, sollte aus 3 Buchstaben bestehen
     * @param format        Die Formatierung des Textes [0] ist vor dem Chatnamen, [1] nach dem Chat Namen, [2] nach dem Spieler Namen
     * @param spieler       Das Set der Spieler an die Gesendet werden soll
     */
    public PlayerChat(Plugin plugin, String chatName, String[] format, Set<String> spielerSet) {
        this.plugin = plugin;
        this.spielerSet = spielerSet;
        this.format = format[0] + chatName + format[1] + "%3$s" + "%1$s" + format[2] + "%2$s";
        plugin.getServer().getPluginManager().registerEvents(new PlayerChatListener(), plugin);
    }
    
    private class PlayerChatListener implements Listener {
        
        @EventHandler(priority = EventPriority.HIGHEST) 
        public void onPlayerAsynchChatEvent(AsyncPlayerChatEvent event) {
            Player spieler = event.getPlayer();
            if (spielerSet.contains(spieler.getName())) {
                Set<Player> recipients = event.getRecipients();
                recipients.clear();
                for (String target : spielerSet) recipients.add(plugin.getServer().getPlayer(target));
                event.setFormat(String.format(format, "%1$s", "%2%s", RangManager.getRangColor(spieler)));
            }
        }
    }
}
