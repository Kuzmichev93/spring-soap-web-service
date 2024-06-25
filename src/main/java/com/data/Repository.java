package com.data;

import localhost._8080.Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Repository extends CofigurationBD implements RepositoryCRUD {
    public Repository(String url, String user, String pass) throws SQLException {
        super(url, user, pass);
    }

    private static final Logger logger = LoggerFactory.getLogger(Repository.class);
    int snils;
    public HashMap<Integer,Information> values = new HashMap<>();

    @Autowired
    Information information;
    @Override
    public Integer getID(int id) {
        try{
            PreparedStatement req = conn.prepareStatement("Select * from people where snils = ?");
            req.setInt(1,id);
            ResultSet s = req.executeQuery();

            while (s.next()){
                information.setName(s.getString("name_user"));
                information.setSurname(s.getString("surname"));
                information.setCity(s.getString("city"));
                information.setSnils(s.getInt("snils"));

                return 1;
            }

            return 0;

        }
        catch (SQLException e){
            logger.error("Error method getID",e);
        }

        return 0;
    }

    @Override
    public HashMap<Integer, Information> getAllpeople() {
        int key = 0;

        try {
            PreparedStatement req = conn.prepareStatement("Select * from people");
            ResultSet s = req.executeQuery();

            while (s.next()){
                Information inf = new Information();
                inf.setName(s.getString("name_user"));
                inf.setSurname(s.getString("surname"));
                inf.setCity(s.getString("city"));
                inf.setSnils(s.getInt("snils"));
                values.put(key,inf);
                key++;


            }

            return values;
        }
        catch (SQLException e){
            logger.error("Error method getAllpeople",e);
        }
        return null;
    }

    @Override
    public Integer save(String name, String surname, String city, int snils) {

        try {

            PreparedStatement req = conn.prepareStatement("insert into people(name_user,surname,city,snils) values(?,?,?,?)");
            req.setString(1,name);
            req.setString(2,surname);
            req.setString(3,city);
            req.setInt(4,snils);
            req.execute();
            return 1;


        }
        catch (SQLException e){
            logger.error("Error method save",e);
        }
        return 0;
    }


    @Override
    public Integer update(String name, String surname, String city, int snils) {
        try {
            if(getID(snils)==1){
                PreparedStatement req = conn.prepareStatement("update people set name_user = ?,surname = ?,city = ? where snils = ?");
                req.setString(1,name);
                req.setString(2,surname);
                req.setString(3,city);
                req.setInt(4,snils);
                req.execute();

                information.setName(name);
                information.setSurname(surname);
                information.setCity(city);
                information.setSnils(snils);
                return 1;
            }
            return 0;

        }
        catch (SQLException e){
            logger.error("Error method update",e);
        }
        return 0;
    }

    @Override
    public Integer delete(int id) {
        try{
            if(getID(id)==1){
                PreparedStatement req = conn.prepareStatement("delete from people where snils = ?");
                req.setInt(1,id);
                req.execute();
                return 1;
            }
            return 0;
        }
        catch(SQLException e){
            logger.error("Error method delete",e);
        }
        return 0;
    }
}
