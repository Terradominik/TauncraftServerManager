package com.tauncraft.tauncraftservermanager;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Stellt einen Chat indem Spieler 
 * oder Plugins schreiben dar
 * 
 * @author Terradominik
 * @version 0.2
 */
public class Chat {
    
    private static Set<Chat> allChats = new HashSet<>();
    private String name;
    private Set<Player> listener = new HashSet<>();
    private Set<Rang> raenge;
    
    private String prefix;
    private String suffix;
    
    /**
     * Konstruktor 
     * 
     * @param name Der Name des Chats (muss eindeutig sein)
     * @param prefix Der Prefix der Chatnachricht (steht vor dem Spielernamen)
     * @param suffix Der Suffix der Chatnachricht (steht nach dem Spielernamen)
     * @param raenge Ein Set aller Raenge, welche den Chat betreten dürfen (wenn null können alle Ränge den Chat betreten)
     */
    public Chat(String name, String prefix, String suffix, Set<Rang> raenge) {
        System.out.println("Neuer Chat registriert: " + name + " | " + prefix + " | " + suffix + " | " + raenge);
        this.name = name;
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        this.suffix = ChatColor.translateAlternateColorCodes('&', suffix);
        if (raenge == null) this.raenge = new HashSet<>();
        else this.raenge = raenge;
        allChats.add(this);
    }
    
    /**
     * Gibt den Prefix der Chatnachricht zurück (steht vor dem Spielernamen)
     * 
     * @return der Prefix der Chatnachricht (Text vor dem Spielernamen)
     */
    public String getPrefix() {
        return prefix;
    }
    
    /**
     * Gibt den Suffix der Chatnachricht zurück (steht nach dem Spielernamen)
     * 
     * @return der Suffix der Chatnachricht (Text nach dem Spielernamen)
     */
    public String getSuffix() {
        return suffix;
    }
    
    /**
     * Gibt das Chat Format (prefix + suffix) zurück
     * 
     * @return Das Chat Format (prefix + suffix)
     * @see #getPrefix() 
     * @see #getSuffix() 
     */
    public String getFormat() {
        return prefix + suffix;
    }
    
    /**
     * Überprüft ob der angegebene Chat der selbe wie dieser ist
     * 
     * @param chat Der Chat welcher verglichen werden soll
     * @return true wenn die Chats identisch sind
     */
    public boolean equals(Chat chat) {
        return name.equals(chat.getName());
    }
    
    /**
     * Fügt einen Spieler zum Chat hinzu
     * 
     * @param spieler Der Spieler welcher hinzugefügt werden soll
     * @return true wenn der Spieler erfolgreich hinzugefügt werden konnte
     */
    public boolean addPlayer(Player spieler) {
        TaunPlayer tp = TaunPlayer.get(spieler);
        if (raenge.contains(tp.getRang()) || raenge.isEmpty()) {
            listener.add(spieler);
            return true;
        }
        return false;
    }
    
    /**
     * Fügt einen Spieler zum Chat hinzu
     * 
     * @param spieler Der Spieler welcher hinzugefügt werden soll
     * @return true wenn der Spieler erfolgreich hinzugefügt werden konnte
     */
    public boolean addPlayer(TaunPlayer spieler) {
        if (raenge.contains(spieler.getRang()) || raenge.isEmpty()) {
            listener.add(spieler.getPlayer());
            return true;
        }
        return false;
    }
    
    /**
     * Fügt einen Spieler zum Chat hinzu
     * 
     * @param spieler Der Spieler welcher hinzugefügt werden soll
     * @return true wenn der Spieler erfolgreich hinzugefügt werden konnte
     */
    public boolean addPlayer(String spieler) {
        TaunPlayer tp = TaunPlayer.get(spieler);
        if (raenge.contains(tp.getRang()) || raenge.isEmpty()) {
            listener.add(tp.getPlayer());
            return true;
        }
        return false;
    }
    
    /**
     * Entfernt einen Spieler aus dem Chat
     * 
     * @param spieler Der Spieler welcher entfernt werden soll
     * @return true wenn der Spieler erfolgreich entfernt werden konnte
     */
    public void removePlayer(Player spieler) {
        listener.remove(spieler);
    }
    
    /**
     * Entfernt einen Spieler aus dem Chat
     * 
     * @param spieler Der Spieler welcher entfernt werden soll
     * @return true wenn der Spieler erfolgreich entfernt werden konnte
     * @see #removePlayer
     */
    public void removePlayer(TaunPlayer spieler) {
        listener.remove(spieler.getPlayer());
    }
    
    /**
     * Entfernt einen Spieler aus dem Chat
     * 
     * @param spieler Der Spieler welcher entfernt werden soll
     * @return true wenn der Spieler erfolgreich entfernt werden konnte
     * @see #removePlayer
     */
    public void removePlayer(String spieler) {
        listener.remove(Bukkit.getPlayer(spieler));
    }
    
    /**
     * Gibt eine Set aller Listener dieses Chats zurück
     * 
     * @return Das Set aller Listener dieses Chats
     */
    public Set<Player> getListener() {
        return listener;
    }
    
    /**
     * Gibt ein Set aller Chat zurück
     * 
     * @return Das Set aller Chats
     */
    public static Set<Chat> getChats() {
        return allChats;
    }
    
    /**
     * Gibt den Namen des Chats zurück
     * 
     * @return Der Name des Chats 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gibt ein Set aller Ränge zurück
     * 
     * @return Ein Set aller Ränge
     */
    public Set<Rang> getRaenge() {
        return raenge;
    }
}
