package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.DatabaseManager;
import com.tauncraft.tauncraftservermanager.Rang;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
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
        spieler.getFirstPlayed();
        event.setJoinMessage(ChatColor.YELLOW + spieler.getName() + " hat den Server betreten");
        if (!spieler.hasPlayedBefore()) {
            plugin.broadcast(welcomeMessage.replace("<name>", spieler.getName()));
            this.insertNewPlayer(spieler);
        } else {
            if(!this.registerPlayer(spieler)) {
                this.insertNewPlayer(spieler);
            }
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
            System.out.println("insert " + ex.getMessage());
        }
        this.registerPlayer(spieler);
    }

    private boolean registerPlayer(Player spieler) {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("SELECT userID,name,taunPoints,rangID FROM spieler WHERE name=? LIMIT 1;");
        try {
            stmnt.setString(1, spieler.getName());
            ResultSet rs = stmnt.executeQuery();
            if (!rs.last()) return false;
            Rang rang = Rang.valueOf(rs.getString(4));
            new TaunPlayer(plugin,rs.getInt(1),spieler.getName(),rs.getInt(3),rang);
            rs.close();
            stmnt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("register " + ex.getMessage());
            return false;
        }
    }
    
    
}
