package com.tauncraft.tauncraftservermanager;

/**
 * Zum überprüfen ob ein Restart gemacht werden will,
 * soll vor jedem Rundenstart jedes Spieles überprüft werden
 * 
 * @author Terradominik
 * @version 0.2
 */
public class Restart {
    private static boolean willRestart = false;
    
    /**
     * Überprüft, ob ein Restart gemacht werden will
     * @return true wenn wein Restart gemacht werden will
     */
    public static boolean getRestart() {
        return willRestart;
    }
    
    /**
     * Erzwingt den Willen des Servers zu Restarten
     */
    public static void setRestart() {
        willRestart = true;
    }
    
    /**
     * Besämftigt den Willen des Servers zu Restarten
     */
    public static void stopRestart() {
        willRestart = false;
    }
}
