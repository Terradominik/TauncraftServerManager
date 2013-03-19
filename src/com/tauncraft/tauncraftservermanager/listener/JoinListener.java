package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.DatabaseManager;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
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
        event.setJoinMessage(ChatColor.DARK_GRAY + spieler.getName() + " hat den Server betreten");
        if (!spieler.hasPlayedBefore()) {
            plugin.broadcast(welcomeMessage.replace("<name>", spieler.getName()));
            this.insertNewPlayer(spieler);
        }
        plugin.send(spieler, motd);
    }
    
    public static void updateNachrichten() {
        motd = plugin.getConfig().getString("motd");
        welcomeMessage = plugin.getConfig().getString("welcomemsg");
    }

    private void insertNewPlayer(Player spieler) {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("INSERT INTO spieler (name,firstJoin,joinIP) VALUES (?,?,?);");
        try {
            stmnt.setString(1, spieler.getName());
            stmnt.setTimestamp(2, new Timestamp(Calendar.getInstance().getTime().getTime()));
            stmnt.setString(3, spieler.getAddress().getAddress().getHostAddress());
            stmnt.executeUpdate();
            stmnt.close();
        } catch (SQLException ex) {
            Logger.getLogger(JoinListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
