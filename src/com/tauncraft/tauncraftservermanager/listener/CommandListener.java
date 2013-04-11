package com.tauncraft.tauncraftservermanager.listener;

import com.tauncraft.tauncraftservermanager.Rang;
import com.tauncraft.tauncraftservermanager.TaunPlayer;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Listener für das Ausführen eines Commands
 * 
 * @author Terradominik
 * @version 0.2
 */
public class CommandListener implements Listener {
    
    public TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public CommandListener(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Wird ausgeführt, sobald ein Command ausgeführt wird
     * @param event Das Event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/pex")) {
            String[] args = event.getMessage().split(" ");
            if (args.length == 6) {
                if (args[1].equalsIgnoreCase("user") && args[3].equalsIgnoreCase("group") && args[4].equalsIgnoreCase("set")) {
                    Player target = plugin.getServer().getPlayer(args[2]);
                    if (target != null) {
                        TaunPlayer tp = TaunPlayer.get(target);
                        Rang targetrang = Rang.getRangByPEX(args[5]);
                        if (targetrang != Rang.NEU) {
                            tp.setRang(targetrang);
                        }
                    }
                }
            }
        } else if (event.getMessage().startsWith("/save-all")) {
            for (TaunPlayer tp : TaunPlayer.getList()) {
                tp.save();
                tp.saveRang();
            }
        }
    }
}
