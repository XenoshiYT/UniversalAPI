package de.xenodev.api.MySQL;

import de.xenodev.api.UniversalAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RewardAPI {

    public boolean playerExists(UUID uuid){

        try{
            ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Reward WHERE UUID= '" + uuid + "'");
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
            UniversalAPI.getMysql().update("INSERT INTO Reward(UUID, SECONDS) VALUES ('" + uuid + "', '" + UniversalAPI.getInstance().getConfig().getInt("Reward-Time") + "');");
        }
    }

    public Integer getSeconds(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Reward WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("SECONDS")) == null));
                i = rs.getInt("SECONDS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getSeconds(uuid);
        }

        return i;
    }

    public void setSeconds(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            UniversalAPI.getMysql().update("UPDATE Reward SET SECONDS= '" + seconds + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setSeconds(uuid, seconds);
        }
    }

    public void removeSeconds(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            setSeconds(uuid, Integer.valueOf(getSeconds(uuid).intValue() - seconds.intValue()));
        }else{
            createPlayer(uuid);
            removeSeconds(uuid, seconds);
        }
    }
}
