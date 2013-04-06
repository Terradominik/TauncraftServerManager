package com.tauncraft.tauncraftservermanager;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 * Enthält alle Spieler, welche gerade als Spieler in einem 
 * Konzept sind (zB: HungerGames, SnowSpleef,CaptureTheSheep, etc)
 * 
 * @author Dominik
 * @version 0.2
 */
public class SpielerListe {

    private static HashMap<String,String> spielerListe = new HashMap<>();

    /**
     * Fügt einen Spieler zu einem Spiel hinzu
     * 
     * @param spieler Der Spieler, welcher hinzugefügt werden soll
     * @param spiel Das Spiel, zudem der Spieler hinzugefügt werden soll
     * @return Ob der Spieler erfolgreich hinzugefügt wurde
     */
    public static boolean add(String spieler, String spiel) {
        return spielerListe.put(spieler, spiel) != null;
    }

    /**
     * Fügt einen Spieler zu einem Spiel hinzu
     * 
     * @param spieler Der Spieler, welcher hinzugefügt werden soll
     * @param spiel Das Spiel, zudem der Spieler hinzugefügt werden soll
     * @return Ob der Spieler erfolgreich hinzugefügt wurde
     * @see SpielerListe#add
     */
    public static boolean add(Player spieler, String spiel) {
        return SpielerListe.add(spieler.getName(), spiel);
    }

    /**
     * Löscht einen Spieler aus der Liste
     * 
     * @param spieler Der Spieler, welcher gelöscht werden soll
     * @return Ob der Spieler in der Liste enthalten war
     */
    public static boolean remove(String spieler) {
        TaunPlayer tp = TaunPlayer.get(spieler);
        tp.resetPlayerListName();
        tp.resetChats();
        return spielerListe.remove(spieler) != null;
    }

    /**
     * Löscht einen Spieler aus der Liste
     * 
     * @param spieler Der Spieler, welcher gelöscht werden soll
     * @return true wenn der Spieler in der Liste enthalten war
     * @see SpielerListe#remove
     */
    public static boolean remove(Player spieler) {
        return SpielerListe.remove(spieler.getName());
    }
    
    /**
     * Überprüft, ob der Spieler in der Liste vorkommt
     * 
     * @param spieler Der Spieler, welcher überprüft werden soll
     * @return true wenn der Spieler in der Liste vorkommt
     */
    public static boolean contains(String spieler) {
        return spielerListe.containsKey(spieler);
    }
    
    /**
     * Überprüft, ob der Spieler in der Liste vorkommt
     * 
     * @param spieler Der Spieler, welcher überprüft werden soll
     * @return true wenn der Spieler in der Liste vorkommt
     * @see SpielerListe#contains
     */
    public static boolean contains(Player spieler) {
        return SpielerListe.contains(spieler.getName());
    }
    
    /**
     * Überprüft, ob sich der Spieler in dem angegebenen Spiel befindet
     * 
     * @param spieler Der Spieler, welcher überprüft werden soll
     * @param spiel Das Spiel, welches überprüft werden soll
     */
    public static boolean contains(String spieler, String spiel) {
        return spielerListe.get(spieler).equals(spiel);
    }
    
    /**
     * Überprüft, ob sich der Spieler in dem angegebenen Spiel befindet
     * 
     * @param spieler Der Spieler, welcher überprüft werden soll
     * @param spiel Das Spiel, welches überprüft werden soll
     * @see SpielerListe#contains
     */
    public static boolean contains(Player spieler, String spiel) {
        return SpielerListe.contains(spieler.getName(),spiel);
    }
    
    ////////
    //Getter
    ////////    
    
    /**
     * Gibt die SpielerListe zurück
     * 
     * @return Die Spielerliste
     */
    public static Map<String,String> get() {
        return spielerListe;
    }

    /**
     * Gibt zurück, ob die SpielerListe leer ist
     * 
     * @return Ob die SpielerListe leer ist
     */
    public static boolean isEmpty() {
        return spielerListe.isEmpty();
    }
    
    /**
     * Gibt das Spiel zurück, indem sich der Spieler befindet
     * 
     * @param spieler Der Name des Spielers
     * @return Der Name des Spieles, indem sich der Spieler befindet
     */
    public static String getSpiel(String spieler) {
        return spielerListe.get(spieler);
    }
    
    /**
     * Gibt das Spiel zurück, indem sich der Spieler befindet
     * 
     * @param spieler Der Spieler
     * @return Der Name des Spieles, indem sich der Spieler befindet
     * @see SpielerListe#getSpiel
     */
    public static String getSpiel(Player spieler) {
        return SpielerListe.getSpiel(spieler.getName());
    }

}
