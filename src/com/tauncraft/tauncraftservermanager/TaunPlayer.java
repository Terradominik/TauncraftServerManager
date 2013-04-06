package com.tauncraft.tauncraftservermanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Verwaltet einen Spieler auf Tauncraft
 *
 * @author Terradominik
 * @version 0.2
 */
public class TaunPlayer {
    
    private int id;
    private String name;
    private Rang rang;
    private Chat writeChat;
    private int totalTaunpoints,differTaunpoints;
    private static Map<String, TaunPlayer> tp = new HashMap<>();
    
    /**
     * Konstruktor
     * 
     * @param id Die Datenbank ID des Spielers
     * @param name Der Name des Spielers
     * @param taunpoints Die Taunpoints des Spielers
     * @param rang Der Rang des Spielers
     */
    public TaunPlayer(int id, String name, int taunpoints, Rang rang) {
        this.id = id;
        this.name = name;
        totalTaunpoints = taunpoints;
        this.rang = rang;
        tp.put(name, this);
        writeChat = TauncraftServerManager.getDefaultWriteChat();
        this.resetPlayerListName();
        for (Chat c : TauncraftServerManager.getDefaultChats()) c.addPlayer(this);
    }
    
    ////////
    //Statische
    //Getter
    ////////    
    
    /**
     * Gibt die Collection aller {@link TaunPlayer} 
     * Objekte zum zurück
     * 
     * @return Die Collection aller TaunPlayer
     */
    public static Collection<TaunPlayer> getList() {
        return tp.values();
    }
    
    /**
     * Gibt as {@link TaunPlayer} Objekt zum
     * dazugehörigen Spieler zurück
     * 
     * @param name der Name des Spielers
     * @return Das zum Spieler gehörende TaunPlayer Objekt
     */
    public static TaunPlayer get(String name) {
        return tp.get(name);
    }
    
    /**
     * Gibt as {@link TaunPlayer} Objekt zum
     * dazugehörigen Spieler zurück
     * 
     * @param spieler Der Spieler
     * @return Das zum Spieler gehörende TaunPlayer Objekt
     */
    public static TaunPlayer get(Player spieler) {
        return TaunPlayer.get(spieler.getName());
    }
    
    /**
     * Setzt den Rang des Spielers
     * 
     * @param rang Der Rang des Spielers
     * @see Rang
     */
    public void setRang(Rang rang){
        this.rang = rang;
        
        PreparedStatement stmnt = DatabaseManager.prepareStatement("UPDATE spieler SET rangID=? WHERE userID=?;");
        try {
            stmnt.setString(1, rang.toString());
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
            stmnt.close();
        } catch (SQLException ex) {
        }
    }
    
    /**
     * Speichert den Rang des Spielers
     * 
     * @see Rang
     */
    public void saveRang(){
        PreparedStatement stmnt = DatabaseManager.prepareStatement("UPDATE spieler SET rangID=? WHERE userID=?;");
        try {
            stmnt.setString(1, rang.toString());
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
            stmnt.close();
        } catch (SQLException ex) {
        }
    }
    
    /**
     * Fügt dem Spieler einen {@link Chat} hinzu
     * 
     * @param chat Der Chat, welcher hinzugefügt werden soll
     */
    public void addChat(Chat chat) {
        chat.addPlayer(name);
    }
    
    /**
     * Fügt dem Spieler einen {@link Chat} hinzu
     * und löscht alle anderen
     * 
     * @param chat Der Chat, welcher hinzugefügt werden soll
     */
    public void addChatOnly(Chat chat) {
        for (Chat c : Chat.getChats()) {
            c.removePlayer(name);
        }
        chat.addPlayer(name);
    }
    
    
    /**
     * Fügt dem Spieler Taunpoints hinzu
     * 
     * @param value Die Anzahl der Taunpoints welche hinzugefügt werden sollen
     * @throws IllegalArgumentException Wenn die angegebenen Taunpoints weniger als 1 sind
     */
    public void addTaunpoints(int value) {
        if (value < 1) throw new IllegalArgumentException();
        totalTaunpoints += value;
        differTaunpoints += value;
    }
    
    /**
     * Zieht dem Spieler Taunpoints ab
     * 
     * @param value Die Anzahl der Taunpoints welche abgezogen werden sollen
     * @throws NotEnoughTaunpointsException Wenn der Spieler nicht genügend Taunpoints hat
     * @throws IllegalArgumentException Wenn die angegebenen Taunpoints weniger als 1 sind
     */
    public void removeTaunpoints(int value) throws NotEnoughTaunpointsException {
        if (value < 1) throw new IllegalArgumentException();
        if (totalTaunpoints - value >= 0) {
            totalTaunpoints -= value;
            differTaunpoints -= value;
        }
        else throw new NotEnoughTaunpointsException();
    }

    /**
     * Führt reload aus
     */
    public void save() {
        this.reload();
    }
    
