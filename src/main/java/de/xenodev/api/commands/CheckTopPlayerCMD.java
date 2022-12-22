package de.xenodev.api.commands;

import de.xenodev.api.UniversalAPI;
import de.xenodev.api.others.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckTopPlayerCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 0){
            ResultSet resultSet = UniversalAPI.getMysql().query("SELECT * FROM Coins ORDER BY COINS DESC LIMIT 3");
            Integer checkPlate = 0;

            sender.sendMessage("");
            sender.sendMessage("§8----------» §a§lTop 3 Aktive Spieler §8«----------");
            sender.sendMessage("");

            try{
                while(resultSet.next()){
                    checkPlate++;
                    Integer coins = resultSet.getInt("COINS");
                    String playerName = UUIDFetcher.getName(UUID.fromString(resultSet.getString("UUID")));

                    if(checkPlate == 1){
                        sender.sendMessage(ChatColor.of(new Color(255, 215, 0)) + "§l1. §a" + playerName.toUpperCase() + "§7: §e" + coins + " §7Coins");
                    }else if(checkPlate == 2){
                        sender.sendMessage(ChatColor.of(new Color(192, 192, 192)) + "§l2. §a" + playerName.toUpperCase() + "§7: §e" + coins + " §7Coins");
                    }else if (checkPlate == 3){
                        sender.sendMessage(ChatColor.of(new Color(205, 127, 50)) + "§l3. §a" + playerName.toUpperCase() + "§7: §e" + coins + " §7Coins");
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            sender.sendMessage("");
            sender.sendMessage("§8----------» §a§lTop 3 Aktive Spieler §8«----------");
            sender.sendMessage("");
        }else{
            sender.sendMessage("§cEs gibt keine weiteren Argumente!");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        return arrayList;
    }
}