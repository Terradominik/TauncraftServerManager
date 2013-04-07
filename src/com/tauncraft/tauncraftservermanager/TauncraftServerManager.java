package com.tauncraft.tauncraftservermanager;

import com.tauncraft.tauncraftservermanager.commands.AdministrationCommands;
import com.tauncraft.tauncraftservermanager.commands.ChatCommands;
import com.tauncraft.tauncraftservermanager.commands.CreativeCommands;
import com.tauncraft.tauncraftservermanager.commands.FunCommands;
import com.tauncraft.tauncraftservermanager.commands.PlayerCommands;
import com.tauncraft.tauncraftservermanager.commands.PunishCommands;
import com.tauncraft.tauncraftservermanager.commands.TeleportCommands;
import com.tauncraft.tauncraftservermanager.listener.BlockListener;
import com.tauncraft.tauncraftservermanager.listener.ChatListener;
import com.tauncraft.tauncraftservermanager.listener.CommandListener;
import com.tauncraft.tauncraftservermanager.listener.DispenseListener;
import com.tauncraft.tauncraftservermanager.listener.InventoryListener;
import com.tauncraft.tauncraftservermanager.listener.JoinListener;
import com.tauncraft.tauncraftservermanager.listener.QuitListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TauncraftServerManager - Hauptklasse
 * 
 * Der TauncraftServerManager ist ein Bukkit Plugin,
 * welches eine Ground API für alle Tauncraft Plugins
 * darstellt. Zusätzlich bietet ermöglicht es viele
 * Commands, die unser Server benötigt.
 *
 * @author Terradominik | raffi287
 * @version 0.2
 */
public class TauncraftServerManager extends JavaPlugin {

    //Manager
    private DatabaseManager databaseManager;
    private PortManager portManager;
    private SpielerListe spielerListe;
    
    //Commands
    private final AdministrationCommands ac = new AdministrationCommands(this);
    private final ChatCommands chc = new ChatCommands(this);
    private final CreativeCommands crc = new CreativeCommands(this);
    private final FunCommands fc = new FunCommands(this);
    private final PlayerCommands plc = new PlayerCommands(this);
    private final PunishCommands puc = new PunishCommands(this);
    private final TeleportCommands tpc = new TeleportCommands(this);
    
    //Chat Formate
    private String broadcastFormat = ChatColor.DARK_AQUA + "";
    private String privateFormat = ChatColor.DARK_GRAY + "";
    
    //Chats
    private static Chat defaultWriteChat;
    private static Chat[] defaultChats;

    /**
     * Beim Enablen des Plugins
     */
    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        this.saveDefaultConfig();
        
        //Manager
        databaseManager = new DatabaseManager(this);
        portManager = new PortManager(this);
        spielerListe = new SpielerListe();

        //Command Registration
        
        //Administration Commands
        this.getCommand("invsee").setExecutor(ac);
        this.getCommand("end").setExecutor(ac);
        this.getCommand("day").setExecutor(ac);
        this.getCommand("night").setExecutor(ac);
        this.getCommand("kick").setExecutor(ac);
        this.getCommand("ban").setExecutor(ac);
        this.getCommand("unban").setExecutor(ac);
        this.getCommand("restart").setExecutor(ac);
        this.getCommand("seen").setExecutor(ac);
        this.getCommand("warn").setExecutor(ac);
        this.getCommand("addport").setExecutor(ac);
        this.getCommand("removeport").setExecutor(ac);
        this.getCommand("configset").setExecutor(ac);
        this.getCommand("group").setExecutor(ac);
        
        //Chat Commands
        this.getCommand("leitung").setExecutor(chc);
        this.getCommand("server").setExecutor(chc);
        this.getCommand("clearmsg").setExecutor(chc);
        this.getCommand("tell").setExecutor(chc);
        this.getCommand("chat").setExecutor(chc);
        
        //Creative Commands
        this.getCommand("tc").setExecutor(crc);
        this.getCommand("gm").setExecutor(crc);
        
        //Fun Commands
        this.getCommand("blockhead").setExecutor(fc);
        this.getCommand("effect").setExecutor(fc);
        this.getCommand("head").setExecutor(fc);
        
        //Player Commands
        this.getCommand("profile").setExecutor(plc);
        this.getCommand("top").setExecutor(plc);

        //Punish
        this.getCommand("detonate").setExecutor(puc);
        this.getCommand("lightning").setExecutor(puc);
        this.getCommand("spiderstorm").setExecutor(puc);

        //Teleport Commands
        this.getCommand("tp").setExecutor(tpc);
        this.getCommand("s").setExecutor(tpc);
        this.getCommand("port").setExecutor(tpc);
        this.getCommand("portlist").setExecutor(tpc);
        this.getCommand("tploc").setExecutor(tpc);
        
        //Listener Registration
        pm.registerEvents(new BlockListener(this), this);
        pm.registerEvents(new ChatListener(this), this);
        pm.registerEvents(new CommandListener(this), this);
        pm.registerEvents(new DispenseListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new JoinListener(this), this);
        pm.registerEvents(new QuitListener(this), this);
        
        //Ladet die Chats
        this.loadChats();
    }

    /**
     * Beim Disablen des Plugins
     */
    @Override
    public void onDisable() {
        //Schließt die Connection
        DatabaseManager.closeConnection();
        //Speichern der Config
        this.saveConfig();
    }

    /**
     * Sendet eine Nachricht an alle Spieler
     * 
     * @param text Der Text, welcher gesendet werden soll
     */
    public void broadcast(String text) {
        this.getServer().broadcastMessage(broadcastFormat + text);
    }
    
    /**
     * Sendet eine Nachricht an einen Spieler
     * 
     * @param sender Der CommandSender an den die Nachricht geschickt werden solll
     * @param text Der Text, welcher gesendet werden soll
     */
    public void send(CommandSender sender, String text) {
        sender.sendMessage(privateFormat + text);
    }
    
    /**
     * Ladet alle Chats aus der Config
     */
    private void loadChats() {
         ConfigurationSection mainCs = this.getConfig().getConfigurationSection("chats");
         Set<Chat> defaultChatSet = new HashSet();
         for (String subString : mainCs.getKeys(false)) {
             ConfigurationSection subCs = mainCs.getConfigurationSection(subString);
             String name = subString;
             String prefix = subCs.getString("prefix");
             if (prefix == null) prefix = "";
             String suffix = subCs.getString("suffix");
             if (suffix == null) suffix = "";
             List<String> stringRaenge = subCs.getStringList("raenge");
             Set<Rang> raenge = new HashSet();
             for (String stringRang : stringRaenge) {
                 Rang rang = Rang.valueOf(stringRang);
                 raenge.add(rang);
             }
             Chat c = new Chat(name,prefix,suffix,raenge);
             if(subCs.getBoolean("default")) defaultChatSet.add(c);
             if(subCs.getBoolean("defaultWriteChat")) defaultWriteChat = c;
         }
         TauncraftServerManager.defaultChats = defaultChatSet.toArray(new Chat[0]);
    }
    
    //Getter
    public static Chat[] getDefaultChats() {
        return defaultChats;
    }
    
    public static Chat getDefaultWriteChat(){
        return defaultWriteChat;
    }
}