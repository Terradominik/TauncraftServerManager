package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.PortManager;
import com.tauncraft.tauncraftservermanager.Rang;
import com.tauncraft.tauncraftservermanager.Restart;
import com.tauncraft.tauncraftservermanager.SpielerListe;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Verwaltet alle wichtigen Commands, welche für Leitungs-Team Mitglieder gedacht sind
 * 
 * @author Terradomninik | raffi287
 * @version 0.2
 */
public class AdministrationCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public AdministrationCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Wird beim ausführen eines Commands aufgerufen
     * 
     * @param sender Der Sender des Commands
     * @param cmd Das Command 
     * @param label Der Name des Commands 
     * @param args Die Parameter des Commands
     * @return Ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("taunsm.admin." + cmd.getName())
                || sender.hasPermission("taunsm.admin.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp()) {
            
            switch (cmd.getName()) {
                case "kick":
                    return kick(sender, args);
                case "ban":
                    return ban(sender, args);
                case "unban":
                    return unban(sender, args);
                case "restart":
                    return restart(args);
                case "warn":
                    return warn(sender, args);
                case "configset":
                    return configset(sender, args);
                case "group":
                    return group(sender, args);
                case "removeport":
                    return removeport(sender, args);
            }
            if (sender instanceof Player) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "invsee": 
                        return invsee(playersender, args);
                    case "end": 
                        return end(playersender, args);
                    case "day":
                        return day(playersender);
                    case "night":
                        return night(playersender);
                    case "seen":
                        return seen(playersender, args);
                    case "addport":
                        return addport(playersender, args);
                    case "hidefrom":
                        return hidefrom(playersender, args);
                }
            }
            plugin.send(sender, "Das Command wurde noch nicht implementiert");
        }
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Zeigt das Inventar eines Spielers
     */
    private boolean invsee(Player sender, String[] args) {
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        if (args.length == 0) return false;

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            sender.openInventory(target.getInventory());
            plugin.send(sender, "Du hast das Inventar von " + target.getName() + " geöffnet");
        } else {
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        }
        return true;
    }
    
    /**
     * Zeigt die Endertruhe eines Spielers
     */
    private boolean end(Player sender, String[] args) {
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        if (args.length == 0) {
            sender.openInventory(sender.getEnderChest());
            plugin.send(sender, "Du hast deine Enderchest geöffnet");
        }
        
        if (args.length >= 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                sender.openInventory(target.getEnderChest());
                plugin.send(sender, "Du hast die Enderchest von " + target.getDisplayName() + " geöffnet");
            }
            else{
                plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            }
        }
        return true;
    }

    /**
     * Setzt die Zeit in der Welt des Senders auf Tag
     */
    private boolean day(Player sender) {
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        sender.getWorld().setTime(300);
        plugin.send(sender, "Es wurde Tag");
        return true;
    }

    /**
     * Setzt die Zeit in der Welt des Senders auf Nacht
     */
    private boolean night(Player sender) {
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        sender.getWorld().setTime(14000);
        plugin.send(sender, "Es wurde Nacht");
        return true;
    }
    
    /**
     * Kickt einen Spieler vom Server
     */
    private boolean kick(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            args[0] = "";
            StringBuilder sb = new StringBuilder();
            for (String s : args) sb.append(s).append(" ");
            target.kickPlayer("Du wurdest gekickt: " + sb.toString());
            plugin.broadcast(target.getName() + " wurde gekickt: " + sb.toString());
        } else 
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        return true;
    }
    
     /**
     * Banned einen Spieler vom Server
     */
    private boolean ban(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        
        String name = args[0];
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");
            
        OfflinePlayer target = plugin.getServer().getPlayer(name);
        if (target != null)
            target.getPlayer().kickPlayer("Du wurdest gebanned:\n" + sb.toString() + "\nUm entbanned zu werden melde dich in unserem TS");
        else
            target = plugin.getServer().getOfflinePlayer(name);
        target.setBanned(true);
        plugin.broadcast(target.getName() + " wurde gebanned: " + sb.toString());
        //Ban Protokoll sender.getName() hat target.getName() gebanned. Grund: sb.toString()
        return true;
    }
    
     /**
     * Entbanned einen Spieler vom Server
     */
    private boolean unban(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        
        OfflinePlayer target = plugin.getServer().getPlayer(args[0]);
        if (target == null)
            target = plugin.getServer().getOfflinePlayer(args[0]);
        target.setBanned(false);
        plugin.broadcast(target.getName() + " wurde entbanned");
        //Unban Protokoll sender.getName() hat target.getName() entbanned
        return true;
    }
    
    /** 
     * Bereitet den Server auf einen Restart vor
     */
    private boolean restart(String[] args) {
        if (args.length == 0 && !Restart.getRestart()) {
            plugin.broadcast(ChatColor.DARK_RED + "Nach beenden der laufenden Runden Restart");
            Restart.setRestart();
        }
        else if (args[0].equalsIgnoreCase("stop")) {
            plugin.broadcast(ChatColor.DARK_RED + "Die Restart Sequenz wurde abgebrochen");
            Restart.stopRestart();
        }
        return true;
    }
    
    /**
     * Gibt das Datum zurück an dem der Spieler das letzte Mal Online war
     */
    private boolean seen(Player sender, String[] args){
        if(args.length == 0)return false;
        OfflinePlayer target = plugin.getServer().getPlayer(args[0]);
        Date d = new Date(target.getLastPlayed());
        plugin.send(sender, target.getName() + " hat das letzte Mal am " + d.toString() + " gespielt");
        return true;
    }

    /**
     * Verwarnt einen Spieler
     */
    private boolean warn(CommandSender sender, String[] args) {
        if(args.length == 0)return false;
        OfflinePlayer target = plugin.getServer().getPlayer(args[0]);
        if (target == null) target = plugin.getServer().getOfflinePlayer(args[0]);
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String arg : args) sb.append(arg + " ");
        plugin.broadcast(ChatColor.RED + "Verwarnung für " + target.getName() + " von " + sender.getName() + ", Grund:" + sb.toString());
        return true;
    }

    /**
     * Fügt einen neuen Port hinzu
     */
    private boolean addport(Player sender, String[] args) {
        if (args.length == 0) return false;
        if(PortManager.addPort(args[0], sender.getLocation())) plugin.send(sender, "Port " + args[0] + " wurde erfolgreich hinzugefügt");
        else plugin.send(sender, "Es trat ein Fehler beim Hinzufügen von " + args[0] + " auf");
        return true;
    }
    
    /**
     * Löscht einen Port
     */
    private boolean removeport(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        if(PortManager.removePort(args[0])) plugin.send(sender, "Port " + args[0] + " wurde erfolgreich gelöscht");
        else plugin.send(sender, "Es trat ein Fehler beim Löschen von " + args[0] + " auf");
        return true;
    }
    
    /**
     * Setzt einen Wert in die Config
     */
    private boolean configset(CommandSender sender, String[] args) {
        String path = args[0];
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");
        
        plugin.getConfig().set(path, sb.toString());
        plugin.saveConfig();
        return true;
    }

    /**
     * Setzt die Gruppe eines Spielers
     */
    private boolean group(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        Player spieler = plugin.getServer().getPlayer(args[0]);
        if (spieler == null) {
            plugin.send(sender, "Spieler " + args[0] + " konnte nicht gefunden werden");
            return true;
        }
        Rang targetrang = null;
        try {
            targetrang = Rang.valueOf(args[1]);
        } catch (IllegalArgumentException iae) {
            StringBuilder sb = new StringBuilder();
            for (Rang rang : Rang.values()) sb.append(rang.toString()).append(", ");
            plugin.send(sender, "Der eingegebene Rang existriert nicht. Folgende Ränge stehen zur Verfügung:");
            plugin.send(sender, sb.toString());
        }
        plugin.getCommand("pex").execute(sender, "pex", new String[]{"user",spieler.getName(),"group","set",targetrang.getPermName()});
        return true;
    }
    
    /**
     * Versteckt dich vor einem Spieler
     */
    private boolean hidefrom(Player sender, String[] args){
        if(args.length == 0){
            plugin.send(sender, "Bitte gib einen Namen ein");
            return true;
        }
        Player spieler = plugin.getServer().getPlayer(args[0]);
        if (spieler != null){
            if(spieler.canSee(sender) == true){
                spieler.hidePlayer(sender);
                plugin.send(sender, spieler.getName() + " sieht dich nun nicht mehr");
            } else {
                spieler.showPlayer(sender);
                plugin.send(sender, spieler.getName() + " sieht dich nun wieder");
            }
        } else {
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        }
        return true;
    }
}
