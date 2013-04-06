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

/**
 * Listener für das Betreten eines Spielers auf den Server
 * 
 * @author Terradominik | raffi287
 * @version 0.2
 */
public class JoinListener implements Listener {

    public static TauncraftServerManager plugin;
    public static String motd;
    public static String welcomeMessage;

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public JoinListener(TauncraftServerManager plugin) {
        JoinListener.plugin = plugin;
        motd = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd"));
        welcomeMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("welcomemsg"));
    }

    /**
     * Wird ausgeführt, sobald ein Spieler den Server betritt
     * @param event Das Event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player spieler = event.getPlayer();
        spieler.getFirstPlayed();
        event.setJoinMessage(ChatColor.YELLOW + spieler.getName() + " hat den Server betreten");
        if (!spieler.hasPlayedBefore()) {
            plugin.getServer().broadcastMessage(welcomeMessage.replace("<name>", spieler.getName()));
            this.insertNewPlayer(spieler);
        } else {
            if(!this.registerPlayer(spieler)) {
                this.insertNewPlayer(spieler);
            }
        }
        spieler.sendMessage(motd);
    }

    /**
     * Fügt einen neuen Spieler in die Datenbank ein
     * @param spieler Der Spieler, welcher in die Datenbank eingefügt werden soll
     */
    private void insertNewPlayer(Player spieler) {
        Rang targetrang = Rang.NEU;
        if (spieler.hasPermission("taunsm.rang.admin")) targetrang = Rang.ADMIN;
        else if (spieler.hasPermission("taunsm.rang.mod")) targetrang = Rang.MOD;
        else if (spieler.hasPermission("taunsm.rang.mod")) targetrang = Rang.SPIELER;
        else if (spieler.hasPermission("taunsm.rang.mod")) targetrang = Rang.BAU_TEAM;
        else if (spieler.hasPermission("taunsm.rang.mod")) targetrang = Rang.TEST_BAU_TEAM;
        else if (spieler.hasPermission("taunsm.rang.mod")) targetrang = Rang.SPIELER;
        
        PreparedStatement stmnt = DatabaseManager.prepareStatement("INSERT INTO spieler (name,rangID,firstJoin,joinIP) VALUES (?,?,?,?);");
        try {
            stmnt.setString(1, spieler.getName());
            stmnt.setString(2, targetrang.toString());
            stmnt.setTimestamp(3, new Timestamp(Calendar.getInstance().getTime().getTime()));
            stmnt.setString(4, spieler.getAddress().getAddress().getHostAddress());
            stmnt.executeUpdate();
            stmnt.close();
        } catch (SQLException ex) {
            System.out.println("Error insertNewPlayer:\n" + ex.getMessage());
        }
        this.registerPlayer(spieler);
    }

    /**
     * Registriert einen Spieler als TaunPlayer und ladet die Werte der Config
     * @param spieler Der Spieler, welcher als TaunPlayer registriert werden soll
     * @return Ob die Registrierung erfolgreich war
     */
    private boolean registerPlayer(Player spieler) {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("SELECT userID,name,taunPoints,rangID FROM spieler WHERE name=? LIMIT 1;");
        try {
            stmnt.setString(1, spieler.getName());
            ResultSet rs = stmnt.executeQuery();
            if (!rs.last()) return false;
            Rang rang = Rang.valueOf(rs.getString(4));
            new TaunPlayer(rs.getInt(1),spieler.getName(),rs.getInt(3),rang);
            rs.close();
            stmnt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error registerPlayer:\n" + ex.getMessage());
            return false;
        }
    }
}