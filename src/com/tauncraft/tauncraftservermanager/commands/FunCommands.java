package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.SpielerListe;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Verwaltet alle "Fun" Commands
 *
 * @author raffi287
 * @version 0.2
 */
public class FunCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public FunCommands(TauncraftServerManager plugin) {
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
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        if (sender.hasPermission("taunsm.fun." + cmd.getName())
                || sender.hasPermission("taunsm.fun.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "blockhead":
                        return blockhead(playersender, args);
                    case "effekt":
                        return effekt(playersender, args);
                    case "head":
                        return head(playersender, args);
                    case "name":
                        return name(playersender, args);
                }
            }
            plugin.send(sender, "Das Command wurde noch nicht implementiert");
        }
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Setzt den Kopf des Spielers zu einem beliebigen Block
     */
    private boolean blockhead(Player sender, String[] args) {
        if (args.length == 0) sender.getInventory().setHelmet(sender.getItemInHand());
        else {
            try {
                String[] sa = args[0].split(":");
                ItemStack i = new ItemStack(Integer.parseInt(sa[0]));
                if (sa.length >= 2) i.setDurability(Short.parseShort(sa[1]));
                sender.getInventory().setHelmet(i);
                plugin.send(sender, "Dein Kopf ist jetzt Block " + args[0]);
            } catch (Exception e) {
                plugin.send(sender, "Das ist kein gültiger Block");
            }
        }
        return true;
    }

    /**
     * Spielt einen Effekt in der Nähe des Spielers ab und macht ihn dadurch besonders
     */
    private boolean effekt(Player sender, String[] args) {
        if (args.length != 1) return false;
        
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
        return true;
    }
    
    /**
     * Erstellt einen Kopf des Spielers
     */
    private boolean head(Player sender, String[] args) {
        ItemStack i = new ItemStack(Material.SKULL_ITEM);
        i.setDurability((short) 3);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        if (args.length == 0){
            sm.setOwner(sender.getName());
            plugin.send(sender, "Du hast nun deinen Kopf");
        }
        else{
            sm.setOwner(args[0]);
            plugin.send(sender, "Du hast nun den Kopf von " + args[0]);
        }
        i.setItemMeta(sm);
        sender.getInventory().addItem(i);
        return true;
    }
    
    /**
     * Ändert den Namen eines Items 
     */
    private boolean name(Player sender, String[] args){
        if(args.length == 0) plugin.send(sender, "Bitte gib einen Namen ein");
        else {
            String s = "";
            s += args[0];
            for (int i = 1; i < args.length; i++) {
                s += " " + args[i];
            }
            ItemStack i = sender.getItemInHand();
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(s.replace("&", "§"));
            String name = im.getDisplayName();
            i.setItemMeta(im);
            plugin.send(sender, "Das Item hat nun den Namen: " + ChatColor.WHITE + name);
        }
        return true;
    }
}
