package com.example.final_project_boardgamez.GameData;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class GameDatabase extends RoomDatabase {
    public abstract SavedGamesDao savedGamesDao();
    private static volatile GameDatabase INSTANCE;

    static GameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GameDatabase.class,
                            "saved_games_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
