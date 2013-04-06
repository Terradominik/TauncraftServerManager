package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.DatabaseManager;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
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
            playersender.sendMessage(ChatColor.GRAY + "Rang: " + tp.getRang().toString());
            playersender.sendMessage(ChatColor.GRAY + "Taunpoints: " + tp.getTaunpoints());
        } else {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                OfflinePlayer op = plugin.getServer().getOfflinePlayer(args[0]);
                plugin.send(playersender, args[0] + " ist nicht online, offline Statistiken werden noch nicht unterstützt");
                return true;
            }
            TaunPlayer tp = TaunPlayer.get(player);
            playersender.sendMessage(ChatColor.GRAY + "Profil von " + player.getName());
            playersender.sendMessage(ChatColor.GRAY + "Rang: " + tp.getRang().getName());
            playersender.sendMessage(ChatColor.GRAY + "Taunpoints: " + tp.getTaunpoints());
        }
        return true;
    }

    /**
     * Zeigt die Spieler mit den meisten Taunpoints
     */
    private boolean top(Player playersender, String[] args) {
        int limit;
        if (args.length == 0) limit = 7;
        else {
            limit = Integer.parseInt(args[0]);
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
