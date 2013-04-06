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
    NEU("Neu","default"),
    SPIELER("Spieler","spieler"),
    TEST_BAU_TEAM("Test-Bau-Team","testbau"),
    BAU_TEAM("Bau-Team","bau", ChatColor.GOLD),
    ALPHA_BAU_TEAM("Alpha Bau-Team","alphabau", ChatColor.GOLD),
    SPECIAL("Special","special", ChatColor.DARK_PURPLE),
    MOD("Mod","mod", ChatColor.DARK_GREEN),
    ADMIN("Admin","admin", ChatColor.DARK_RED);
    
    private String name;
    private String permname;
    private ChatColor farbe;
    
    /**
     * Konstruktor
     * 
     * @param name Der Name des Ranges
     * @param permname Der Name des Ranges in PEX
     */
    private Rang(String name, String permname) {
        this.name = name;
        this.name = permname;
    }
    
    /**
     * Erweiterter Konstruktor
     * 
     * @param name Der Name des Ranges
     * @param permname Der Name des Ranges in PEX
     * @param farbe Die Farbe des Ranges
     */
    private Rang(String name, String permname, ChatColor farbe) {
        this.name = name;
        this.permname = permname;
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
    
    /**
     * Gibt den Namen des Ranges in PEX zurück
     * @return Der Name des PEX Ranges
     */
    public String getPermName() {
        return permname;
    }
}