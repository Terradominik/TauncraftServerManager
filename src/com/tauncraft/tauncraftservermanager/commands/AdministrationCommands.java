package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * AdministrationCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 0.1
 */
public class AdministrationCommands implements CommandExecutor {

    private Plugin plugin;

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
                switch (label) {
                    case "invsee":
                        invsee((Player) sender, args);
                        break;
                    case "end":
                        end((Player) sender, args);
                        break;
                    case "day":
                        day((Player) sender);
                        break;
                    case "night":
                        night((Player) sender);
                        break;
                    default:
                    //Ausgabe: "Das Command wurde noch nicht implementiert"
                }
            }
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return true;
    }

    private void invsee(Player sender, String[] args) {
        if (args.length == 0) {
            //Ausgabe: "Verwendung: /invsee <Spieler>"
        }
        if (args.length >= 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                sender.openInventory(target.getInventory());
                //Ausgabe: "Du hast das Inventar von " + target.getDisplayName() + " geöffnet"
            }
            else {
                //Ausgabe: "Es ist kein Spieler mit dem Namen " + args[0] + " online"
            }
        }
    }
    
    private void end(Player sender, String[] args) {
        if (args.length == 0) {
            sender.openInventory(sender.getEnderChest());
        }
        if (args.length >= 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                sender.openInventory(target.getEnderChest());
                //Ausgabe: "Du hast die Enderchest von " + target.getDisplayName() + " geöffnet"
            }
            else{
                //Ausgabe: "Es ist kein Spieler mit dem Namen " + args[0] + " online"
            }
        }
    }
    
    private void day(Player sender) {
        sender.getWorld().setTime(300);
        //Ausgabe: "Es wurde Tag"
   }
    
    private void night(Player sender) {
        sender.getWorld().setTime(14000);
        //Ausgabe: "Es wurde Nacht"
   }
}
