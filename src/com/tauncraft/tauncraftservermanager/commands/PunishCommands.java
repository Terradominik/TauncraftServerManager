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
     * @param cmd Command 
     * @param label Name des Commands 
     * @param args Parameter des Commands
     * @return ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("taunsm.punish." + label)
                || sender.hasPermission("taunsm.punish.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            switch (label) {
                case "detonate":
                    return detonate(sender, args);
                case "lightning":
                    return lightning(sender, args);
            }
            plugin.send(sender, "Das Command wurde noch nicht implementiert");
        }
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }
    
    /**
     * Lasst einen Spieler Explodieren (nicht Violet zeigen)
     */
    private boolean detonate(CommandSender sender, String[] args){
        if(args.length == 0) return false;
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            target.getWorld().createExplosion(target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ(), 2, false, false);
            plugin.send(sender, "Du hast " + target.getDisplayName() + " hochgejagt");
        } else
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        return true;
    }
    
    /**
     * Schleudert einen Blitz auf einen Spieler (nicht Violet zeigen)
     */ 
    private boolean lightning (CommandSender sender, String[] args) {
        if(args.length == 0) return false;
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            target.getWorld().strikeLightning(target.getLocation());
            plugin.send(sender, "Du hast " + target.getDisplayName() + " einen Blitzschlag verpasst");
        } else 
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        return true;
    }
}
