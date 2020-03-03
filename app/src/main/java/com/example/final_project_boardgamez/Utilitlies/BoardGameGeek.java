package com.example.final_project_boardgamez.Utilitlies;

import android.net.Uri;

import com.example.final_project_boardgamez.GameData.GameInfo;

import java.util.ArrayList;

public class BoardGameGeek {
    private final static String BoardGameGeek_SEARCH_BASE_URL = "http://www.boardgamegeek.com/xmlapi/";
    private final static String BoardGameGeek_SEARCH_QUERY_PARAM = "search";

    static class GitHubSearchResults {
        ArrayList<GameInfo> games;
    }

    public static String buildGitHubSearchURL(String userGame) {
        return Uri.parse(BoardGameGeek_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(BoardGameGeek_SEARCH_QUERY_PARAM, userGame)
                .build()
                .toString();
    }


    // TODO: Need Method to parse the XML files

}


// Example API Call
//http://www.boardgamegeek.com/xmlapi/search?search=Crossbows%20and%20Catapults