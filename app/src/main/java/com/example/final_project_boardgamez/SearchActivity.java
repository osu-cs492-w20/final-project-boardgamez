package com.example.final_project_boardgamez;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.final_project_boardgamez.GameData.Game;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity implements GameManagerAdapter.OnGameClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private GamesViewModel mGamesViewModel;
    private GameManagerAdapter mGameAdapter;
    private RecyclerView mGamesRV;
    private EditText mSearchField;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mGamesViewModel = new GamesViewModel();
        mGameAdapter = new GameManagerAdapter(this);
        mGamesRV = findViewById(R.id.rv_search);
        mSearchField = findViewById(R.id.et_search_field);
        mSearchButton = findViewById(R.id.btn_search_button);

        mGamesRV.setAdapter(mGameAdapter);
        mGamesRV.setLayoutManager(new LinearLayoutManager(this));
        mGamesRV.setHasFixedSize(true);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = mSearchField.getText().toString();
                mGamesViewModel.loadGames(search);
                mGamesViewModel.getGames().observe(SearchActivity.this, new Observer<List<Game>>() {
                    @Override
                    public void onChanged(List<Game> games) {
                        if (games != null) {
                            Log.d(TAG, "first game in list: " + games.get(0).name);
                            mGameAdapter.updateGameCollection(games);
                        }
                    }
                });
            }
        });


    }

    @Override
    public void onGameClicked(Game game) {

    }
}
