package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            switch (label) {
                case "detonate":
                    detonate(sender, args);
                    break;
                case "lightning":
                    lightning(sender, args);
                    break;
                default:
                    plugin.send(sender, "Das Command wurde noch nicht implementiert");
            }
        }
        plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }
    
    private void detonate(CommandSender sender, String[] args){
        if(args.length == 0) plugin.send(sender, "Verwendung: /detonate <Spieler>");
        if(args.length >= 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                target.getWorld().createExplosion(target.getLocation(), 2);
                plugin.send(sender, "Du hast " + target.getDisplayName() + " hochgejagt");
            } else {
                plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            }
        }
    }
    
    private void lightning (CommandSender sender, String[] args) {
        if(args.length == 0) plugin.send(sender, "Verwendung: /lightning <Spieler>");
        if(args.length >= 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                target.getWorld().strikeLightning(target.getLocation());
                plugin.send(sender, "Du hast " + target.getDisplayName() + " einen Blitzschlag verpasst");
            } else {
                plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            }
        }
    }
}
