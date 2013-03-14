package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * FunCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 0.1
 */
public class FunCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    public FunCommands(TauncraftServerManager plugin) {
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
        if (sender.hasPermission("taunsm.fun." + label)
                || sender.hasPermission("taunsm.fun.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (label) {
                    case "blockhead":
                        blockhead(playersender, args);
                        break;
                    case "effect":
                        effect(playersender, args);
                        break;
                    case "head":
                        head(playersender, args);
                        break;
                    default:
                        plugin.send(sender, "Das Command wurde noch nicht implementiert");
                }
            }
        }
        plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    private void blockhead(Player sender, String[] args) {
        ItemStack i = null;
        if (args.length >= 1) {
            try {
                String[] sa = args[0].split(":");
                i = new ItemStack(Integer.parseInt(sa[0]));
                if (sa.length >= 2) i.setDurability(Short.parseShort(sa[1]));
                plugin.send(sender, "Dein Kopf ist jetzt Block " + args[0]);
            } catch (Exception e) {
                plugin.send(sender, "Das ist kein gültiger Block");
            }
        } else {
            i = sender.getItemInHand();
        }
        sender.getInventory().setHelmet(i);
    }

    private void effect(Player sender, String[] args) {
        if (args.length == 0) {
            plugin.send(sender, "Verwendung: /effect <id>");
            plugin.send(sender, "IDs: ");
            plugin.send(sender, "1: Ender Auge");
            plugin.send(sender, "2: Rauch");
        }
        if (args.length == 1) {
            switch (args[0]) {
                case "1":
                    for (int a = 0; a < 10; a++) {
                        sender.getWorld().playEffect(sender.getLocation(), Effect.ENDER_SIGNAL, null);
                    }
                    break;
                case "2":
                    Location loc = sender.getLocation();
                    for (int a = 0; a < 10; a++) {
                        for (int i = 1; i < 10; i++) {
                            loc.getWorld().playEffect(loc, Effect.SMOKE, i);
                        }
                    }
                    break;
                default:
                plugin.send(sender, "Der Effekt wurde noch nicht implementiert");
            }
        }
    }
    
    private void head(Player sender, String[] args) {
        if(args.length == 0) plugin.send(sender, "Verwendung: /head <Spieler>");
        if (args.length >= 1) {
            ItemStack i = new ItemStack(Material.SKULL_ITEM);
            i.setDurability((short) 3);
            SkullMeta sm = (SkullMeta) i.getItemMeta();
            sm.setOwner(args[0]);
            i.setItemMeta(sm);
            sender.getInventory().addItem(i);
            plugin.send(sender, "Du hast nun den Kopf von " + args[0]);
        }
    }
}
