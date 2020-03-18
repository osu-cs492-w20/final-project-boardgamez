package com.example.final_project_boardgamez.GameData;

import android.content.Context;
import android.os.AsyncTask;

import com.example.final_project_boardgamez.Utilitlies.BarcodeSpiderUtils;
import com.example.final_project_boardgamez.Utilitlies.Network;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LoadGameFromUPCTask extends AsyncTask<Void, Void, String> {
    private String mURL;
    private LoadGamesFromUPCTaskCallback mCallback;
    private Context mContext;

    public interface LoadGamesFromUPCTaskCallback {
        void onUPCLookupFinished(Context context, List<Game> game);
    }

    LoadGameFromUPCTask(String url, Context context, LoadGamesFromUPCTaskCallback callback) {
        mURL = url;
        mCallback = callback;
        mContext = context; // Context for handling success/error response
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
    protected void onPostExecute(String json) {
        List<Game> game = null;
        if (json != null) {
            game = BarcodeSpiderUtils.parseGameJSON(json);
        }
        mCallback.onUPCLookupFinished(mContext, game);
    }
}
