package com.example.final_project_boardgamez;

import android.content.Context;

import com.example.final_project_boardgamez.GameData.Game;
import com.example.final_project_boardgamez.GameData.GamesRepository;
import com.example.final_project_boardgamez.GameData.Status;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GamesViewModel extends ViewModel {

    private LiveData<List<Game>> mGames;
    private LiveData<List<Game>> mScannedGame;
    private LiveData<Status> mLoadingStatus;

    private GamesRepository mRepository;

    public GamesViewModel() {
        mRepository = new GamesRepository();
        mGames = mRepository.getGames();
        mScannedGame = mRepository.getScannedGame();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public void loadGames(String search) {
        mRepository.loadGames(search);
    }

    public void loadScannedGame(Context context, String upc) { mRepository.loadGameFromUPC(upc, context); }

    public LiveData<List<Game>> getGames() {
        return mGames;
    }

    public LiveData<List<Game>> getScannedGame() { return mScannedGame; }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
