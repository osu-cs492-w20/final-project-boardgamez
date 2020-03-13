package com.example.final_project_boardgamez.GameData;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedGamesRepository {
    private static final String TAG = SavedGamesRepository.class.getSimpleName();
    private SavedGamesDao mSavedGamesDao;

    public SavedGamesRepository(Application application) {
        GameDatabase gameDatabase = GameDatabase.getDatabase(application);
        mSavedGamesDao = gameDatabase.savedGamesDao();
    }

    public void insertGame(Game game) {
        Log.d(TAG, "Inserting game...");
        new InsertAsyncTask(mSavedGamesDao).execute(game);
    }

    public void deleteGame(Game game) {
        Log.d(TAG, "Deleting game...");
        new DeleteAsyncTask(mSavedGamesDao).execute(game);
    }

    public Game getSavedGameByName(String name) {
        Game game = new Game();
        try {
            game = new GetGameByNameAsyncTask(mSavedGamesDao).execute(name).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    public LiveData<List<Game>> getAllSavedGames() {
        return mSavedGamesDao.getAllSavedGames();
    }

    private static class GetGameByNameAsyncTask extends AsyncTask<String, Void, Game> {
        private SavedGamesDao nAsyncTaskDao;
        GetGameByNameAsyncTask(SavedGamesDao savedGamesDao) {
            nAsyncTaskDao = savedGamesDao;
        }

        @Override
        protected Game doInBackground(String... strings) {
            return nAsyncTaskDao.getGameByName(strings[0]);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Game, Void, Void> {
        private SavedGamesDao mAsyncTaskDao;
        InsertAsyncTask(SavedGamesDao savedGamesDao) {
            mAsyncTaskDao = savedGamesDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            Log.d(TAG, "Do insert of game: " + games[0].name);
            mAsyncTaskDao.insert(games[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Game, Void, Void> {
        private SavedGamesDao mAsyncTaskDao;
        DeleteAsyncTask(SavedGamesDao savedGamesDao) {
            mAsyncTaskDao = savedGamesDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mAsyncTaskDao.delete(games[0]);
            return null;
        }
    }
}
