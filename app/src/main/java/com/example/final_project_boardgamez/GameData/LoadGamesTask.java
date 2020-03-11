package com.example.final_project_boardgamez.GameData;

import android.os.AsyncTask;

import com.example.final_project_boardgamez.Utilitlies.BoardGameAtlas;
import com.example.final_project_boardgamez.Utilitlies.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadGamesTask extends AsyncTask<Void, Void, String> {

    private String mURL;
    private LoadGamesTaskCallback mCallback;

    public interface LoadGamesTaskCallback {
        void onGamesLoadFinished(List<Game> games);
    }

    LoadGamesTask(String url, LoadGamesTaskCallback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String json = null;
        try {
            json = Network.doHttpGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<Game> games = null;
        if (s != null) {
            games = BoardGameAtlas.parseGameJSON(s);
        }
        mCallback.onGamesLoadFinished(games);
    }
}
