package com.example.myapplication.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class NotesDB extends RoomDatabase {
    public static final String DATABSE_NAME = "notesDb";
    private static NotesDB instance;

    public abstract NotesDao notesDao();

    public static NotesDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NotesDB.class, DATABSE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
}
