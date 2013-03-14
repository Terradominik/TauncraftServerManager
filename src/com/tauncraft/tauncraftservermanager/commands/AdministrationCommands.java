package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
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
                    default:
                        plugin.send(sender, "Das Command wurde noch nicht implementiert");
                }
            }
        }
        plugin.send(sender, "Du hast nicht die nötigen Rechte");
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
}
