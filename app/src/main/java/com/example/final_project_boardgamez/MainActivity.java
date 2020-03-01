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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    //public void onGameClicked(GameInfo game) {                    // When we are ready for real data
    public void onGameClicked() {
        Log.d(TAG, "Recognized the click");
        // Handles games being clicked on in the main activity
        // Brings the user to the detailed page
        Intent intent = new Intent(this, GameDetailedActivity.class);
        //intent.putExtra(GameDetailedActivity.EXTRA_GAME_INFO, game);
        startActivity(intent);
    }
}
