package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Skull;
import org.bukkit.plugin.Plugin;

/**
 * Command Klasse
 *
 * @author Terradomninik | raffi287
 * @version 2012-02-22
 */
public class TeleportCommands implements CommandExecutor {

    private Plugin plugin;

    public TeleportCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Beim eingeben eines Command
     *
     * @param sender sender des Commands
     * @param command Command
     * @param label Name des Commands
     * @param args Parameter des Commands
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("taunsm.teleport." + label) || sender.hasPermission("taunsm.teleport.*") ||sender.hasPermission("taunsm.*") || sender.isOp()) {
            if (sender instanceof Player) {
                switch (label) {
                    case "tp":
                        tp((Player) sender, args);
                        break;
                    case "s":
                        s((Player) sender, args);
                        break;
                    default:
                        //Ausgabe: "Das Command wurde noch nicht implementiert"
                        return false;
                }
                return true;
            }
            return false;
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return false;
    }

    /**
     * Teleportiert dich zu einem anderen Spieler
     *
     * @param sender
     * @param args
     */
    private void tp(Player sender, String[] args) {
        if(args.length == 0) {
            //Ausgabe: "Verwendung: /tp <Spieler1> [Spieler2]"
        }
        if (args.length == 1) {
            try {
                Player target = plugin.getServer().getPlayer(args[0]);
                sender.teleport(target);
                //Ausgabe (an sender): "Du wurdest zu " + target.getName() " geportet"
                //Ausgabe (an target): sender.getName() + " hat sich zu dir geportet"
            } catch (NullPointerException npe) {
                //Ausgabe (an sender): "Es ist kein Spieler mit dem Namen " + strings[0] + " online"
            }
        } else {
            tpConsole(sender, args);
        }
    }

    /**
     * Teleportiert target1 zu target2
     *
     * @param sender
     * @param args
     */
    private void tpConsole(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            try {
                Player target1 = plugin.getServer().getPlayer(args[0]);
                Player target2 = plugin.getServer().getPlayer(args[1]);
                target1.teleport(target2);
                //Ausgabe (an sender): target1.getName() + " wurde zu " + target2.getName() + " geportet"
                //Ausgabe (an target1): target2.getName() + " wurde von " + sender.getName() + " zu dir geportet"
                //Ausgabe (an target2): sender.getName() + " hat dich zu " + target1.getName() + " geportet"
            } catch (NullPointerException npe) {
                //Ausgabe (an sender): Die Spieler konnten nicht gefunden werden
            }
        }
    }

    /**
     * Teleportiert einen Spieler zu dir
     * 
     * @param sender
     * @param args 
     */
    private void s(Player sender, String[] args) {
        if(args.length == 0) {
            //Ausgabe: "Verwendung: /s <Spieler>"
        }
        if (args.length >= 1) {
            try {
                Player target = plugin.getServer().getPlayer(args[0]);
                target.teleport(sender);
                //Ausgabe (an sender): target.getName() " wurde zu dir geportet"
                //Ausgabe (an target): sender.getName() + " hat dich zu ihm geportet"
            } catch (NullPointerException npe) {
                //Ausgabe (an sender): "Es ist kein Spieler mit dem Namen " + strings[0] + " online"
            }
        }
    }
}