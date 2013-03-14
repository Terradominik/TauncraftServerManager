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
     * @param command Command 
     * @param label Name des Commands 
     * @param args Parameter des Commands
     * @return ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("taunsm.admin." + label)
                || sender.hasPermission("taunsm.admin.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (label) {
                    case "invsee":
                        invsee(playersender, args);
                        break;
                    case "end":
                        end(playersender, args);
                        break;
                    case "day":
                        day(playersender);
                        break;
                    case "night":
                        night(playersender);
                        break;
                    default:
                        plugin.send(sender, "Das Command wurde noch nicht implementiert");
                }
            }
        }
        plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    private void invsee(Player sender, String[] args) {
        if (args.length == 0) plugin.send(sender, "Verwendung: /invsee <Spieler>");
        if (args.length >= 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                sender.openInventory(target.getInventory());
                plugin.send(sender, "Du hast das Inventar von " + target.getName() + " geöffnet");
            }
            else {
                plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            }
        }
    }
    
    private void end(Player sender, String[] args) {
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
    }
    
    private void day(Player sender) {
        sender.getWorld().setTime(300);
        plugin.send(sender, "Es wurde Tag");
   }
    
    private void night(Player sender) {
        sender.getWorld().setTime(14000);
        plugin.send(sender, "Es wurde Nacht");
   }
}
