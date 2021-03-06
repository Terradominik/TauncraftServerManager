package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.Chat;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Verwaltet alle Commands, welche mit dem Chat-System zu tun haben
 * 
 * @author Terradomninik
 * @version 0.2
 */
public class ChatCommands implements CommandExecutor {

    private TauncraftServerManager plugin;
    private String chatFormat = ChatColor.DARK_PURPLE + "";

    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public ChatCommands(TauncraftServerManager plugin) {
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
        if (sender.hasPermission("taunsm.chat." + cmd.getName())
                || sender.hasPermission("taunsm.chat.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            switch (cmd.getName()) {
                case "leitung":
                    return leitungsMessage(args);
                case "server":
                    return serverMessage(args);
                case "clearmsg":
                    return clearMessage(args);
                case "tell":
                    return tell(sender, args);
                case "chat":
                    return sender instanceof Player ? chat((Player) sender,args) : false;
            }
            plugin.send(sender, "Das Command wurde noch nicht implementiert");
        }
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Sendet eine Leitungsnachricht
     */
    private boolean leitungsMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");
        plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "Leitung: " + sb);
        return true;
    }
    
    /**
     * Sendet eine Servernachricht
     */
    private boolean serverMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");
        plugin.getServer().broadcastMessage(ChatColor.DARK_RED + "Server: " + sb);
        return true;
    }
    
    /**
     * Sendet eine reine Nachricht ohne Formatierung (gut zum Testen)
     */
    private boolean clearMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");
        String msg = ChatColor.translateAlternateColorCodes('&', sb.toString());
        plugin.getServer().broadcastMessage(msg);
        return true;
    }

    /**
     * Sendet eine private Nachricht
     */
    private boolean tell(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        Player receiver = plugin.getServer().getPlayer(args[0]);
        if (receiver == null) plugin.send(sender, "Der Spieler " + args[0] + " konnte nicht gefunden werden");
        else {
            args[0] = "";
            StringBuilder sb = new StringBuilder();
            for (String s : args) sb.append(s).append(" ");
            plugin.send(sender, ChatColor.DARK_PURPLE + "Tell zu " + receiver.getName() + ":" + sb.toString());
            plugin.send(receiver, ChatColor.DARK_PURPLE + "Tell von " + sender.getName() + ":" + sb.toString());
        }
        return true;
    }

    /**
     * Ändert den write Chat
     */
    private boolean chat(Player player, String[] args) {
        if (args.length == 0) return false;
        for (Chat c : Chat.getChats()) {
            if (c.getName().equalsIgnoreCase(args[0])) {
              TaunPlayer tp = TaunPlayer.get(player);
              tp.addChat(c);
              if(tp.setWriteChat(c)) plugin.send(player, "Der Chat wurde zu " + c.getName() + " geändert");
              else plugin.send(player, "Du hast nicht die nötigen Rechte " + c.getName() + " zu betreten");
              return true;
            }
        }
        plugin.send(player, "Es wurde kein Chat mit dem Namen " + args[0] + " gefunden");
        return true;
    }
}
