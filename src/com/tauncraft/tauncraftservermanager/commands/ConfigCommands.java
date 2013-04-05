package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * AdministrationCommands Klasse
 *
 * @author Terradomninik | raffi287
 * @version 0.1
 */
public class ConfigCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    public ConfigCommands(TauncraftServerManager plugin) {
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
        if (sender.hasPermission("taunsm.config." + cmd.getName())
                || sender.hasPermission("taunsm.config.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            
            switch (cmd.getName()) {
                case "configset":
                    return configset(sender, args);
            }
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "test": 
                        return false;
                }
            }
            plugin.send(sender, "Das Command wurde noch nicht implementiert");
        }
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Setzt einen Wert in die Config
     */
    private boolean configset(CommandSender sender, String[] args) {
        String path = args[0];
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");
        
        plugin.getConfig().set(path, sb.toString());
        plugin.saveConfig();
        return true;
    }
}
