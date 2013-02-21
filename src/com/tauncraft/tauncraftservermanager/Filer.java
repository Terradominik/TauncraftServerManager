package com.tauncraft.tauncraftservermanager;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Verwaltet die stats.yml
 * @author Dominik
 */
public class Filer {

    private static YamlConfiguration stats;
    private static File statsFile;
    private static boolean loaded = false;
    private TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin 
     */
    private Filer(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * @return die Config
     */
    public static YamlConfiguration getStats() {
        if (!loaded) {
            loadStats();
        }
        return stats;
    }
    
    /**
     * @return das File
     */
    public static File getStatsFile() {
        return statsFile;
    }

    /**
     * Laden der Stats
     */
    public static void loadStats() {
        statsFile = new File(Bukkit.getServer().getPluginManager().getPlugin("TaunCaptureTheFlag").getDataFolder(), "stats.yml");
        if (statsFile.exists()) {
            stats = new YamlConfiguration();
            try {
                stats.load(statsFile);
            } catch (IOException | InvalidConfigurationException ex) {}
            loaded = true;
        }
    }
    
    /**
     * Speichern der Stats
     */
    public static void saveStats() {
        try {
            stats.save(statsFile);
        } catch (Exception ex) {}
    }
}