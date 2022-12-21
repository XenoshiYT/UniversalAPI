package de.xenodev.api.MySQL;

import java.sql.*;

public class MySQL {

    private String host;
    private String database;
    private String user;
    private String password;
    private Connection con;

    public MySQL(String host, String database, String user, String password){
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;

        connect();
    }

    public void connect(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", user, password);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void close(){
        try{
            if(con != null){
                con.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void update(String qry){
        try{
            Statement st = con.createStatement();
            st.executeUpdate(qry);
            st.close();
        }catch(SQLException ex){
            connect();
            ex.printStackTrace();
        }
    }

    public ResultSet query(String qry){
        ResultSet rs = null;

        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        }catch(SQLException ex){
            connect();
            ex.printStackTrace();
        }

        return rs;
    }
}
