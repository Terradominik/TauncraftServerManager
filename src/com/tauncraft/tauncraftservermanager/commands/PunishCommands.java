package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * PunishCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 0.1
 */
public class PunishCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    public PunishCommands(TauncraftServerManager plugin) {
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
        if (sender.hasPermission("taunsm.punish." + label)
         || sender.hasPermission("taunsm.punish.*")
         || sender.hasPermission("taunsm.*")
         || sender.isOp()) {
            if (sender instanceof Player) {
                switch (label) {
                    case "detonate":
                        detonate(args);
                        break;
                    case "lightning":
                        lightning(args);
                        break;
                    default:
                    //Ausgabe: "Das Command wurde noch nicht implementiert"
                }
            }
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return true;
    }
    
    private void detonate(String[] args){
        if(args.length == 0) {
            //Ausgabe: "Verwendung: /detonate <Spieler>"
        }
        if(args.length >= 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                target.getWorld().createExplosion(target.getLocation(), 2);
                //Ausgabe: "Du hast " + target.getDisplayName() + " hochgejagt"
            } else {
                //Ausgabe: "Es ist kein Spieler mit dem Namen " + args[0] + " online"
            }
        }
    }
    
    private void lightning (String[] args) {
        if(args.length == 0) {
            //Ausgabe: "Verwendung: /lightning <Spieler>"
        }
        if(args.length >= 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                target.getWorld().strikeLightning(target.getLocation());
                //Ausgabe: "Du hast " + target.getDisplayName() + " einen Blitzschlag verpasst"
            } else {
                //Ausgabe: "Es ist kein Spieler mit dem Namen " + args[0] + " online"
            }
        }
    }
}
