package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    public static TauncraftServerManager plugin;
    public static String motd;
    public static String welcomeMessage;

    public JoinListener(TauncraftServerManager plugin) {
        JoinListener.plugin = plugin;
        updateNachrichten();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player spieler = event.getPlayer();
        if (spieler.hasPlayedBefore()) {
            plugin.broadcast(welcomeMessage.replace("<name>", spieler.getName()));
        }
        plugin.send(spieler, motd);
    }
    
    public static void updateNachrichten() {
        motd = plugin.getConfig().getString("motd");
        welcomeMessage = plugin.getConfig().getString("welcomemsg");
    }
}
