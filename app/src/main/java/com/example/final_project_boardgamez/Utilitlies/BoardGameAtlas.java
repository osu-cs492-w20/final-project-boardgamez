package com.example.final_project_boardgamez.Utilitlies;

import android.net.Uri;

import com.example.final_project_boardgamez.GameData.Game;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BoardGameAtlas {
    private final static String BoardGameAtlas_SEARCH_BASE_URL = "https://www.boardgameatlas.com/api/search";
    private final static String BoardGameAtlas_SEARCH_QUERY_PARAM = "name";
    private final static String BoardGameAtlas_CLIENT_ID_PARAM = "client_id";
    private final static String BoardGameAtlas_CLIENT_ID_KEY = "0zG5JY1Gvx";

    static class GameSearchResults {
        GameSearchGame[] games;
    }

    static class GameSearchGame {
        String name;
        int year_published;
        int min_players;
        int max_players;
        int min_playtime;
        int max_playtime;
        int min_age;
        String description_preview;
        String image_url;
    }

    public static String buildGameSearchURL(String userGame) {
        return Uri.parse(BoardGameAtlas_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(BoardGameAtlas_SEARCH_QUERY_PARAM, userGame)
                .appendQueryParameter(BoardGameAtlas_CLIENT_ID_PARAM, BoardGameAtlas_CLIENT_ID_KEY)
                .build()
                .toString();
    }

    public static ArrayList<Game> parseGameJSON(String json) {
        Gson gson = new Gson();
        GameSearchResults results = gson.fromJson(json, GameSearchResults.class);
        if (results != null && results.games != null) {
            ArrayList<Game> games = new ArrayList<>();

            for (GameSearchGame currentGame : results.games) {
                Game newGame = new Game();

                newGame.min_age = currentGame.min_age;
                newGame.description = currentGame.description_preview;
                newGame.min_players = currentGame.min_players;
                newGame.max_players = currentGame.max_players;
                newGame.name = currentGame.name;
                newGame.min_playtime = currentGame.min_playtime;
                newGame.max_playtime = currentGame.max_playtime;
                newGame.image_url = currentGame.image_url;
                newGame.year_published = currentGame.year_published;

                games.add(newGame);
            }

            return games;
        } else {
            return null;
        }
    }

}
