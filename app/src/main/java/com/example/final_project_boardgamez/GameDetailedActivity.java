package com.example.final_project_boardgamez;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.final_project_boardgamez.GameData.Game;

import java.util.ArrayList;

public class GameDetailedActivity extends AppCompatActivity {
    public static final String EXTRA_GAME_INFO = "Game";
    private static final String TAG = GameDetailedActivity.class.getSimpleName();

    private SavedGamesViewModel mSavedGamesViewModel;
    private Game mGame;
    private Menu mMenu;
    private Toast mToast;
    private Button mTagsButton;

    private TextView mAppliedFiltersTV;
    private String[] mFilterItems;
    private boolean[] mCheckedFilters;
    ArrayList<Integer> mSelectedFilters = new ArrayList<>();

    private boolean mGameIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detailed_collection);
        Log.d(TAG, "Created GameDetailedActivity");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Game Details");

        mFilterItems = getResources().getStringArray(R.array.filter_list);
        mCheckedFilters = new boolean[mFilterItems.length];
        mAppliedFiltersTV = findViewById(R.id.tv_applied_filters_details);
        mAppliedFiltersTV.setVisibility(View.GONE);

        mToast = null;

        mSavedGamesViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedGamesViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GAME_INFO)) {

            mGame = (Game) intent.getSerializableExtra(EXTRA_GAME_INFO);

            TextView gameTitleTV = findViewById(R.id.tv_game_title);
            gameTitleTV.setText(mGame.name);

            ImageView mGameIV = findViewById(R.id.iv_game_image);
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
                    mTagsButton.setVisibility(View.VISIBLE);

                } else {
                    mGameIsSaved = false;
                }
            }
        });

        String text = "Active Tags: ";
        if(mGame.tag_owned){
            mAppliedFiltersTV.setVisibility(View.VISIBLE);
            Log.d(TAG, "Game is Owned");
            text += "Owned, ";
        }
        if(mGame.tag_wishlist){
            mAppliedFiltersTV.setVisibility(View.VISIBLE);
            Log.d(TAG, "Game is on Wishlist");
            text += "Wishlist, ";
        }
        if(mGame.tag_played){
            mAppliedFiltersTV.setVisibility(View.VISIBLE);
            Log.d(TAG, "Game is has been played");
            text += "Has Played";
        }

        mAppliedFiltersTV.setText(text);


        mTagsButton = findViewById(R.id.edit_tags_btn);
        mTagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditTagsClicked();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed, menu);
        mMenu = menu;

        // Show save button or delete button based on whether current game is already saved.
        if (mGameIsSaved) {
            mMenu.findItem(R.id.action_remove).setVisible(true);
            mTagsButton.setVisibility(View.VISIBLE);
        } else {
            mMenu.findItem(R.id.action_save).setVisible(true);
        }

        // Setup the click listener for the save button.
        mMenu.findItem(R.id.action_save).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked add button");
                if (mGame != null && !mGameIsSaved) {
                    mSavedGamesViewModel.insertSavedGame(mGame);
                    mGameIsSaved = !mGameIsSaved;
                    mMenu.findItem(R.id.action_save).setVisible(false);
                    mMenu.findItem(R.id.action_remove).setVisible(true);

                    if (mToast != null) {
                        mToast.cancel();
                    }

                    mToast = Toast.makeText(GameDetailedActivity.this, "Gamed saved to your collection!", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });

        // Setup the click listener for the remove button.
        mMenu.findItem(R.id.action_remove).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked remove button");
                if (mGame != null && mGameIsSaved) {

                    // Create a dialog box to check if the user is sure they want to remove the game.
                    new AlertDialog.Builder(GameDetailedActivity.this)
                            .setTitle("Remove Game")
                            .setMessage("Are you sure you want to remove this game from your collection?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mSavedGamesViewModel.deleteSavedGame(mGame);
                                    mGameIsSaved = !mGameIsSaved;
                                    mMenu.findItem(R.id.action_remove).setVisible(false);
                                    mMenu.findItem(R.id.action_save).setVisible(true);

                                    if (mToast != null) {
                                        mToast.cancel();
                                    }

                                    mToast = Toast.makeText(GameDetailedActivity.this, "Game removed from your collection!", Toast.LENGTH_SHORT);
                                    mToast.show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        mMenu.findItem(R.id.action_share).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG, "Clicked share button");
                if (mGame != null) {
                    String shareText = "Look at this game: " + mGame.name;

                    if(mGame.max_players == 0 || mGame.min_players == 0){
                        shareText += ",  Players: " + "N/A";
                    } else {
                        if(mGame.min_players == mGame.max_players){
                            shareText += ",  Players: " + mGame.min_players;
                        }else{
                            shareText +=",  Players: " + mGame.min_players + " - " + mGame.max_players;
                        }
                    }

                    if(mGame.max_playtime == 0 || mGame.min_playtime ==0){
                        shareText += ",  Playtime: " + "N/A";
                    } else{
                        if(mGame.min_playtime == mGame.max_playtime){
                            shareText += ",  Playtime: " + mGame.min_playtime + " mins";
                        }else{
                            shareText += ",  Playtime: " + mGame.min_playtime + " - " + mGame.max_playtime + " mins";
                        }
                    }

                    if(mGame.min_age == 0){
                        shareText += ",  Age: " + "N/A";
                    }else{
                        shareText += ",  Age: " + mGame.min_age + "+";
                    }


                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    shareIntent.setType("text/plain");

                    Intent chooserIntent = Intent.createChooser(shareIntent, null);
                    startActivity(chooserIntent);
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



    private void onEditTagsClicked() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameDetailedActivity.this);
        mBuilder.setTitle("Edit Tags");
        mBuilder.setMultiChoiceItems(mFilterItems, mCheckedFilters, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked) {
                    if (!mSelectedFilters.contains(position)){

                        mSelectedFilters.add(position);
                    }
                } else { // Filter was unchecked
                    if (mSelectedFilters.contains(position)) {
                        mSelectedFilters.remove(position);
                    }
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String filterItem = "";

                mGame.tag_owned = false;
                mGame.tag_played = false;
                mGame.tag_wishlist = false;

                String text = "Active Tags: ";

                if (mSelectedFilters.size() > 0) {
                    for (int i = 0; i < mSelectedFilters.size(); i++) {
                        filterItem = filterItem + mFilterItems[mSelectedFilters.get(i)];
                        if (i != mSelectedFilters.size() - 1) {
                            filterItem = filterItem + ", ";
                        }

                        String tag = mFilterItems[mSelectedFilters.get(i)];
                        switch(tag)
                        {
                            case "Owned":
                                Log.d(TAG, "Mark Owned as TRUE");
                                mGame.tag_owned = true;
                                break;
                            case "Wishlist":
                                Log.d(TAG, "Mark Wishlist as TRUE");
                                mGame.tag_wishlist = true;
                                break;
                            case "Has Played":
                                Log.d(TAG, "Mark HasPlayed as TRUE");
                                mGame.tag_played = true;
                                break;
                            default:
                                System.out.println("Do Nothing");
                        }
                    }

                    mSavedGamesViewModel.updateSavedGame(mGame);

                    if(mGame.tag_owned){
                        text += "Owned, ";
                    }
                    if(mGame.tag_wishlist){
                        text += "Wishlist, ";
                    }
                    if(mGame.tag_played){
                        text += "Has Played";
                    }

                    //mAppliedFiltersTV.setText("Active Tags: " + filterItem);
                    mAppliedFiltersTV.setText(text);
                    mAppliedFiltersTV.setVisibility(View.VISIBLE);
                } else {
                    mAppliedFiltersTV.setVisibility(View.GONE);
                }
            }
        });

        // TODO: Decide if we want a dismiss button
          /*  mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }); */

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < mCheckedFilters.length; i++) {  // Loop through checked items
                    mCheckedFilters[i] = false;
                    mSelectedFilters.clear();
                    mAppliedFiltersTV.setText("");
                    mAppliedFiltersTV.setVisibility(View.GONE);
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}
