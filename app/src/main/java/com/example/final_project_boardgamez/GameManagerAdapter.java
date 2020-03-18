package com.example.final_project_boardgamez;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_project_boardgamez.GameData.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManagerAdapter extends RecyclerView.Adapter<GameManagerAdapter.GameManagerViewHolder> {

    private static final String TAG = GameManagerAdapter.class.getSimpleName();
    private List<Game> mGameDetails;
    private OnGameClickListener mGameClickListener;

    public interface OnGameClickListener {
        void onGameClicked(Game game);
    }

    public GameManagerAdapter(OnGameClickListener listener) {
        mGameClickListener = listener;
        mGameDetails = new ArrayList<>();
        Log.d(TAG, "Adapter: Created an ArrayList");
    }

    public void addGame(Game game) {
        Log.d(TAG, "Adapter: Adding Game to ArrayList");
        mGameDetails.add(game);
        notifyDataSetChanged();
    }

    public List<Game> getGameList(){
        return mGameDetails;
    }

    public void updateGameCollection(List<Game> gameList) {
        Log.d(TAG, "Adapter: Updating whole ArrayList with another list");
        if(gameList != null){
            mGameDetails = gameList;
            notifyDataSetChanged();
        }else{
            Log.d(TAG, "Adapter: CAN'T UPDATE GAME LIST WITH NULL LIST.");
        }
    }

    @Override
    public int getItemCount() {
        return mGameDetails.size();
    }

    @Override
    public GameManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.game_list_item, parent, false);
        return new GameManagerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameManagerViewHolder holder, int position) {
        Game gameDetails = mGameDetails.get(position);
        String gameTag = "";
        if (gameDetails.game_tag != null) {
            gameTag = "Tags: " + gameDetails.game_tag;
        }
        Log.d(TAG, "Adapter: Bind Game Name = " + gameDetails.name);
        holder.bind(gameDetails, gameTag);
    }


    class GameManagerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mGameDetailsTV;
        private TextView mGameTitleTV;
        private ImageView mGameThumbnailIV;

        public GameManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameDetailsTV = itemView.findViewById(R.id.tv_game_item_details);
            mGameTitleTV = itemView.findViewById(R.id.tv_game_item_title);
            mGameThumbnailIV = itemView.findViewById(R.id.iv_game_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "In on click");
            Game game = mGameDetails.get(getAdapterPosition());
            mGameClickListener.onGameClicked(game);
        }

        void bind(Game game, String gameTag) {
            mGameDetailsTV.setText((game.description).trim());  // Remove any leading or trailing whitespace from game description
            mGameTitleTV.setText(game.name);
            if (gameTag != "") {
                mGameDetailsTV.setText(gameTag);
            }

            String thumbnail = game.image_url;
            Glide.with(mGameThumbnailIV.getContext())
                    .load(thumbnail).into(mGameThumbnailIV);
        }
    }
}
