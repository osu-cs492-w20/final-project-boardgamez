package com.example.final_project_boardgamez.Utilitlies;

import android.net.Uri;

import com.example.final_project_boardgamez.GameData.Game;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BarcodeSpiderUtils {

    private static final String BS_BASE_URL = "https://api.barcodespider.com/v1/lookup";
    private final static String BS_TOKEN_QUERY_PARAM = "token";
    private final static String BS_UPC_QUERY_PARAM = "upc";
    private final static String BS_TOKEN = "f81deb6218e69ad0b97c";

    static class BSUPCLookupResults {
        BSItemAttributes item_attributes;
    }

    static class BSItemAttributes {
        String title;
        String upc;     // TODO: Add these attributes to Game class??
        String image;
        String publisher;
        String parent_category;
        String description;
    }

    public static String buildUPCLookupURL(String item_upc) {
        return Uri.parse(BS_BASE_URL).buildUpon()
                .appendQueryParameter(BS_TOKEN_QUERY_PARAM, BS_TOKEN)
                .appendQueryParameter(BS_UPC_QUERY_PARAM, item_upc)
                .build()
                .toString();
    }

    public static ArrayList<Game> parseGameJSON(String json) {
        Gson gson = new Gson();
        BSUPCLookupResults results = gson.fromJson(json, BSUPCLookupResults.class);
        if (results != null && results.item_attributes != null && results.item_attributes.parent_category.equals("Toys & Games")) {
            ArrayList<Game> gameList = new ArrayList<>();
            Game game = new Game();

            game.name = results.item_attributes.title;
            game.description = results.item_attributes.description;
            game.image_url = results.item_attributes.image;
            gameList.add(game);
            return gameList;
        } else {
            return null;
        }
    }
}
