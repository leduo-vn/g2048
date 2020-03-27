package com.example.g2048.entities;

import androidx.room.Insert;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoUser {

    @Insert
    public void addUser(User user);

    @Query("select * from users WHERE username LIKE :name")
    public User getUserByName(String name);

    @Update
    public void updateUser(User user);

}
