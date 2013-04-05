package com.tauncraft.tauncraftservermanager;

import com.tauncraft.tauncraftservermanager.commands.AdministrationCommands;
import com.tauncraft.tauncraftservermanager.commands.ChatCommands;
import com.tauncraft.tauncraftservermanager.commands.ConfigCommands;
import com.tauncraft.tauncraftservermanager.commands.CreativeCommands;
import com.tauncraft.tauncraftservermanager.commands.FunCommands;
import com.tauncraft.tauncraftservermanager.commands.PlayerCommands;
import com.tauncraft.tauncraftservermanager.commands.PunishCommands;
import com.tauncraft.tauncraftservermanager.commands.TeleportCommands;
import com.tauncraft.tauncraftservermanager.listener.BlockListener;
import com.tauncraft.tauncraftservermanager.listener.ChatListener;
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
 * @author Terradominik | raffi287
 * @version 2012-02-22
 */
public class TauncraftServerManager extends JavaPlugin {

    //Manager
    private DatabaseManager databaseManager;
    
    //Listener
    private BlockListener blockListener;
    private ChatListener chatListener;
    private DispenseListener dispenseListener;
    private InventoryListener inventoryListener;
    private JoinListener joinListener;
    private QuitListener quitListener;
    
    //Commands
    private final AdministrationCommands ac = new AdministrationCommands(this);
    private final ChatCommands chc = new ChatCommands(this);
    private final ConfigCommands cfc = new ConfigCommands(this);
    private final CreativeCommands crc = new CreativeCommands(this);
    private final FunCommands fc = new FunCommands(this);
    private final PlayerCommands plc = new PlayerCommands(this);
    private final PunishCommands puc = new PunishCommands(this);
    private final TeleportCommands tpc = new TeleportCommands(this);
    
    private String broadcastFormat = ChatColor.DARK_AQUA + "";
    private String privateFormat = ChatColor.DARK_GRAY + "";
    
    private static Chat defaultWriteChat;
    private static Chat[] defaultChats;

    /**
     * Beim Enablen
     */
    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        this.saveDefaultConfig();
        
        //Manager
        databaseManager = new DatabaseManager(this);

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
        
        //Chat Commands
        this.getCommand("leitung").setExecutor(chc);
        this.getCommand("server").setExecutor(chc);
        this.getCommand("clearmsg").setExecutor(chc);
        this.getCommand("tell").setExecutor(chc);
        this.getCommand("chat").setExecutor(chc);

        //Config Commands
        this.getCommand("configset").setExecutor(cfc);
        
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

        blockListener = new BlockListener(this);
        joinListener = new JoinListener(this);
        quitListener = new QuitListener(this);
        chatListener = new ChatListener();
        
        //Listener Registration
        pm.registerEvents(this.blockListener, this);
        pm.registerEvents(this.chatListener, this);
        pm.registerEvents(this.dispenseListener, this);
        pm.registerEvents(this.inventoryListener, this);
        pm.registerEvents(this.joinListener, this);
        pm.registerEvents(this.quitListener, this);
        
        this.loadChats();
    }

    /**
     * Beim Disablen
     */
    @Override
    public void onDisable() {
        //Speichern der Config
        this.saveConfig();
    }

    /**
     * Broadcast Message
     */
    public void broadcast(String text) {
        this.getServer().broadcastMessage(broadcastFormat + text);
    }
    
    /**
     * Private Message
     */
    public void send(CommandSender spieler, String text) {
        spieler.sendMessage(privateFormat + text);
    }
    
    public static Chat[] getDefaultChats(Rang rang) {
        return defaultChats;
    }
    
    public static Chat getDefaultWriteChat(){
        return defaultWriteChat;
    }
    
    private void loadChats() {
         ConfigurationSection mainCs = this.getConfig().getConfigurationSection("chats");
         Set<Chat> defaultChats = new HashSet();
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
             if(subCs.getBoolean("default")) defaultChats.add(c);
             if(subCs.getBoolean("defaultWriteChat")) defaultWriteChat = c;
         }
         TauncraftServerManager.defaultChats = defaultChats.toArray(new Chat[0]);
    }
}