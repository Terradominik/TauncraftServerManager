package com.tauncraft.tauncraftservermanager;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

/**
 * Enthält alle Spieler, welche gerade als Spieler in einem Konzept sind
 * (zB: HungerGames, SnowSpleef, TaunCaptureTheFlag, etc)
 * 
 * @author Dominik
 */
public class SpielerListe {
    private static Set<String> spielerListe = new HashSet<>();
    
    /**
     * Returnd die Liste
     * @return
     */
    public static Set<String> get() {
        return spielerListe;
    }
    
    /**
     * Ob die Liste lehr ist
     * @return 
     */
    public static boolean isEmpty() {
        return spielerListe.isEmpty();
    }
    
    /**
     * Added einen Spieler anhand des Namens
     * @param spieler 
     */
    public static boolean add(String spieler) {
        return spielerListe.add(spieler);
    }
    
    /**
     * Added einen Spieler anhand des Player Objektes
     * @param spieler 
     */
    public static boolean add(Player spieler) {
        return spielerListe.add(spieler.getName());
    }
    
    /**
     * Removed einen Spieler anhand des Namens
     * @param spieler 
     */
    public static boolean remove(String spieler) {
        return spielerListe.remove(spieler);
    }
    
    /**
     * Removed einen Spieler anhand des Player Objektes
     * @param spieler 
     */
    public static boolean remove(Player spieler) {
        return spielerListe.remove(spieler.getName());
    }
}
