package com.example.final_project_boardgamez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_boardgamez.GameData.Game;

public class GameDetailedActivity extends AppCompatActivity {
    public static final String EXTRA_GAME_INFO = "Game";
    private static final String TAG = GameDetailedActivity.class.getSimpleName();

    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detailed_collection);
        Log.d(TAG, "Created GameDetailedActivity");

        Button editButton = findViewById(R.id.btn_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Replace with an action...");
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GAME_INFO)) {
            mGame = (Game) intent.getSerializableExtra(EXTRA_GAME_INFO);

            TextView gameTitleTV = findViewById(R.id.tv_game_title);
            gameTitleTV.setText(mGame.name);

            TextView gameDescriptionTV = findViewById(R.id.tv_game_description);
            gameDescriptionTV.setText(mGame.description);

        }

    }


}
