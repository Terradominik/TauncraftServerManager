package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.Chat;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Dominik
 */
public class ChatListener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAsynchChatEvent(AsyncPlayerChatEvent event) {
        Set<Player> recipients = event.getRecipients();
        Chat targetChat = TaunPlayer.get(event.getPlayer()).writeChat;
        for (TaunPlayer target : TaunPlayer.getMap().values()) {
            if (target.listeningChats.contains(targetChat)) {
               recipients.add(target.getPlayer());
            }
        }
    }
}
