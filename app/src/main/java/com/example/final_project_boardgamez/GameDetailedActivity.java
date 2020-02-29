package com.example.final_project_boardgamez;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_boardgamez.GameData.GameInfo;

public class GameDetailedActivity extends AppCompatActivity {
    public static final String EXTRA_GAME_INFO = "GameInfo";
    private GameInfo mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detailed_collection);
    }


}
