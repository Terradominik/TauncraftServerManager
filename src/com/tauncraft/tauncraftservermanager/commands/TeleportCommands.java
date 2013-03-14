package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeleportCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 0.1
 */
public class TeleportCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

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
     * @return ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("taunsm.teleport." + label)
                || sender.hasPermission("taunsm.teleport.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (label) {
                    case "tp":
                        tp(playersender, args);
                        break;
                    case "s":
                        s(playersender, args);
                        break;
                    default:
                        plugin.send(sender, "Dieses Command wurde noch nicht implementiert");
                }
            } else {
                switch (label) {
                    case "tp":
                        tpConsole(sender, args);
                        break;
                    default:
                        plugin.send(sender, "Dieses Command wurde noch nicht implementiert");
                }
            }
        }
        plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Teleportiert dich zu einem anderen Spieler
     *
     * @param sender
     * @param args
     */
    private void tp(Player sender, String[] args) {
        if (args.length == 0) plugin.send(sender, "Verwendung: /tp <Spieler1> [Spieler2]");
        else if (args.length == 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                sender.teleport(target);
                plugin.send(sender, "Du wurdest zu " + target.getName() + " geportet");
                plugin.send(target, sender.getName() + " hat sich zu dir geportet");
                for (int a = 0; a < 5; a++) {
                    sender.getWorld().playEffect(sender.getLocation(), Effect.ENDER_SIGNAL, null);
                }
            } 
            else plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        } else tpConsole(sender,args);
    }

    /**
     * Teleportiert target1 zu target2
     *
     * @param sender
     * @param args
     */
    private void tpConsole(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player target1 = plugin.getServer().getPlayer(args[0]);
            Player target2 = plugin.getServer().getPlayer(args[1]);
            if (target1 != null && target2 != null) {
                target1.teleport(target2);
                plugin.send(sender, target1.getName() + " wurde zu " + target2.getName() + " geportet");
                plugin.send(target1, target2.getName() + " wurde von " + sender.getName() + " zu dir geportet");
                plugin.send(target2, sender.getName() + " hat dich zu " + target1.getName() + " geportet");
            }
            else plugin.send(sender, "Die Spieler konnten nicht gefunden werden");
        }
    }

    /**
     * Teleportiert einen Spieler zu dir
     *
     * @param sender
     * @param args
     */
    private void s(Player sender, String[] args) {
        if (args.length == 0) plugin.send(sender, "Verwendung: /s <Spieler>");
        else if (args.length >= 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                target.teleport(sender);
                plugin.send(sender, target.getName() + " wurde zu dir geportet");
                plugin.send(target, sender.getName() + " hat dich zu ihm geportet");
            }else plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        }
    }
}
