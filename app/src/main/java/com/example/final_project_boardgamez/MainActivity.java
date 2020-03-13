package com.example.final_project_boardgamez;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.final_project_boardgamez.GameData.Game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameManagerAdapter.OnGameClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mMainGameListRV;
    private GameManagerAdapter mAdapterRV;                  // Changed to a custom adapter
    private RecyclerView.LayoutManager mLayoutManagerRV;
    private TextView mAppliedFiltersTV;
    private String[] mFilterItems;
    boolean[] mCheckedFilters;
    ArrayList<Integer> mSelectedFilters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Created MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFilterItems = getResources().getStringArray(R.array.filter_list);
        mCheckedFilters = new boolean[mFilterItems.length];

        mAppliedFiltersTV = findViewById(R.id.tv_applied_filters);
        mAppliedFiltersTV.setVisibility(View.GONE);
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_add_sign);
*/
        mMainGameListRV = findViewById(R.id.rv_main);
        mMainGameListRV.setHasFixedSize(true);

        /* Setup layout manager */
        mLayoutManagerRV = new LinearLayoutManager(this);
        mMainGameListRV.setLayoutManager(mLayoutManagerRV);

        /* Setup adapter */
        mAdapterRV = new GameManagerAdapter(this);
        mMainGameListRV.setAdapter(mAdapterRV);


        // Adding a Game to the RV      //TODO: Just Hard coding for testings
        Game game = new Game();
        game.name = "Settlers of Catan";
        game.description = "HARDCODED: In Catan (formerly The Settlers of Catan), players try to be the dominant force on the island of Catan by building settlements, cities, and roads.";
        game.min_age = 10;
        game.year_published = 1995;
        game.min_players = 3;
        game.max_players = 4;
        game.min_playtime = 60;
        mAdapterRV.addGame(game);

        Log.d(TAG, "Main: Adapter is size: " + mAdapterRV.getItemCount());



        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);


                // Adding a Game to the RV      //TODO: Needs to Store Permanently
                /*Game game = new Game();
                game.name = "Coup";
                game.description = "This is a random game description hardcoded in the Main Activity on Create";
                game.min_age = 5;
                game.year_published = 1998;
                game.min_players = 2;
                game.max_players = 12;
                game.min_playtime = 30;
                mAdapterRV.addGame(game);*/

                //Log.d(TAG, "Main: Adapter is size: " + mAdapterRV.getItemCount());



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            onFilterSettingsClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameClicked(Game game) {          // Passes the whole game item
        Log.d(TAG, "Main: Recognized the game click");
        // Handles games being clicked on in the main activity
        // Brings the user to the detailed page
        Intent intent = new Intent(this, GameDetailedActivity.class);
        intent.putExtra(GameDetailedActivity.EXTRA_GAME_INFO, game);
        startActivity(intent);
    }

    private void onFilterSettingsClicked() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Filter by Tag");
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
                if (mSelectedFilters.size() > 0) {
                    for (int i = 0; i < mSelectedFilters.size(); i++) {
                        filterItem = filterItem + mFilterItems[mSelectedFilters.get(i)];
                        if (i != mSelectedFilters.size() - 1) {
                            filterItem = filterItem + ", ";
                        }
                        Log.d(TAG, filterItem);
                    }
                    mAppliedFiltersTV.setText("Tag filters: " + filterItem);
                    mAppliedFiltersTV.setVisibility(View.VISIBLE);
                    // Set text here
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
                    // Clear text view if any
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}
