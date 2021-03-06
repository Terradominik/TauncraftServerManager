package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;

/**
 * Verwaltet alle "Punish" Commands
 *
 * @author raffi287
 * @version 0.2
 */
public class PunishCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public PunishCommands(TauncraftServerManager plugin) {
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
        if (sender.hasPermission("taunsm.punish." + label)
                || sender.hasPermission("taunsm.punish.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            switch (label) {
                case "detonate":
                    return detonate(sender, args);
                case "lightning":
                    return lightning(sender, args);
                case "spiderstorm":
                    return spiderstorm(sender, args);
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
    
    private boolean spiderstorm(CommandSender sender, String[] args) {
        if(args.length == 0) return false;
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            for(int i = 0; i < 6; i++){
                Spider sp = (Spider)target.getWorld().spawnEntity(target.getLocation(), EntityType.SPIDER);
                sp.setTarget(target);
            }
            plugin.send(sender, "Du hast " + target.getDisplayName() + " einen Spiderstorm geschickt");
        } else 
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        return true;
    }
}
