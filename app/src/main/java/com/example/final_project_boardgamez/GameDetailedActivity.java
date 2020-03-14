package com.example.final_project_boardgamez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.final_project_boardgamez.GameData.Game;

public class GameDetailedActivity extends AppCompatActivity {
    public static final String EXTRA_GAME_INFO = "Game";
    private static final String TAG = GameDetailedActivity.class.getSimpleName();
    private SavedGamesViewModel mSavedGamesViewModel;
    private boolean mGameIsSaved = false;
    private Game mGame;
    private ImageView mGameIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detailed_collection);
        Log.d(TAG, "Created GameDetailedActivity");

        mSavedGamesViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedGamesViewModel.class);

        Button editButton = findViewById(R.id.btn_edit);
        Button addButton = findViewById(R.id.btn_add);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GAME_INFO)) {

            mGame = (Game) intent.getSerializableExtra(EXTRA_GAME_INFO);

            TextView gameTitleTV = findViewById(R.id.tv_game_title);
            gameTitleTV.setText(mGame.name);

            mGameIV = findViewById(R.id.iv_game_image);
            Glide.with(mGameIV.getContext()).load(mGame.image_url).into(mGameIV);

            TextView gamePlayersTV = findViewById(R.id.tv_game_players);
            gamePlayersTV.setText("Players: " + mGame.min_players + " - " + mGame.max_players);

            TextView gameTimeTV = findViewById(R.id.tv_game_playtime);
            gameTimeTV.setText("Playtime: " + mGame.min_playtime + " - " + mGame.max_playtime + " minutes");

            TextView gameMinAgeTV = findViewById(R.id.tv_game_min_age);
            gameMinAgeTV.setText("Minimum age: " + mGame.min_age);

            TextView gameYearPublishedTV = findViewById(R.id.tv_game_year_published);
            gameYearPublishedTV.setText("Year published: " + mGame.year_published);

            TextView gameDescriptionTV = findViewById(R.id.tv_game_description);
            gameDescriptionTV.setText(mGame.description);

        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Replace with an action...");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked add button");
                if (mGame != null) {
                    if (!mGameIsSaved) {
                        mSavedGamesViewModel.insertSavedGame(mGame);
                    }
                }
            }
        });

        mSavedGamesViewModel.getSavedGameByName(mGame.name).observe(this, new Observer<Game>() {
            @Override
            public void onChanged(Game game) {
                if (game != null) {
                    mGameIsSaved = true;
                } else {
                    mGameIsSaved = false;
                }
            }
        });

    }


}
