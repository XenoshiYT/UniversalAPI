package de.xenodev.api;

import de.xenodev.api.MySQL.*;
import de.xenodev.api.commands.AddAPIPointsCMD;
import de.xenodev.api.commands.CheckTopPlayerCMD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class UniversalAPI extends JavaPlugin implements Listener {

    private static MySQL mysql;
    private static UniversalAPI instance;
    final TimeAPI timeAPI = new TimeAPI();
    final RewardAPI rewardAPI = new RewardAPI();
    final CoinAPI coinAPI = new CoinAPI();
    final BytesAPI bytesAPI = new BytesAPI();

    @Override
    public void onEnable() {
        instance = this;
        if(!new File("plugins/" + getName(), "config.yml").exists()){
            saveDefaultConfig();
        }
        ConnectMySQL();
        saveOnlineTime();
        if(getConfig().getBoolean("Reward-Boolean")) {
            addCoinsPerTime();
        }

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginCommand("addapipoints").setExecutor(new AddAPIPointsCMD());
        getServer().getPluginCommand("addapipoints").setTabCompleter(new AddAPIPointsCMD());
        getServer().getPluginCommand("top").setExecutor(new CheckTopPlayerCMD());
        getServer().getPluginCommand("top").setTabCompleter(new CheckTopPlayerCMD());
    }

    @Override
    public void onDisable() {
        mysql.close();
    }

    public static MySQL getMysql() {
        return mysql;
    }

    public static UniversalAPI getInstance() {
        return instance;
    }

    public BytesAPI getBytesAPI() {
        return bytesAPI;
    }

    public CoinAPI getCoinAPI() {
        return coinAPI;
    }

    public RewardAPI getRewardAPI() {
        return rewardAPI;
    }

    public TimeAPI getTimeAPI() {
        return timeAPI;
    }

    private void ConnectMySQL(){
        mysql = new MySQL(getConfig().getString("mysql.host"), getConfig().getString("mysql.database"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));
        mysql.update("CREATE TABLE IF NOT EXISTS Time(UUID VARCHAR(100),HOURS BIGINT,MINUTES INT,SECONDS INT)");
        mysql.update("CREATE TABLE IF NOT EXISTS Coins(UUID VARCHAR(100),COINS BIGINT)");
        mysql.update("CREATE TABLE IF NOT EXISTS Reward(UUID VARCHAR(100),SECONDS BIGINT)");
        mysql.update("CREATE TABLE IF NOT EXISTS BYTES(UUID VARCHAR(100),BYTES BIGINT)");
    }

    private void saveOnlineTime(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    timeAPI.addSeconds(all.getUniqueId(), 1);
                    if(timeAPI.getSeconds(all.getUniqueId()) == 60){
                        timeAPI.setSeconds(all.getUniqueId(), 0);
                        timeAPI.addMinutes(all.getUniqueId(), 1);
                    }
                    if(timeAPI.getMinutes(all.getUniqueId()) == 60){
                        timeAPI.setMinutes(all.getUniqueId(), 0);
                        timeAPI.addHours(all.getUniqueId(), 1);
                    }
                }
            }
        }, 0, 20L);
    }

    private void addCoinsPerTime(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    rewardAPI.removeSeconds(all.getUniqueId(), 1);
                    if(rewardAPI.getSeconds(all.getUniqueId()) == 0){
                        rewardAPI.setSeconds(all.getUniqueId(), getConfig().getInt("Reward-Time"));
                        coinAPI.addCoins(all.getUniqueId(), getConfig().getInt("Reward-Coins"));
                    }
                }
            }
        }, 0, 20L);
    }

    @EventHandler
    private void handlePlayerJoin(PlayerJoinEvent e){
        rewardAPI.createPlayer(e.getPlayer().getUniqueId());
        coinAPI.createPlayer(e.getPlayer().getUniqueId());
        timeAPI.createPlayer(e.getPlayer().getUniqueId());
        bytesAPI.createPlayer(e.getPlayer().getUniqueId());
    }
}
