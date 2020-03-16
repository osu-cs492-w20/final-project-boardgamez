package com.example.final_project_boardgamez.GameData;

import android.util.Log;

import com.example.final_project_boardgamez.Utilitlies.BarcodeSpiderUtils;
import com.example.final_project_boardgamez.Utilitlies.BoardGameAtlas;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GamesRepository implements LoadGamesTask.LoadGamesTaskCallback, LoadGameFromUPCTask.LoadGamesFromUPCTaskCallback {

    private static final String TAG = GamesRepository.class.getSimpleName();

    private MutableLiveData<List<Game>> mGames;
    private MutableLiveData<List<Game>> mScannedGame;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentSearch;

    public GamesRepository() {
        mGames = new MutableLiveData<>();
        mGames.setValue(null);

        mScannedGame = new MutableLiveData<>();
        mScannedGame.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mCurrentSearch = null;
    }

    public void loadGames(String search) {
        if (true) {
            mCurrentSearch = search;
            mGames.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            String url = BoardGameAtlas.buildGameSearchURL(search);
            Log.d(TAG, "fetching game data with this url: " + url);
            new LoadGamesTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached game data");
        }
    }

    public void loadGameFromUPC(String upc) {
        String url = BarcodeSpiderUtils.buildUPCLookupURL(upc);
        mScannedGame.setValue(null);
        Log.d(TAG, "Looking up game details for upc");
        new LoadGameFromUPCTask(url, this).execute();
    }

    public LiveData<List<Game>> getGames() {
        return mGames;
    }

    public LiveData<List<Game>> getScannedGame() { return mScannedGame; }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onGamesLoadFinished(List<Game> games) {
        mGames.setValue(games);
        if (games != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    @Override
    public void onUPCLookupFinished(List<Game> game) {
        mScannedGame.setValue(game);
    }
}
