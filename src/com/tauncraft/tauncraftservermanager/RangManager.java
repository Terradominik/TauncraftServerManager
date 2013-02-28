package com.tauncraft.tauncraftservermanager;

import java.util.Map;
import org.bukkit.entity.Player;

/**
 * Noch nicht Fertig! (Ränge werden mit Enums gelöst)
 * @author Dominik
 */
public class RangManager {
    private static Map<String, String> rangMap;

    public static Map<String, String> getRangMap() {
        return rangMap;
    }
    
    public static String getRangColor(Player spieler) {
        return rangMap.get(spieler.getName());
    }
}
