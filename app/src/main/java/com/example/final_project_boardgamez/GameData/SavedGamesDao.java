package com.example.final_project_boardgamez.GameData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedGamesDao {
    @Insert
    void insert(Game game);

    @Delete
    void delete(Game game);

    @Query("SELECT * FROM games")
    LiveData<List<Game>> getAllSavedGames();

    @Query("SELECT * FROM games WHERE name = :gameName LIMIT 1")
    Game getGameByName(String gameName);

}
