package com.example.final_project_boardgamez.GameData;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.final_project_boardgamez.GameDetailedActivity;
import com.example.final_project_boardgamez.SearchActivity;
import com.example.final_project_boardgamez.Utilitlies.BarcodeSpiderUtils;
import com.example.final_project_boardgamez.Utilitlies.BoardGameAtlas;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GamesRepository implements LoadGamesTask.LoadGamesTaskCallback, LoadGameFromUPCTask.LoadGamesFromUPCTaskCallback {

    private static final String TAG = GamesRepository.class.getSimpleName();

    private MutableLiveData<List<Game>> mGames;
    private MutableLiveData<List<Game>> mScannedGame;
    private MutableLiveData<Status> mLoadingStatus;
    private boolean lastcall;

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

    public void updateLastCall (boolean val) {
        lastcall = val;
    }

    public boolean getLastCall() {
        return lastcall;
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

    public void loadGameFromUPC(String upc, Context context) {
        String url = BarcodeSpiderUtils.buildUPCLookupURL(upc);
        mScannedGame.setValue(null);
        Log.d(TAG, "Looking up game details for upc");
        mLoadingStatus.setValue(Status.LOADING);
        new LoadGameFromUPCTask(url, context,this).execute();
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
    public void onUPCLookupFinished(Context context, List<Game> game) {  // Launch intent or error from here??
        mScannedGame.setValue(game);
        if (game != null && !game.isEmpty()) {
            mLoadingStatus.setValue(Status.SUCCESS);
            Intent intent = new Intent(context, GameDetailedActivity.class);
            intent.putExtra(GameDetailedActivity.EXTRA_GAME_INFO, game.get(0));
            context.startActivity(intent);
        } else {
            mLoadingStatus.setValue(Status.SUCCESS);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Error: Game not found");
            builder.setMessage("Please check your internet connection or try searching for the game by name.");
            builder.setNegativeButton("Dismiss", null);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            final AlertDialog alertDialog = builder.create();
            if(!alertDialog.isShowing()) {
                alertDialog.show();
            }
        }
    }
}
