package de.xenodev.api.MySQL;

import de.xenodev.api.UniversalAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CoinAPI {

    public boolean playerExists(UUID uuid){

        try{
            ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
            if(rs.next()){
                return rs.getString("UUID") != null;
            }
            return false;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public void createPlayer(UUID uuid){

        if(!(playerExists(uuid))){
            UniversalAPI.getMysql().update("INSERT INTO Coins(UUID, COINS) VALUES ('" + uuid + "', '0');");
        }
    }


    public Integer getCoins(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("COINS")) == null));
                i = rs.getInt("COINS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getCoins(uuid);
        }

        return i;
    }

    public void setCoins(UUID uuid, Integer coins){
        if(playerExists(uuid)){
            UniversalAPI.getMysql().update("UPDATE Coins SET COINS= '" + coins + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setCoins(uuid, coins);
        }
    }

    public void addCoins(UUID uuid, Integer coins){
        if(playerExists(uuid)){
            setCoins(uuid, Integer.valueOf(getCoins(uuid).intValue() + coins.intValue()));
        }else{
            createPlayer(uuid);
            addCoins(uuid, coins);
        }
    }

    public void removeCoins(UUID uuid, Integer coins){
        if(playerExists(uuid)){
            setCoins(uuid, Integer.valueOf(getCoins(uuid).intValue() - coins.intValue()));
        }else{
            createPlayer(uuid);
            removeCoins(uuid, coins);
        }
    }
}
