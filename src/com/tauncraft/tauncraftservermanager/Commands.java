package com.tauncraft.tauncraftservermanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {

    private Plugin plugin;

    public Commands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

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
                        return false;

                }
                return true;
            }
        }
        return false;
    }
    
    public void tp(Player pl, String[] strings){
        Player empf;
        Player empf2;
        if(strings.length == 1) {
            if(plugin.getServer().getPlayer(strings[0]) != null){
                empf = plugin.getServer().getPlayer(strings[0]);
                pl.teleport(empf);
            }
            else{
                //Ausgabe: Dieser Spieler ist momentan nicht Online.
            }
        }
        else if(strings.length >= 2) {
            if(plugin.getServer().getPlayer(strings[1]) != null) {
                
            }
        }
    }
}
