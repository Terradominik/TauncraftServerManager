package com.tauncraft.tauncapturetheflag;

import com.tauncraft.tauncapturetheflag.listener.BlockListener;
import com.tauncraft.tauncapturetheflag.listener.QuitListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TaunCaptureTheFlag
 * @author Terradominik / Tauncraft
 * @version 0.1
 */
public class TaunCaptureTheFlag extends JavaPlugin {

    public final BlockListener blockListener = new BlockListener(this);
    public final QuitListener quitListener = new QuitListener(this);
    public final SpielManager spielManager = new SpielManager(this);
    
    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        this.loadConfig();
        Filer.loadStats();
        
        //Command Registration
        this.getCommand("tcf").setExecutor(new Commands(this));

        //Listener Registration
        pm.registerEvents(this.blockListener, this);
        pm.registerEvents(this.quitListener, this);
    }

    @Override
    public void onDisable() {
        //Speichern der Config
        this.saveConfig();
        Filer.saveStats();
    }
    
    /**
     * Ladet Config
     */
    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}