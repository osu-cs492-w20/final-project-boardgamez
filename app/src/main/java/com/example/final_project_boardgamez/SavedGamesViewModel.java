package com.example.final_project_boardgamez;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.final_project_boardgamez.GameData.Game;
import com.example.final_project_boardgamez.GameData.SavedGamesRepository;

import java.util.List;

public class SavedGamesViewModel extends AndroidViewModel {
    private SavedGamesRepository mSavedGamesRepository;

    public SavedGamesViewModel(Application application) {
        super(application);
        mSavedGamesRepository = new SavedGamesRepository(application);
    }

    public void insertSavedGame(Game game) {
        mSavedGamesRepository.insertGame(game);
    }

    public void deleteSavedGame(Game game) {
        mSavedGamesRepository.deleteGame(game);
    }

    public void updateSavedGame(Game game){
        mSavedGamesRepository.updateGame(game);
    }

//    public void setTagBool(String gameName, boolean bool){
//        mSavedGamesRepository.setTagBool(gameName, bool);
//    }

    public LiveData<List<Game>> getAllSavedGames() {
        return mSavedGamesRepository.getAllSavedGames();
    }

    public LiveData<Game> getSavedGameByName(String gameName) {
        return mSavedGamesRepository.getSavedGameByName(gameName);
    }
}
