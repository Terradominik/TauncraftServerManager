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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author
 * Raffael
 */
public class FunCommands implements CommandExecutor {

    private Plugin plugin;

    public FunCommands(TauncraftServerManager plugin) {
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
        if (sender.hasPermission("taunsm.fun." + label) || sender.hasPermission("taunsm.fun.*") ||sender.hasPermission("taunsm.*") || sender.isOp()) {
            if (sender instanceof Player) {
                switch (label) {
                    case "blockhead":
                        blockhead((Player) sender, args);
                        break;
                    default:
                        //Ausgabe: "Das Command wurde noch nicht implementiert"
                }
            }
        }
        //Ausgabe: "Du hast nicht die nötigen Permissions"
        return true;
    }

    private void blockhead(Player spieler, String[] args) {
        ItemStack i = null;
        if (args.length >= 1) {
            try{
                String[] sa = args[0].split(":");
                i = new ItemStack(Integer.parseInt(sa[0]));
                if (sa.length >= 2) {
                    i.setDurability(Short.parseShort(sa[1]));
                }
                //Ausgabe: "Dein Kopf ist jetzt Block " + strings[0]
            }
            catch(Exception e){
                //Ausgabe: "Das ist kein gültiger Block"
            }

        } else {
            i = spieler.getItemInHand();
        }
        spieler.getInventory().setHelmet(i);
    }
}
