package com.tauncraft.tauncraftservermanager;

import org.bukkit.ChatColor;

/**
 * Ein Enum, welches alle unterschiedlichen
 * Ränge auf Tauncraft und deren Farben speichert
 * 
 * @author Terradominik
 * @version 0.2
 */
public enum Rang {
    NEU("Neu"),
    SPIELER("Spieler"),
    BAU_TEAM("Bau-Team", ChatColor.GOLD),
    ALPHA_BAU_TEAM("Alpha Bau-Team", ChatColor.GOLD),
    SPECIAL("Special", ChatColor.DARK_PURPLE),
    MOD("Mod", ChatColor.DARK_GREEN),
    ADMIN("Admin", ChatColor.DARK_RED);
    
    private String name;
    private ChatColor farbe;
    
    /**
     * Konstruktor
     * 
     * @param name Der Name des Ranges
     */
    private Rang(String name) {
        this.name = name;
    }
    
    /**
     * Erweiterter Konstruktor
     * 
     * @param name Der Name des Ranges
     * @param farbe Die Farbe des Ranges
     */
    private Rang(String name, ChatColor farbe) {
        this.name = name;
        this.farbe = farbe;
    }
    
    /**
     * Gibt die Farbe des Ranges zurück
     * @return Die Farbe des Ranges
     */
    public String getColor() {
        return farbe != null ? farbe.toString() : "";
    }
    
    /**
     * Gibt den Namen des Ranges zurück
     * @return Der Name des Ranges
     */
    @Override
    public String toString() {
        return name;
    }
}