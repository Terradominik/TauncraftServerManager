package com.tauncraft.tauncraftservermanager;

/**
 * Zum überprüfen ob ein Restart gemacht werden will,
 * soll vor jedem Rundenstart überprüft werden
 * @author Dominik
 */
public class Restart {
    private static boolean willRestart = false;
    
    public static boolean getRestart() {
        return willRestart;
    }
    
    public static void setRestart() {
        willRestart = true;
    }
    
    public static void stopRestart() {
        willRestart = false;
    }
}