    /**
     * Speichert die Taunpoints des Spielers in der Datenbank
     * 
     * @return Wenn das Speichern erfolgreich war
     */
    public boolean saveTaunpoints() {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("UPDATE spieler SET taunpoints=taunpoints+? WHERE userID=?;");
        try {
            stmnt.setInt(1, differTaunpoints);
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
            stmnt.close();
            differTaunpoints = 0;
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Ladet die Taunpoints des Spielers aus der Datenbank
     * Achtung, sollte nur mit Bedacht ausgeführt werden, da
     * der Spieler so eventuell nicht in der Datenbank
     * gespeicherte Taunpoints verliert.
     * 
     * @see #reload
     * @see #save
     * @return Wenn das Laden erfolgreich war
     */
    public boolean load() {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("SELECT taunPoints FROM spieler WHERE userID=? LIMIT 1;");
        try {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            differTaunpoints = 0;
            totalTaunpoints = rs.getInt(1);
            rs.close();
            stmnt.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Speichert die Taunpoints in die Datenbank
     * und ladet daraus danach die Updates.
     * 
     * @see #save
     * @see #reload
     */
    public void reload() {
        if(this.saveTaunpoints()) this.load();
    }
    
    /**
     * Setzt den Namen des Spielers zum Standard Format zurück
     * Diese Methode wird automatisch von der {@link SpielerListe}
     * beim {@link SpielerListe#remove verlassen} des Spieles aufgerufen.
     * 
     * @see #setPlayerListName
     * @see SpielerListe
     */
    public void resetPlayerListName(){
        String stringName = rang.getColor() + name;
        if (stringName.length() > 16) {
            stringName = stringName.substring(0,16);
        }
        this.getPlayer().setPlayerListName(stringName);
    }
    
    /**
     * Setzt den Namen des Spielers in 
     * der Spieler Übersicht (TAB)
     * 
     * @param prefix Det Text der vor dem Spieler hinzugefügt werden soll
     * @see #setPlayerListName
     * @see #resetPlayerListName
     */
    public void setPlayerListName(String prefix){
        String stringName = prefix + name;
        if (stringName.length() > 16) {
            stringName = stringName.substring(0, 14) + "..";
        }
        this.getPlayer().setPlayerListName(stringName);
    }
    
    /**
     * Setzt den Namen des Spielers in 
     * der Spieler Übersicht (TAB)
     * 
     * @param prefix Det Text der vor dem Spieler hinzugefügt werden soll
     * @param suffix Det Text der vor dem Spieler hinzugefügt werden soll
     * @see #setPlayerListName
     * @see #resetPlayerListName
     */
    public void setPlayerListName(String prefix, String suffix){
        String stringName = prefix + name + suffix;
        if (stringName.length() > 16) stringName = stringName.substring(0, 14) + "..";
        this.getPlayer().setPlayerListName(stringName);
    }
    
    /**
     * Setzt den Write Chat, also der Chat indem
     * der Spieler schreibt, zu dem angegebenen Chat
     * 
     * @param chat Der Chat, welcher als WriteChat fungieren soll
     * @return Ob das Setzen erfolgreich war
     * @see Chat
     */
    public boolean setWriteChat(Chat chat) {
        if (chat.getRaenge().contains(rang) || chat.getRaenge().isEmpty()) {
            writeChat = chat;
            return true;
        }
        return false;
    }
    
    /**
     * Setzt die Listening Chats und den Write Chat wieder zum Default
     * Diese Methode wird automatisch von der {@link SpielerListe}
     * beim {@link SpielerListe#remove verlassen} des Spieles aufgerufen.
     * 
     * @see SpielerListe
     * @see Chat
     */
    public void resetChats() {
        writeChat = TauncraftServerManager.getDefaultWriteChat();
        for (Chat c : TauncraftServerManager.getDefaultChats()) c.addPlayer(this);
    }
    
    /**
     * Setzt den Write Chat wieder zum Default
     * 
     * @see Chat
     */
    public void resetWriteChat() {
        writeChat = TauncraftServerManager.getDefaultWriteChat();
    }
    
    /**
     * Setzt die Listening Chats wieder zum Default
     * 
     * @see Chat
     */
    public void resetListeningChats() {
        for (Chat c : TauncraftServerManager.getDefaultChats()) c.addPlayer(this);
    }
    
    /**
     * Fügt den Spieler zum angegeben Spiel hinzu
     * 
     * @param spiel Das Spiel zu welchem der Spieler geadded werden soll
     * @return true wenn das Hinzufügen erfolgreich war
     * @see SpielerListe#add
     */
    public boolean addToSpiel(String spiel) {
        return SpielerListe.add(name, spiel);
    }
    
    /**
     * Entfernt den Spieler von seinem aktuellen Spiel
     * @return true wenn das Entfernen des Spielers erfolgreich war
     * @see SpielerListe#remove
     */
    public boolean removeFromSpiel() {
        return SpielerListe.remove(name);
    }
    
    ////////
    //Getter
    ////////    
    
    /**
     * Gibt die Datenbank ID des Spielers zurück
     * @return Die Datenbank ID des Spielers
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gibt den Namen des Spielers zurück
     * @return Den Namen des Spielers
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gibt den Rang des Spielers zurück
     * @return Den Rang des Spielers
     */
    public Rang getRang() {
        return rang;
    }
        
    /**
     * Gibt den WriteChat, also den Chat,
     * in dem der Spieler schreibt, zurück
     * @return Den WriteChat des Spielers
     */
    public Chat getWriteChat() {
        return writeChat;
    }
    
    /**
     * Gibt die aktuelle Anzahl an Taunpoints,
     * die dieser Spieler besitzt zurück
     * @return Die Taunpoints des Spielers
     */
    public int getTaunpoints() {
        return totalTaunpoints;
    }
    
    /**
     * Gibt die Differenz der Taunpoints
     * des Spielers mit denen in der
     * Datenbank zurück
     * @return Die Taunpoints der Datenbank des Spielers
     */
    public int getDifferTaunpoints() {
        return differTaunpoints;
    }
    
    /**
     * Gibt die zum Spieler gehörende Player Variable zurück
     * @return Die zum Spieler gehörende Player Variable
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(name);
    }
    
    /**
     * Gibt das Spiel zurück, indem sich der Spieler befindet
     * @return Das Spiel, indem sich der Spieler befindet
     * @see SpielerListe#getSpiel
     */
    public String getSpiel() {
        return SpielerListe.getSpiel(name);
    }
}