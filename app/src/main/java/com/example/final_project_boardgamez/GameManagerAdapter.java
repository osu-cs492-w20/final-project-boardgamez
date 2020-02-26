package com.example.final_project_boardgamez;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManagerAdapter extends RecyclerView.Adapter<GameManagerAdapter.GameManagerViewHolder> {

    private ArrayList<String> mGameDetails;
    final String[] dummyGameData = {
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)",
            "Coup (2012)"
    };

    public GameManagerAdapter() {
        mGameDetails = new ArrayList<>(Arrays.asList(dummyGameData));
    }

    @Override
    public GameManagerAdapter.GameManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.game_list_item, parent, false);
        return new GameManagerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameManagerViewHolder holder, int position) {
        String gameDetails = mGameDetails.get(position);
        holder.mGameDetailsTV.setText(gameDetails);
        holder.mGameThumbnailIV.setImageResource(R.mipmap.ic_dummy_thumbnail);
        // holder.bind(gameDetails);
    }

    @Override
    public int getItemCount() {
        return dummyGameData.length;
    }

    class GameManagerViewHolder extends RecyclerView.ViewHolder {
        private TextView mGameDetailsTV;
        private ImageView mGameThumbnailIV;

        public GameManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameDetailsTV = itemView.findViewById(R.id.tv_game_details);
            mGameThumbnailIV = itemView.findViewById(R.id.iv_game_image);
        }

        void bind(String gameDetailsText) {
            mGameDetailsTV.setText(gameDetailsText);
        }
    }
}
