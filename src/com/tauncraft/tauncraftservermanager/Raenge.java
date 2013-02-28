package com.tauncraft.tauncraftservermanager;

import org.bukkit.ChatColor;

public enum Raenge {
    NEU("Neu"),
    SPIELER("Spieler"),
    BAU_TEAM("Bau-Team"),
    ALPHA_BAU_TEAM("Alpha Bau-Team", ChatColor.GOLD),
    MOD("Mod", ChatColor.DARK_GREEN),
    ADMIN("Admin", ChatColor.RED);
    
    private String name;
    private ChatColor farbe;
    
    private Raenge(String name) {
        this.name = name;
    }
    
    private Raenge(String name, ChatColor farbe) {
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