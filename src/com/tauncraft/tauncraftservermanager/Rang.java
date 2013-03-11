package com.tauncraft.tauncraftservermanager;

import org.bukkit.ChatColor;

public enum Rang {
    NEU("Neu"),
    SPIELER("Spieler"),
    BAU_TEAM("Bau-Team", ChatColor.GOLD),
    ALPHA_BAU_TEAM("Alpha Bau-Team", ChatColor.GOLD),
    SPECIAL("Special", ChatColor.DARK_PURPLE),
    MOD("Mod", ChatColor.DARK_GREEN),
    ADMIN("Admin", ChatColor.RED);
    
    private String name;
    private ChatColor farbe;
    
    private Rang(String name) {
        this.name = name;
    }
    
    private Rang(String name, ChatColor farbe) {
        this.name = name;
        this.farbe = farbe;
    }
    
    public String getColor() {
        return farbe != null ? farbe.toString() : "";
    }
    
    @Override
    public String toString() {
        return name;
    }
}