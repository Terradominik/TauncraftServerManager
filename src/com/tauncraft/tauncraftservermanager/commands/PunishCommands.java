/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author
 * Raffael
 */
public class PunishCommands implements CommandExecutor {

    private Plugin plugin;

    public PunishCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Beim
     * eingeben
     * eines
     * Command
     *
     * @param
     * sender
     * sender
     * des
     * Commands
     * @param
     * command
     * Command
     * @param
     * label
     * Name
     * des
     * Commands
     * @param
     * args
     * Parameter
     * des
     * Commands
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("taunsm.punish." + label) || sender.hasPermission("taunsm.punish.*") || sender.hasPermission("taunsm.*") || sender.isOp()) {
            if (sender instanceof Player) {
                switch (label) {
                    case "detonate":
                        detonate((Player) sender, args);
                        break;
                    default:
                    //Ausgabe: "Das Command wurde noch nicht implementiert"
                }
            }
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return true;
    }
    
    private void detonate(Player sender, String[] args){
        if(args.length == 0) {
            //Ausgabe: "Verwendung: /detonate <Spieler>"
        }
        if(args.length >= 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                target.getWorld().createExplosion(target.getLocation(), 2);
                //Ausgabe: "Du hast " + target.getDisplayName() + " hochgejagt"
            } else {
                //Ausgabe: "Kein Spieler mit diesem Namen online"
            }
            
        }
    }
}
