package com.example.final_project_boardgamez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.final_project_boardgamez.GameData.Game;
import com.example.final_project_boardgamez.GameData.Status;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity implements GameManagerAdapter.OnGameClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private GamesViewModel mGamesViewModel;
    private GameManagerAdapter mGameAdapter;
    private RecyclerView mGamesRV;
    private TextView mLoadingError;
    private ProgressBar mLoadingIndicator;
    private EditText mSearchField;
    private Button mSearchButton;

    private List mSearchResultsList;
    private static final String SEARCH_RESULTS_KEY = "searchResults";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SearchActivity: CREATING... ");
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Discover Games");

        mGamesViewModel = new GamesViewModel();
        mGameAdapter = new GameManagerAdapter(this);
        mGamesRV = findViewById(R.id.rv_search);
        mSearchField = findViewById(R.id.et_search_field);
        mSearchButton = findViewById(R.id.btn_search_button);
        //mLoadingError = findViewById(R.id.tv_loading_error);
        //mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mGamesRV.setAdapter(mGameAdapter);
        mGamesRV.setLayoutManager(new LinearLayoutManager(this));
        mGamesRV.setHasFixedSize(true);

        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SEARCH_RESULTS_KEY)) {
            mSearchResultsList = (List)savedInstanceState.getSerializable(
                    SEARCH_RESULTS_KEY
            );
            if(mSearchResultsList != null){
                mGameAdapter.updateGameCollection(mSearchResultsList);
            }
        }

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = mSearchField.getText().toString();
                mGamesViewModel.loadGames(search);
                mGamesViewModel.getGames().observe(SearchActivity.this, new Observer<List<Game>>() {
                    @Override
                    public void onChanged(List<Game> games) {
                        if (games != null) {
                            mSearchResultsList = games;
                            Log.d(TAG, "First game in list: " + games.get(0).name);
                            mGameAdapter.updateGameCollection(games);
                        }
                    }
                });
            }
        });

       /* mGamesViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mLoadingError.setVisibility(View.INVISIBLE);
                    mGamesRV.setVisibility(View.VISIBLE);
                } else {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mGamesRV.setVisibility(View.INVISIBLE);
                    mLoadingError.setVisibility(View.VISIBLE);

                }
            }
        });*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "SearchActivity: STARTING... ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SearchActivity: RESUMING... ");
        if(mSearchResultsList != null){
            mGameAdapter.updateGameCollection(mSearchResultsList);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SearchActivity: STOPPING... ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SearchActivity: DESTROYING...");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");

        if (mSearchResultsList != null) {
            outState.putSerializable(SEARCH_RESULTS_KEY,
                    (Serializable) mSearchResultsList);
        }
    }


    @Override
    public void onGameClicked(Game game) {
        Intent intent = new Intent(this, GameDetailedActivity.class);
        intent.putExtra(GameDetailedActivity.EXTRA_GAME_INFO, game);
        startActivity(intent);
    }
}
