package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.DatabaseManager;
import com.tauncraft.tauncraftservermanager.Rang;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Verwaltet alle Commands, welche für alle Spieler gedacht sind
 * 
 * @author Terradomninik
 * @version 0.2
 */
public class PlayerCommands implements CommandExecutor {

    private TauncraftServerManager plugin;
    
    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public PlayerCommands (TauncraftServerManager plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Wird beim ausführen eines Commands aufgerufen
     *
     * @param sender Der Sender des Commands
     * @param cmd Das Command 
     * @param label Der Name des Commands 
     * @param args Die Parameter des Commands
     * @return Ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("taunsm.player." + cmd.getName())
                || sender.hasPermission("taunsm.player.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "profile":
                        return profile(playersender, args);
                    case "top":
                        return top(playersender, args);
                }
            }
            plugin.send(sender, "Dieses Command wurde noch nicht implementiert");
        } 
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Zeigt das Profil eines Spielers
     */
    private boolean profile(Player playersender, String[] args) {
        if (args.length == 0) {
            TaunPlayer tp = TaunPlayer.get(playersender);
            playersender.sendMessage(ChatColor.GRAY + "Dein Profil");
            playersender.sendMessage(this.profileMessage(new String[]{tp.getRang().toString(),tp.getTaunpoints()+""}));
        } else {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                OfflinePlayer op = plugin.getServer().getOfflinePlayer(args[0]);
                String sql = "SELECT taunPoints,rangID from spieler WHERE name=? LIMIT 1";
                PreparedStatement ps = DatabaseManager.prepareStatement(sql);
                String msgrang,msgtaunpoints;
                try {
                    ps.setString(1, op.getName());
                    ResultSet rs = ps.executeQuery();
                    if (!rs.last()) {
                        plugin.send(player, op.getName() + " konnte nicht gefunden werden");
                        return true;
                    }
                    msgtaunpoints = rs.getInt(1) + "";
                    Rang rang = Rang.valueOf(rs.getString(2));
                    msgrang = rang.getName();
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error registerPlayer:\n" + ex.getMessage());
                    return false;
                }
                playersender.sendMessage(ChatColor.GRAY + "Profil von " + op.getName());
                playersender.sendMessage(this.profileMessage(new String[]{msgrang,msgtaunpoints}));
                return true;
            }
            TaunPlayer tp = TaunPlayer.get(player);
            playersender.sendMessage(ChatColor.GRAY + "Profil von " + player.getName());
            playersender.sendMessage(this.profileMessage(new String[]{tp.getRang().toString(),tp.getTaunpoints()+""}));
        }
        return true;
    }
    
    /**
     * Formatierte Profile Ausgabe
     */
    private String[] profileMessage(String[] args) {
        return new String[]{
            ChatColor.GRAY + "----------",
            ChatColor.GRAY + "Rang: " + Rang.valueOf(args[0]).getColor() + args[0],
            ChatColor.GRAY + "Taunpoints: " + args[1]
        };
    }

    /**
     * Zeigt die Spieler mit den meisten Taunpoints
     */
    private boolean top(Player playersender, String[] args) {
        int limit = 9;
        if (args.length != 0) {
            try {
                limit = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
            }
            if (limit > 30) limit = 30;
            else if(limit < 1) limit = 1;
        }
        Statement stmnt = DatabaseManager.getStatement();
        try {
            ResultSet rs = stmnt.executeQuery("SELECT name,taunPoints FROM spieler ORDER BY taunPoints DESC LIMIT " + limit + ";");
            if (!rs.isBeforeFirst()) return false;
            playersender.sendMessage(ChatColor.GRAY + "Topliste:");
            while (rs.next()) {
                playersender.sendMessage(ChatColor.GRAY + "" + rs.getRow() + ": " + rs.getString(1) + " - " + rs.getInt(2));
            }
            rs.close();
            stmnt.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
