package com.example.final_project_boardgamez.GameData;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "games_v2")
public class Game implements Serializable {
    @PrimaryKey
    @NonNull
    public String name;

    public String description;
    public String image_url;
    public String game_tag;

    public int year_published;
    public int min_players;
    public int max_players;
    public int min_playtime;
    public int max_playtime;
    public int min_age;

    public boolean tag_owned;
    public boolean tag_played;
    public boolean tag_wishlist;
}
