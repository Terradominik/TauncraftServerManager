package com.tauncraft.tauncraftservermanager;

/**
 * @author Dominik
 */
public enum ChatPriority {
    GLOBAL, //Globaler Chat, wird von allen jeden Spieler gesehen (Nicht nutzen, da in Bukkit broadcast vorhanden ist)
    PUBLIC, //Jede Person kann den Channel joinen oder verlassen
    LONELY, 
    PROTECTED;
}
