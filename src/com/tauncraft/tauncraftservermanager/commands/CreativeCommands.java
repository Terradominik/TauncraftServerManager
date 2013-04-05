package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.SpielerListe;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.util.IllegalFormatException;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Verwaltet alle Commands, welche mit dem Creative Mode oder dem Bau-Team zu tun haben
 * 
 * @author Terradomninik
 * @version 0.2
 */
public class CreativeCommands implements CommandExecutor {
    private TauncraftServerManager plugin;
    
    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public CreativeCommands (TauncraftServerManager plugin) {
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
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        if (sender.hasPermission("taunsm.creative." + cmd.getName())
                || sender.hasPermission("taunsm.creative.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "tc":
                        return tc(playersender, args);
                    case "gm":
                        return gm(playersender, args);
                }
            }
            plugin.send(sender, "Dieses Command wurde noch nicht implementiert");
        } 
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Toggelt den Gamemode zwischen Creative und Survival
     */
    private boolean tc(Player playersender, String[] args) {
        if (args.length == 0) {
            GameMode targetGm = playersender.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL;
            playersender.setGameMode(targetGm);
            plugin.send(playersender, "Dein Gamemode wurde zu " + targetGm.toString() + " geändert");
        } else {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) plugin.send(playersender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            else {
                GameMode targetGm = playersender.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL;
                target.setGameMode(targetGm);
                plugin.send(playersender, "Der Gamemode von " + target.getName() + " wurde zu " + targetGm.toString() + " geändert");
            }
        }
        return true;
    }

    /**
     * Ändert den Gamemode
     */
    private boolean gm(Player playersender, String[] args) {
        if (args.length == 0) return false;
        int pointer = 0;
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            target = playersender;
            pointer++;
        }
        GameMode targetGm = null;
        try {
            targetGm = GameMode.getByValue(Integer.parseInt(args[pointer]));
        } catch (NumberFormatException nfe) {
            try {
                GameMode.valueOf(args[pointer]);
            } catch (IllegalFormatException ife) {
                return false;
            }
        }
        if (targetGm == null) return false;
        target.setGameMode(targetGm);
        if (pointer == 0) plugin.send(playersender, "Dein Gamemode wurde zu " + targetGm.toString() + " geändert");
        else plugin.send(playersender, "Der Gamemode von " + target.getName() + " wurde zu " + targetGm.toString() + " geändert");
        return true;
    }
}
