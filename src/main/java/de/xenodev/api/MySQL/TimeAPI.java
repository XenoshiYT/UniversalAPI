package de.xenodev.api.MySQL;

import de.xenodev.api.UniversalAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TimeAPI {

    public boolean playerExists(UUID uuid){

        try{
            ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Time WHERE UUID= '" + uuid + "'");
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
            UniversalAPI.getMysql().update("INSERT INTO Time(UUID, HOURS, MINUTES, SECONDS) VALUES ('" + uuid + "', '0', '0', '0');");
        }
    }

    public Integer getHours(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Time WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("HOURS")) == null));
                i = rs.getInt("HOURS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getHours(uuid);
        }

        return i;
    }

    public Integer getMinutes(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Time WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("MINUTES")) == null));
                i = rs.getInt("MINUTES");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getMinutes(uuid);
        }

        return i;
    }

    public Integer getSeconds(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Time WHERE UUID= '" + uuid + "'");
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

    public void setHours(UUID uuid, Integer hours){
        if(playerExists(uuid)){
            UniversalAPI.getMysql().update("UPDATE Time SET HOURS= '" + hours + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setHours(uuid, hours);
        }
    }

    public void setMinutes(UUID uuid, Integer minutes){
        if(playerExists(uuid)){
            UniversalAPI.getMysql().update("UPDATE Time SET MINUTES= '" + minutes + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setMinutes(uuid, minutes);
        }
    }

    public void setSeconds(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            UniversalAPI.getMysql().update("UPDATE Time SET SECONDS= '" + seconds + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setSeconds(uuid, seconds);
        }
    }

    public void addHours(UUID uuid, Integer hours){
        if(playerExists(uuid)){
            setHours(uuid, Integer.valueOf(getHours(uuid).intValue() + hours.intValue()));
        }else{
            createPlayer(uuid);
            addHours(uuid, hours);
        }
    }

    public void addMinutes(UUID uuid, Integer minutes){
        if(playerExists(uuid)){
            setMinutes(uuid, Integer.valueOf(getMinutes(uuid).intValue() + minutes.intValue()));
        }else{
            createPlayer(uuid);
            addMinutes(uuid, minutes);
        }
    }

    public void addSeconds(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            setSeconds(uuid, Integer.valueOf(getSeconds(uuid).intValue() + seconds.intValue()));
        }else{
            createPlayer(uuid);
            addSeconds(uuid, seconds);
        }
    }

    public String changeTime(UUID uuid){
        if(getHours(uuid) != 0){
            return getHours(uuid) + "h";
        }else if(getMinutes(uuid) >= 0 && getHours(uuid) == 0){
            return getMinutes(uuid) + " min";
        }
        return "kein Eintrag!";
    }
}
