package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.Chat;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Listener für das Schreiben im Chat durch einen Spieler
 * 
 * @author Terradominik
 * @version 0.2
 */
public class ChatListener implements Listener {
    
    private TauncraftServerManager plugin;
    
    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public ChatListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Wird ausgeführt, sobald eine Chat Nachricht von einem Spieler geschrieben wird
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAsynchChatEvent(AsyncPlayerChatEvent event) {
        TaunPlayer tp = TaunPlayer.get(event.getPlayer());
        Chat targetChat = tp.getWriteChat();
        Set<Player> recipients = event.getRecipients();
        recipients.clear();
        recipients.addAll(targetChat.getListener());
        event.setFormat(targetChat.getPrefix() + tp.getRang().getColor() + "%1$s" + targetChat.getSuffix() + "%2$s");
    }
}