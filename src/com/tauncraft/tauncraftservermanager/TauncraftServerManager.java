package com.tauncraft.tauncraftservermanager;

import com.tauncraft.tauncraftservermanager.commands.ChatCommands;
import com.tauncraft.tauncraftservermanager.commands.FunCommands;
import com.tauncraft.tauncraftservermanager.commands.PunishCommands;
import com.tauncraft.tauncraftservermanager.commands.TeleportCommands;
import com.tauncraft.tauncraftservermanager.listener.BlockListener;
import com.tauncraft.tauncraftservermanager.listener.QuitListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TauncraftServerManager - Hauptklasse
 *
 * @author Terradominik | raffi287
 * @version 2012-02-22
 */
public class TauncraftServerManager extends JavaPlugin {

    public final BlockListener blockListener = new BlockListener(this);
    public final QuitListener quitListener = new QuitListener(this);
    private TeleportCommands tpc;
    private ChatCommands cc;
    private FunCommands fc;
    private PunishCommands pc;

    /**
     * Beim Enablen
     */
    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        this.loadConfig();

        //Command Registration
        
            //Teleport
        this.getCommand("tp").setExecutor(tpc);
        //this.getCommand("tps").setExecutor(tpc);
        this.getCommand("s").setExecutor(tpc);
        //this.getCommand("port").setExecutor(tpc);
        
            //Chat
        //this.getCommand("mod").setExecutor(cc);
        //this.getCommand("leitung").setExecutor(cc);
        //this.getCommand("say").setExecutor(cc);

        
            //Fun
        this.getCommand("blockhead").setExecutor(fc);
        //this.getCommand("head").setExecutor(fc);
        
            //Punish

        //Listener Registration
        pm.registerEvents(this.blockListener, this);
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
     * Ladet Config
     */
    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}