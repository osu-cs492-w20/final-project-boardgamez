package com.example.final_project_boardgamez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private Menu mMenu;
    private boolean mGameIsSaved = false;
    private Game mGame;
    private ImageView mGameIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detailed_collection);
        Log.d(TAG, "Created GameDetailedActivity");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSavedGamesViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedGamesViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GAME_INFO)) {

            mGame = (Game) intent.getSerializableExtra(EXTRA_GAME_INFO);

            TextView gameTitleTV = findViewById(R.id.tv_game_title);
            gameTitleTV.setText(mGame.name);

            mGameIV = findViewById(R.id.iv_game_image);
            Glide.with(mGameIV.getContext()).load(mGame.image_url).into(mGameIV);

            TextView gamePlayersTV = findViewById(R.id.tv_game_players);
            if (mGame.min_players == 0 || mGame.max_players == 0) {
                gamePlayersTV.setText("Players: N/A");
            } else if (mGame.min_players == mGame.max_players) {
                gamePlayersTV.setText("Players: " + mGame.min_players);
            } else {
                gamePlayersTV.setText("Players: " + mGame.min_players + " - " + mGame.max_players);
            }

            TextView gameTimeTV = findViewById(R.id.tv_game_playtime);
            if (mGame.max_playtime == 0) {
                gameTimeTV.setText("Playtime: N/A");
            } else if (mGame.min_playtime == mGame.max_playtime) {
                gameTimeTV.setText("Playtime: " + mGame.min_playtime + " minutes");
            } else {
                gameTimeTV.setText("Playtime: " + mGame.min_playtime + " - " + mGame.max_playtime + " minutes");
            }

            TextView gameMinAgeTV = findViewById(R.id.tv_game_min_age);
            String minAge = mGame.min_age == 0 ? "N/A" : Integer.toString(mGame.min_age);
            gameMinAgeTV.setText("Minimum age: " + minAge);

            TextView gameYearPublishedTV = findViewById(R.id.tv_game_year_published);
            String yearPublished = mGame.year_published == 0 ? "N/A" : Integer.toString(mGame.year_published);
            gameYearPublishedTV.setText("Year published: " + yearPublished);

            TextView gameDescriptionTV = findViewById(R.id.tv_game_description);
            gameDescriptionTV.setText(mGame.description);

        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed, menu);
        mMenu = menu;
        if (mGameIsSaved) {
            mMenu.findItem(R.id.action_remove).setVisible(true);
        } else {
            mMenu.findItem(R.id.action_add).setVisible(true);
        }

        mMenu.findItem(R.id.action_remove).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG, "Clicked remove button");
                if (mGame != null) {
                    if (mGameIsSaved) {
                        mSavedGamesViewModel.deleteSavedGame(mGame);
                        mGameIsSaved = !mGameIsSaved;
                        mMenu.findItem(R.id.action_remove).setVisible(false);
                        mMenu.findItem(R.id.action_add).setVisible(true);
                    }
                }
                return true;
            }
        });

        mMenu.findItem(R.id.action_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG, "Clicked add button");
                if (mGame != null) {
                    if (!mGameIsSaved) {
                        mSavedGamesViewModel.insertSavedGame(mGame);
                        mGameIsSaved = !mGameIsSaved;
                        mMenu.findItem(R.id.action_add).setVisible(false);
                        mMenu.findItem(R.id.action_remove).setVisible(true);
                    }
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Return to previous activity
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
