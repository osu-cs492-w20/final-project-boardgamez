package com.example.final_project_boardgamez;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project_boardgamez.GameData.Game;
import com.example.final_project_boardgamez.GameData.Status;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity implements GameManagerAdapter.OnGameClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 0;

    private GamesViewModel mGamesViewModel;
    private GameManagerAdapter mGameAdapter;
    private RecyclerView mGamesRV;
    private TextView mLoadingError;
    private TextView mNoResults;
    private ProgressBar mLoadingIndicator;
    private EditText mSearchField;
    private Button mSearchButton;
    private ImageButton mScanBarcode;

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
        mScanBarcode = findViewById(R.id.ib_scan_barcode);
        // mSearchButton = findViewById(R.id.btn_search_button);
        mNoResults = findViewById(R.id.tv_no_results);
        mLoadingError = findViewById(R.id.tv_loading_error);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mNoResults.setTypeface(null, Typeface.ITALIC);

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

        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchField.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(mSearchField.getWindowToken(), 0);
                    String search = mSearchField.getText().toString();
                    mGamesViewModel.loadGames(search);
                    mGamesViewModel.getGames().observe(SearchActivity.this, new Observer<List<Game>>() {
                        @Override
                        public void onChanged(List<Game> games) {
                            if (games != null) {
                                mSearchResultsList = games;
                               // Log.d(TAG, "First game in list: " + games.get(0).name);
                                mGameAdapter.updateGameCollection(games);
                                if (games.isEmpty()){
                                    mNoResults.setVisibility(View.VISIBLE);
                                } else {
                                    mNoResults.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    });
                }
                return false;
            }
        });

        mScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BarcodeReaderActivity.getLaunchIntent(SearchActivity.this, true, false);
                startActivityForResult(intent, BARCODE_READER_ACTIVITY_REQUEST);
            }
        });

        mGamesViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    mGamesRV.setVisibility(View.INVISIBLE);
                    mLoadingError.setVisibility(View.INVISIBLE);
                    mNoResults.setVisibility(View.INVISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mLoadingError.setVisibility(View.INVISIBLE);
                    mGamesRV.setVisibility(View.VISIBLE);
                } else {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mGamesRV.setVisibility(View.INVISIBLE);
                    mLoadingError.setVisibility(View.VISIBLE);
                    mNoResults.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            //mGamesViewModel.loadScannedGame(SearchActivity.this, "722301926246");  // TODO: HARDCODED UPC for testing API on emulator
            return;
        }
        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);  // Possibly pass in "barcode"?
            mGamesViewModel.loadScannedGame(SearchActivity.this, barcode.rawValue);
        }
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
