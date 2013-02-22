package com.tauncraft.tauncraftservermanager;

import com.tauncraft.tauncraftservermanager.listener.BlockListener;
import com.tauncraft.tauncraftservermanager.listener.QuitListener;
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

    /**
     * Beim Enablen
     */
    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        this.loadConfig();

        //Command Registration
        
            //Sinnvoll
        this.getCommand("tp").setExecutor(new Commands(this));
        this.getCommand("tps").setExecutor(new Commands(this));
        this.getCommand("s").setExecutor(new Commands(this));
        this.getCommand("mod").setExecutor(new Commands(this));
        this.getCommand("leitung").setExecutor(new Commands(this));
        this.getCommand("say").setExecutor(new Commands(this));
        this.getCommand("port").setExecutor(new Commands(this));
        
            //Spaß
        this.getCommand("blockhead").setExecutor(new Commands(this));
        this.getCommand("head").setExecutor(new Commands(this));
        
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