package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.Restart;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * AdministrationCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 0.1
 */
public class AdministrationCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    public AdministrationCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Beim eingeben eines Command
     *
     * @param sender sender des Commands
     * @param cmd Command 
     * @param label Name des Commands 
     * @param args Parameter des Commands
     * @return ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("taunsm.admin." + cmd.getName())
                || sender.hasPermission("taunsm.admin.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            
            switch (cmd.getName()) {
                case "kick":
                    return kick(sender, args);
                case "ban":
                    return ban(sender, args);
                case "unban":
                    return unban(sender, args);
                case "restart":
                    return restart(args);
            }
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "invsee": 
                        return invsee(playersender, args);
                    case "end": 
                        return end(playersender, args);
                    case "day":
                        return day(playersender);
                    case "night":
                        return night(playersender);
                }
            }
            plugin.send(sender, "Das Command wurde noch nicht implementiert");
        }
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Zeigt das Inventar eines Spielers
     */
    private boolean invsee(Player sender, String[] args) {
        if (args.length == 0) return false;

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            sender.openInventory(target.getInventory());
            plugin.send(sender, "Du hast das Inventar von " + target.getName() + " geöffnet");
        } else {
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        }
        return true;
    }
    
    /**
     * Zeigt die Endertruhe eines Spielers
     */
    private boolean end(Player sender, String[] args) {
        if (args.length == 0) {
            sender.openInventory(sender.getEnderChest());
            plugin.send(sender, "Du hast deine Enderchest geöffnet");
        }
        
        if (args.length >= 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                sender.openInventory(target.getEnderChest());
                plugin.send(sender, "Du hast die Enderchest von " + target.getDisplayName() + " geöffnet");
            }
            else{
                plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            }
        }
        return true;
    }

    /**
     * Setzt die Zeit in der Welt des Senders auf Tag
     */
    private boolean day(Player sender) {
        sender.getWorld().setTime(300);
        plugin.send(sender, "Es wurde Tag");
        return true;
    }

    /**
     * Setzt die Zeit in der Welt des Senders auf Nacht
     */
    private boolean night(Player sender) {
        sender.getWorld().setTime(14000);
        plugin.send(sender, "Es wurde Nacht");
        return true;
    }
    
    /**
     * Kickt einen Spieler vom Server
     */
    private boolean kick(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            args[0] = "";
            StringBuilder sb = new StringBuilder();
            for (String s : args) sb.append(s);
            target.kickPlayer("Du wurdest gekickt: " + sb.toString());
            plugin.broadcast(target.getName() + " wurde gekickt: " + sb.toString());
        } else 
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        return true;
    }
    
     /**
     * Banned einen Spieler vom Server
     */
    private boolean ban(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        
        String name = args[0];
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s);
            
        OfflinePlayer target = plugin.getServer().getPlayer(name);
        if (target != null)
            ((Player) target).kickPlayer("Du wurdest gebanned:\n" + sb.toString() + "\nUm entbanned zu werden melde dich in unserem TS");
        else
            target = plugin.getServer().getOfflinePlayer(name);
        target.setBanned(true);
        plugin.broadcast(target.getName() + " wurde gebanned: " + sb.toString());
        //Ban Protokoll sender.getName() hat target.getName() gebanned. Grund: sb.toString()
        return true;
    }
    
     /**
     * Entbanned einen Spieler vom Server
     */
    private boolean unban(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        
        OfflinePlayer target = plugin.getServer().getPlayer(args[0]);
        if (target == null)
            target = plugin.getServer().getOfflinePlayer(args[0]);
        target.setBanned(false);
        plugin.broadcast(target.getName() + " wurde entbanned");
        //Unban Protokoll sender.getName() hat target.getName() entbanned
        return true;
    }
    
    /**
     * Bereitet den Server auf einen Restart vor
     */
    private boolean restart(String[] args) {
        if (args.length == 0) Restart.setRestart();
        else if (args[0].equalsIgnoreCase("stop")) Restart.stopRestart();
        return true;
    }
}
