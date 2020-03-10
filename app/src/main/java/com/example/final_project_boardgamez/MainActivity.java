package com.example.final_project_boardgamez;

import android.content.Intent;
import android.os.Bundle;

import com.example.final_project_boardgamez.GameData.GameInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements GameManagerAdapter.OnGameClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mMainGameListRV;
    private GameManagerAdapter mAdapterRV;                  // Changed to a custom adapter
    private RecyclerView.LayoutManager mLayoutManagerRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Created MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        GameInfo game = new GameInfo();
        game.name = "Settlers of Catan";
        game.description = "HARDCODED: In Catan (formerly The Settlers of Catan), players try to be the dominant force on the island of Catan by building settlements, cities, and roads.";
        game.age = 10;
        game.yearpublished = 1995;
        game.minplayers = 3;
        game.maxplayers = 4;
        game.playingtime = 60;
        mAdapterRV.addGame(game);

        Log.d(TAG, "Main: Adapter is size: " + mAdapterRV.getItemCount());



        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Adding a Game to the RV      //TODO: Needs to Store Permanently
                GameInfo game = new GameInfo();
                game.name = "Coup";
                game.description = "This is a random game description hardcoded in the Main Activity on Create";
                game.age = 5;
                game.yearpublished = 1998;
                game.minplayers = 2;
                game.maxplayers = 12;
                game.playingtime = 30;
                mAdapterRV.addGame(game);

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameClicked(GameInfo game) {          // Passes the whole game item
        Log.d(TAG, "Main: Recognized the game click");
        // Handles games being clicked on in the main activity
        // Brings the user to the detailed page
        Intent intent = new Intent(this, GameDetailedActivity.class);
        intent.putExtra(GameDetailedActivity.EXTRA_GAME_INFO, game);
        startActivity(intent);
    }
}
