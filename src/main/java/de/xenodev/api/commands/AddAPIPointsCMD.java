package de.xenodev.api.commands;

import de.xenodev.api.UniversalAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddAPIPointsCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(p.hasPermission("tmb.command.addapipoints")){
                p.sendMessage("§7UniversalAPI §8| §7Dir fehlt folgende Permission: §6" + "tmb.command.addapipoints");
                return true;
            }

            if(args.length == 1){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                p.sendMessage("§7UniversalAPI §8| §7Der Spieler §a" + target.getName() + " §7hat folgende Daten");
                p.sendMessage("§8» §7Bytes: §e" + UniversalAPI.getInstance().getBytesAPI().getBytes(target.getUniqueId()));
                p.sendMessage("§8» §7Coins: §e" + UniversalAPI.getInstance().getCoinAPI().getCoins(target.getUniqueId()));
                p.sendMessage("§8» §7Time: §e" + UniversalAPI.getInstance().getTimeAPI().changeTime(target.getUniqueId()));
            }else if(args.length == 4){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                Integer newAmount = null;
                try{
                    newAmount = Integer.valueOf(args[3]);
                }catch(NumberFormatException e1){
                    p.sendMessage("§7Die Anzahl ist inkorrekt! §8[ " + args[3] + " §8]");
                }

                if(args[1].equalsIgnoreCase("set")){
                    if(args[2].equalsIgnoreCase("bytes")){
                        Integer oldBytes = UniversalAPI.getInstance().getBytesAPI().getBytes(target.getUniqueId());
                        UniversalAPI.getInstance().getBytesAPI().setBytes(target.getUniqueId(), newAmount);
                        p.sendMessage("§7UniversalAPI §8| §a" + target.getName() + " §7Bytes geändert von §e" + oldBytes + " §7zu §6" + newAmount);
                    }else if(args[2].equalsIgnoreCase("coins")){
                        Integer oldCoins = UniversalAPI.getInstance().getCoinAPI().getCoins(target.getUniqueId());
                        UniversalAPI.getInstance().getCoinAPI().setCoins(target.getUniqueId(), newAmount);
                        p.sendMessage("§7UniversalAPI §8| §a" + target.getName() + " §7Coins geändert von §e" + oldCoins + " §7zu §6" + newAmount);
                    }else{
                        p.sendMessage("§cDiesen Datatypen gibt es nicht! §8[ " + args[2] + " §8]");
                    }
                }else if(args[1].equalsIgnoreCase("remove")){
                    if(args[2].equalsIgnoreCase("bytes")){
                        Integer oldBytes = UniversalAPI.getInstance().getBytesAPI().getBytes(target.getUniqueId());
                        UniversalAPI.getInstance().getBytesAPI().removeBytes(target.getUniqueId(), newAmount);
                        p.sendMessage("§7UniversalAPI §8| §a" + target.getName() + " §7Bytes geändert von §e" + oldBytes + " §7zu §6" + (oldBytes - newAmount));
                    }else if(args[2].equalsIgnoreCase("coins")){
                        Integer oldCoins = UniversalAPI.getInstance().getCoinAPI().getCoins(target.getUniqueId());
                        UniversalAPI.getInstance().getCoinAPI().removeCoins(target.getUniqueId(), newAmount);
                        p.sendMessage("§7UniversalAPI §8| §a" + target.getName() + " §7Coins geändert von §e" + oldCoins + " §7zu §6" + (oldCoins - newAmount));
                    }else{
                        p.sendMessage("§cDiesen Datatypen gibt es nicht! §8[ " + args[2] + " §8]");
                    }
                }else if(args[1].equalsIgnoreCase("add")){
                    if(args[2].equalsIgnoreCase("bytes")){
                        Integer oldBytes = UniversalAPI.getInstance().getBytesAPI().getBytes(target.getUniqueId());
                        UniversalAPI.getInstance().getBytesAPI().addBytes(target.getUniqueId(), newAmount);
                        p.sendMessage("§7UniversalAPI §8| §a" + target.getName() + " §7Bytes geändert von §e" + oldBytes + " §7zu §6" + (oldBytes + newAmount));
                    }else if(args[2].equalsIgnoreCase("coins")){
                        Integer oldCoins = UniversalAPI.getInstance().getCoinAPI().getCoins(target.getUniqueId());
                        UniversalAPI.getInstance().getCoinAPI().addCoins(target.getUniqueId(), newAmount);
                        p.sendMessage("§7UniversalAPI §8| §a" + target.getName() + " §7Coins geändert von §e" + oldCoins + " §7zu §6" + (oldCoins + newAmount));
                    }else{
                        p.sendMessage("§cDiesen Datatypen gibt es nicht! §8[ " + args[2] + " §8]");
                    }
                }else{
                    p.sendMessage("§cDieser Befehl exisitiert nicht! §8[ " + args[1] + " §8]");
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if(args.length == 1){
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
            return arrayList;
        }else if(args.length == 2){
            arrayList.add("set");
            arrayList.add("remove");
            arrayList.add("add");
            return arrayList;
        }else if(args.length == 3){
            arrayList.add("bytes");
            arrayList.add("coins");
            return arrayList;
        }else{
            return arrayList;
        }
    }
}
