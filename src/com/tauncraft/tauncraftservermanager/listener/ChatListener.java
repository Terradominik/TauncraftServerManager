package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.Chat;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Dominik
 */
public class ChatListener implements Listener {
    
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