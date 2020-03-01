package com.example.final_project_boardgamez;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_boardgamez.GameData.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManagerAdapter extends RecyclerView.Adapter<GameManagerAdapter.GameManagerViewHolder> {

    private ArrayList<String> mGameDetails;
    private ArrayList<String> mGameTags;
    //private ArrayList<GameInfo> mGameDetails;           // When we are ready for real data
    private OnGameClickListener mGameClickListener;

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

    final String[] dummyTagData = {     // TODO: Make 2D array to store tags
            "Owns, Family friendly",
            "Wishlist, Party game",
            "Owns",
            "Owns",
            "Has played, Family friendly",
            "Wishlist",
            "Has played",
            "Owns",
            "Owns"
    };

    interface OnGameClickListener {
        //void onGameClicked(GameInfo game);
        void onGameClicked();
    }

    public GameManagerAdapter(OnGameClickListener listener) {
        mGameClickListener = listener;
        mGameDetails = new ArrayList<>(Arrays.asList(dummyGameData));
        mGameTags = new ArrayList<>(Arrays.asList(dummyTagData));
    }

//    public void updateGameCollection(ArrayList<GameInfo> gameList) {          // When we are ready for real data
//        mGameDetails = gameList;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return dummyGameData.length;
    }

    @Override
    public GameManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.game_list_item, parent, false);
        return new GameManagerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameManagerViewHolder holder, int position) {
        String gameDetails = mGameDetails.get(position);
        String gameTag = "Tags: " + mGameTags.get(position);
        holder.mGameDetailsTV.setText(gameDetails);
        holder.mGameTagTV.setText(gameTag);
        holder.mGameThumbnailIV.setImageResource(R.mipmap.ic_dummy_thumbnail);
        // holder.bind(gameDetails);
    }





    class GameManagerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {     // When we are ready for real data
    //class GameManagerViewHolder extends RecyclerView.ViewHolder {
        private TextView mGameDetailsTV;
        private TextView mGameTagTV;
        private ImageView mGameThumbnailIV;

        public GameManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameDetailsTV = itemView.findViewById(R.id.tv_game_details);
            mGameTagTV = itemView.findViewById(R.id.tv_game_tag);
            mGameThumbnailIV = itemView.findViewById(R.id.iv_game_image);
        }

        @Override
        public void onClick(View v) {
            //GameInfo game = mGameDetails.get(getAdapterPosition());         // When we are ready for real data
           // mGameClickListener.onGameClicked(game);
            mGameClickListener.onGameClicked();
        }

        void bind(String gameDetailsText) {
            mGameDetailsTV.setText(gameDetailsText);
        }
    }
}
