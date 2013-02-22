package com.tauncraft.tauncraftservermanager;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

/**
 * Enthält alle Spieler, welche gerade als Spectatoren in einem Konzept sind
 * (zB: HungerGames, SnowSpleef, TaunCaptureTheFlag, etc)
 * 
 * @author Dominik
 */
public class SpecListe {
    private static Set<String> specListe = new HashSet<>();
    
    /**
     * Returnd die Liste
     * @return
     */
    public static Set<String> get() {
        return specListe;
    }
    
    /**
     * Ob die Liste lehr ist
     * @return 
     */
    public static boolean isEmpty() {
        return specListe.isEmpty();
    }
    
    /**
     * Added einen Spieler anhand des Namens
     * @param spieler 
     */
    public static boolean add(String spieler) {
        return specListe.add(spieler);
    }
    
    /**
     * Added einen Spieler anhand des Player Objektes
     * @param spieler 
     */
    public static boolean add(Player spieler) {
        return specListe.add(spieler.getName());
    }
    
    /**
     * Removed einen Spieler anhand des Namens
     * @param spieler 
     */
    public static boolean remove(String spieler) {
        return specListe.remove(spieler);
    }
    
    /**
     * Removed einen Spieler anhand des Player Objektes
     * @param spieler 
     */
    public static boolean remove(Player spieler) {
        return specListe.remove(spieler.getName());
    }
}
