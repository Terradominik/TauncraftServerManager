package com.tauncraft.tauncraftservermanager;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 * Enthält alle Spieler, welche gerade als Spieler in einem Konzept sind (zB: HungerGames, SnowSpleef, CaptureTheBlock, etc)
 * @author Dominik
 */
public class SpielerListe {

    private static HashMap<String,String> spielerListe = new HashMap<>();

    /**
     * Return die Liste
     */
    public static Map<String,String> get() {
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
    public static boolean add(String spieler, String konzeptname) {
        return spielerListe.put(spieler, konzeptname) != null;
    }

    /**
     * Added einen Spieler anhand des Player Objektes
     */
    public static boolean add(Player spieler, String konzeptname) {
        return SpielerListe.add(spieler.getName(), konzeptname);
    }

    /**
     * Removed einen Spieler anhand des Namens
     */
    public static boolean remove(String spieler) {
        TaunPlayer.get(spieler).resetPlayerListName();
        return spielerListe.remove(spieler) != null;
    }

    /**
     * Removed einen Spieler anhand des Player Objektes
     */
    public static boolean remove(Player spieler) {
        return SpielerListe.remove(spieler.getName());
    }
    
    /**
     * Removed einen Spieler anhand des Namens
     */
    public static boolean contains(String spieler) {
        return spielerListe.containsKey(spieler);
    }
    
    /**
     * Removed einen Spieler anhand des Player Objektes
     */
    public static boolean contains(Player spieler) {
        return SpielerListe.contains(spieler.getName());
    }
    
    /**
     * Überprüft anhand des Namens ob ein Spieler im angegebenen Konzept ist
     */
    public static boolean contains(String spieler, String konzept) {
        return spielerListe.get(spieler).equals(konzept);
    }
    
    /**
     * Überprüft anhand des Spieler Objektes ob ein Spieler im angegebenen Konzept ist
     */
    public static boolean contains(Player spieler, String konzept) {
        return SpielerListe.contains(spieler.getName(),konzept);
    }
    
    /**
     * Gibt anhand des Namens das Spiel zurück, indem sich der Spieler befindet
     */
    public static String getSpiel(String spieler) {
        return spielerListe.get(spieler);
    }
    
    /**
     * Gibt anhand des Spieler Objektes das Spiel zurück, indem sich der Spieler befindet
     */
    public static String getSpiel(Player spieler) {
        return SpielerListe.getSpiel(spieler.getName());
    }
}
