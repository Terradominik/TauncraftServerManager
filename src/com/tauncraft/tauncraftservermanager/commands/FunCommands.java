/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * FunCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 2012-02-22
 */
public class FunCommands implements CommandExecutor {

    private Plugin plugin;

    public FunCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Beim eingeben eines Command
     *
     * @param sender
     * sender des Commands
     * @param command
     * Command
     * @param label
     * Name des Commands
     * @param args
     * Parameter des Commands
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("taunsm.fun." + label) || sender.hasPermission("taunsm.fun.*") || sender.hasPermission("taunsm.*") || sender.isOp()) {
            if (sender instanceof Player) {
                switch (label) {
                    case "blockhead":
                        blockhead((Player) sender, args);
                        break;
                    case "effect":
                        effect((Player) sender, args);
                        break;
                    default:
                    //Ausgabe: "Das Command wurde noch nicht implementiert"
                }
            }
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return true;
    }

    private void blockhead(Player sender, String[] args) {
        ItemStack i = null;
        if (args.length >= 1) {
            try {
                String[] sa = args[0].split(":");
                i = new ItemStack(Integer.parseInt(sa[0]));
                if (sa.length >= 2) {
                    i.setDurability(Short.parseShort(sa[1]));
                }
                //Ausgabe: "Dein Kopf ist jetzt Block " + strings[0]
            } catch (Exception e) {
                //Ausgabe: "Das ist kein gültiger Block"
            }

        } else {
            i = sender.getItemInHand();
        }
        sender.getInventory().setHelmet(i);
    }

    private void effect(Player sender, String[] args) {
        String id = " ";
        if (args.length == 0) {
            //Ausgabe: "Verwendung: /effect <id>"
            //Ausgabe: "IDs: EnderAuge = 1"
            //Ausgabe: "Rauch = 2"
        }
        if (args.length == 1) {
            switch (args[0]) {
                case "1":
                    for (int a = 0; a < 10; a++) {
                        sender.getWorld().playEffect(sender.getLocation(), Effect.ENDER_SIGNAL, null);
                    }
                    break;
                case "2":
                    for (int a = 0; a < 10; a++) {
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 1);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 2);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 3);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 4);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 5);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 6);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 7);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 8);
                        sender.getWorld().playEffect(sender.getLocation(), Effect.SMOKE, 9);
                    }
                    break;
                default:
                //Ausgabe: "Der Effekt wurde noch nicht implementiert"
            }

        }
    }
}
