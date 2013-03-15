package com.tauncraft.tauncraftservermanager;

import com.tauncraft.tauncraftservermanager.commands.AdministrationCommands;
import com.tauncraft.tauncraftservermanager.commands.ChatCommands;
import com.tauncraft.tauncraftservermanager.commands.ConfigCommands;
import com.tauncraft.tauncraftservermanager.commands.FunCommands;
import com.tauncraft.tauncraftservermanager.commands.PunishCommands;
import com.tauncraft.tauncraftservermanager.commands.TeleportCommands;
import com.tauncraft.tauncraftservermanager.listener.BlockListener;
import com.tauncraft.tauncraftservermanager.listener.JoinListener;
import com.tauncraft.tauncraftservermanager.listener.QuitListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TauncraftServerManager - Hauptklasse
 *
 * @author Terradominik | raffi287
 * @version 2012-02-22
 */
public class TauncraftServerManager extends JavaPlugin {

    //Manager
    private final RangManager rangManager = new RangManager(this);
    
    //Listener
    private final BlockListener blockListener = new BlockListener(this);
    private final JoinListener joinListener = new JoinListener(this);
    private final QuitListener quitListener = new QuitListener(this);
    
    //Commands
    private final AdministrationCommands ac = new AdministrationCommands(this);
    private final ChatCommands chc = new ChatCommands(this);
    private final ConfigCommands cfc = new ConfigCommands(this);
    private final TeleportCommands tpc = new TeleportCommands(this);
    private final FunCommands fc = new FunCommands(this);
    private final PunishCommands pc = new PunishCommands(this);
    
    private String broadcastFormat = ChatColor.AQUA + "";
    private String privateFormat = ChatColor.DARK_GRAY + "";
    

    /**
     * Beim Enablen
     */
    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        this.saveDefaultConfig();

        //Command Registration
        
        //Administration Commands
        this.getCommand("invsee").setExecutor(ac);
        this.getCommand("end").setExecutor(ac);
        this.getCommand("day").setExecutor(ac);
        this.getCommand("night").setExecutor(ac);
        this.getCommand("kick").setExecutor(ac);
        this.getCommand("ban").setExecutor(ac);
        this.getCommand("unban").setExecutor(ac);
        this.getCommand("restart").setExecutor(ac);
        this.getCommand("seen").setExecutor(ac);
        
        //Chat Commands
        this.getCommand("leitung").setExecutor(chc);
        this.getCommand("server").setExecutor(chc);
        this.getCommand("clearmsg").setExecutor(chc);
        this.getCommand("tell").setExecutor(chc);

        //Config Commands
        this.getCommand("configset").setExecutor(cfc);
        
        //Fun Commands
        this.getCommand("blockhead").setExecutor(fc);
        this.getCommand("effect").setExecutor(fc);
        this.getCommand("head").setExecutor(fc);

        //Punish
        this.getCommand("detonate").setExecutor(pc);
        this.getCommand("lightning").setExecutor(pc);

        //Teleport Commands
        this.getCommand("tp").setExecutor(tpc);
        this.getCommand("s").setExecutor(tpc);

        //Listener Registration
        pm.registerEvents(this.blockListener, this);
        pm.registerEvents(this.joinListener, this);
        pm.registerEvents(this.quitListener, this);
    }

    /**
     * Beim Disablen
     */
    @Override
    public void onDisable() {
        //Speichern der Config
        this.saveConfig();
    }

    /**
     * Broadcast Message
     */
    public void broadcast(String text) {
        this.getServer().broadcastMessage(broadcastFormat + text);
    }
    
    /**
     * Private Message
     */
    public void send(CommandSender spieler, String text) {
        spieler.sendMessage(privateFormat + text);
    }
}