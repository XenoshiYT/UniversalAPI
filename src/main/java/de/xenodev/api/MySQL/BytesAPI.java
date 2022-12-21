package de.xenodev.api.MySQL;

import de.xenodev.api.UniversalAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BytesAPI {

    public boolean playerExists(UUID uuid){

        try{
            ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Bytes WHERE UUID= '" + uuid + "'");
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
            UniversalAPI.getMysql().update("INSERT INTO Bytes(UUID, BYTES) VALUES ('" + uuid + "', '0');");
        }
    }


    public Integer getBytes(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = UniversalAPI.getMysql().query("SELECT * FROM Bytes WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("BYTES")) == null));
                i = rs.getInt("BYTES");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getBytes(uuid);
        }

        return i;
    }

    public void setBytes(UUID uuid, Integer bytes){
        if(playerExists(uuid)){
            UniversalAPI.getMysql().update("UPDATE Bytes SET BYTES= '" + bytes + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setBytes(uuid, bytes);
        }
    }

    public void addBytes(UUID uuid, Integer bytes){
        if(playerExists(uuid)){
            setBytes(uuid, Integer.valueOf(getBytes(uuid).intValue() + bytes.intValue()));
        }else{
            createPlayer(uuid);
            addBytes(uuid, bytes);
        }
    }

    public void removeBytes(UUID uuid, Integer bytes){
        if(playerExists(uuid)){
            setBytes(uuid, Integer.valueOf(getBytes(uuid).intValue() - bytes.intValue()));
        }else{
            createPlayer(uuid);
            removeBytes(uuid, bytes);
        }
    }
}
