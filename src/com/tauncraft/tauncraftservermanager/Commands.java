package com.tauncraft.tauncraftservermanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Command Klasse
 * @author Terradomninik | raffi287
 * @version 2012-02-22
 */
public class Commands implements CommandExecutor {

    private Plugin plugin;

    public Commands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Beim eingeben eines Command
     * @param sender    sender des Commands
     * @param command   Command
     * @param label     Name des Commands
     * @param args      Parameter des Commands
     * @return 
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("taunsm." + label) || sender.hasPermission("taunsm.*") || sender.isOp()) {
                switch (label) {
                    case "command":
                        
                        break;
                    case "command1":
                        
                        break;
                    default:
                        //Ausgabe: "Das Command wurde noch nicht implementiert"
                        return false;
                }
                return true;
            }
            //Ausgabe: "Du hast nicht die nötigen Permissions"
            return false;
        }
        return false;
    }
    
    /**
     * Teleportation
     * @param pl
     * @param strings 
     */
    private void tp(Player sender, String[] strings){
        if(strings.length == 1) {
            try {
                Player target = plugin.getServer().getPlayer(strings[0]);
                sender.teleport(target);
                //Ausgabe (an sender): "Du wurdest zu " + target.getName() " geportet"
                //Ausgabe (an target): sender.getName() + " hat sich zu dir geportet"
            } catch (NullPointerException npe) {
                //Ausgabe (an sender): "Es ist kein Spieler mit dem Namen " + strings[0] + " online"
            }
        }
        else tpConsole(sender, strings);
    }

    /**
     * Consolen Teleportation
     * @param sender
     * @param strings 
     */
    private void tpConsole(CommandSender sender, String[] strings) {
        if(strings.length >= 2) {
            try {
                Player target1 = plugin.getServer().getPlayer(strings[0]);
                Player target2 = plugin.getServer().getPlayer(strings[1]);
                target1.teleport(target2);
                //Ausgabe (an sender): target1.getName() + " wurde zu " + target2.getName() + " geportet"
                //Ausgabe (an target1): target2.getName() + " wurde von " + sender.getName() + " zu dir geportet"
                //Ausgabe (an target2): sender.getName() + " hat dich zu " + target1.getName() + " geportet"
            } catch (NullPointerException npe) {
                //Ausgabe (an sender): Die Spieler konnten nicht gefunden werden
            }
        }
    }
}
