package com.example.g2048.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.g2048.entities.DaoUser;
import com.example.g2048.entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class database extends RoomDatabase {

    public abstract DaoUser daoUser();
}
