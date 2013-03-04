package com.tauncraft.tauncraftservermanager;

import java.util.Set;
import org.bukkit.plugin.Plugin;

/**
 * Plugin Chat Nachricht
 * @author Dominik
 */
public class PluginChat {
    Set<String> spielerSet;
    Plugin plugin;
    String format;
    
    /**
     * @param plugin        Die Instanz, des jeweiligen Plugins zum aufrufen der Plugin Methoden
     * @param chatName      Der Anzeige Name des Chats, sollte aus 3 Buchstaben bestehen
     * @param format        Die Formatierung des Textes [0] ist vor dem Chatnamen, [1]Danach
     * @param spieler       Das Set der Spieler an die Gesendet werden soll
     */
    public PluginChat(Plugin plugin, String chatName, String[] format, Set<String> spielerSet) {
        this.plugin = plugin;
        this.spielerSet = spielerSet;
        this.format = format[0] + chatName + format[1] + "%1$s";
    }
    
    public void sendMessage(String text) {
        text = String.format(format, text);
        for (String spieler : spielerSet) plugin.getServer().getPlayer(spieler).sendMessage(text);
    }
    
}
