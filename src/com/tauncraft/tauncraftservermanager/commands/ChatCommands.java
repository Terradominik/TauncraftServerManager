/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ChatCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 2012-02-22
 */
public class ChatCommands implements CommandExecutor {

    private TauncraftServerManager plugin;
    private String chatFormat = ChatColor.DARK_PURPLE + "";

    public ChatCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Beim eingeben eines Command
     *
     * @param sender sender des Commands
     * @param command Command 
     * @param label Name des Commands 
     * @param args Parameter des Commands
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("taunsm.chat." + label) || sender.hasPermission("taunsm.chat.*") || sender.hasPermission("taunsm.*") || sender.isOp()) {
            if (sender instanceof Player) {
                switch (label) {
                    case "mod":
                        leitungsMessage(args);
                        break;
                    case "leitung":
                        leitungsMessage(args);
                        break;
                    case "server":
                        serverMessage(args);
                    case "clearmsg":
                        clearMessage(args);
                    case "tell":
                        tell(sender,args);
                    case "whisper":
                        tell(sender,args);
                    case "tt":
                        tell(sender,args);
                    case "t":
                        tell(sender,args);
                    default:
                    //Ausgabe: "Das Command wurde noch nicht implementiert"
                }
            }
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return true;
    }
    
    private void leitungsMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s);
        plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "Leitung: " + sb);
    }
    
    private void serverMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s);
        plugin.getServer().broadcastMessage(ChatColor.DARK_RED + "Server: " + sb);
    }
    
    private void clearMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s);
        String msg = ChatColor.translateAlternateColorCodes('&', sb.toString());
        plugin.getServer().broadcastMessage(msg);
    }

    private void tell(CommandSender sender, String[] args) {
        Player receiver = plugin.getServer().getPlayer(args[0]);
        if (receiver == null) plugin.send(sender, "Der Spieler " + args[0] + " konnte nicht gefunden werden");
        else {
            args[0] = "";
            StringBuilder sb = new StringBuilder();
            for (String s : args) sb.append(s);
            plugin.send(sender, ChatColor.DARK_PURPLE + "Tell zu " + receiver.getName() + ": " + sb.toString());
            plugin.send(receiver, ChatColor.DARK_PURPLE + "Tell von " + sender.getName() + ": " + sb.toString());
        }
    }
}
