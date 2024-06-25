package com.data;

import localhost._8080.Information;

import java.util.HashMap;

public interface RepositoryCRUD {
    Integer getID(int id);
    HashMap<Integer, Information> getAllpeople();
    Integer save(String name,String surname,String city,int snils);
    Integer update(String name, String surname, String city, int snils);
    Integer  delete(int id);
}
