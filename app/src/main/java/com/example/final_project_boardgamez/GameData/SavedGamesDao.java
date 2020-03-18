package com.example.final_project_boardgamez.GameData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedGamesDao {
    @Insert
    void insert(Game game);

    @Delete
    void delete(Game game);

    @Update
    void update(Game game);

    @Query("SELECT * FROM games_v2")
    LiveData<List<Game>> getAllSavedGames();

    @Query("SELECT * FROM games_v2 WHERE name = :gameName LIMIT 1")
    LiveData<Game> getGameByName(String gameName);

}
