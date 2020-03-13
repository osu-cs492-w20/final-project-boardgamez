package com.example.final_project_boardgamez.GameData;

import android.util.Log;

import com.example.final_project_boardgamez.Utilitlies.BoardGameAtlas;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GamesRepository implements LoadGamesTask.LoadGamesTaskCallback {

    private static final String TAG = GamesRepository.class.getSimpleName();

    private MutableLiveData<List<Game>> mGames;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentSearch;

    public GamesRepository() {
        mGames = new MutableLiveData<>();
        mGames.setValue(null);

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

    public LiveData<List<Game>> getGames() {
        return mGames;
    }

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
}
