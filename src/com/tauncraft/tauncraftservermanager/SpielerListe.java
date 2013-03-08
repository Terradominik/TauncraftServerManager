package com.tauncraft.tauncraftservermanager;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

/**
 * Enthält alle Spieler, welche gerade als Spieler in einem Konzept sind (zB: HungerGames, SnowSpleef, CaptureTheBlock, etc)
 * @author Dominik
 */
public class SpielerListe {

    private static Set<String> spielerListe = new HashSet<>();

    /**
     * Return die Liste
     */
    public static Set<String> get() {
        return spielerListe;
    }

    /**
     * Ob die Liste leer ist
     */
    public static boolean isEmpty() {
        return spielerListe.isEmpty();
    }

    /**
     * Added einen Spieler anhand des Namens
     */
    public static boolean add(String spieler) {
        return spielerListe.add(spieler);
    }

    /**
     * Added einen Spieler anhand des Player Objektes
     */
    public static boolean add(Player spieler) {
        return spielerListe.add(spieler.getName());
    }

    /**
     * Removed einen Spieler anhand des Namens
     */
    public static boolean remove(String spieler) {
        return spielerListe.remove(spieler);
    }

    /**
     * Removed einen Spieler anhand des Player Objektes
     */
    public static boolean remove(Player spieler) {
        return spielerListe.remove(spieler.getName());
    }
    
    /**
     * Removed einen Spieler anhand des Namens
     */
    public static boolean contains(String spieler) {
        return spielerListe.contains(spieler);
    }
    
    /**
     * Removed einen Spieler anhand des Player Objektes
     */
    public static boolean contains(Player spieler) {
        return spielerListe.contains(spieler.getName());
    }
}
