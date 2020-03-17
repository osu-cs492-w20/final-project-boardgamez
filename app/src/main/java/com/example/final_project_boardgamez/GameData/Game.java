package com.example.final_project_boardgamez.GameData;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "games")
public class Game implements Serializable {
    @PrimaryKey
    @NonNull
    public String name;

    public String description;
    public String image_url;
    public String game_tag;
//    public String game_tag1;
//    public String game_tag2;
//    public String game_tag3;

    public int year_published;
    public int min_players;
    public int max_players;
    public int min_playtime;
    public int max_playtime;
    public int min_age;

//    private boolean tag_owned;
//    private boolean tag_played;
//    private boolean tag_wishlist;
//
//    public boolean isTag_owned() {
//        return tag_owned;
//    }
//
//    public void setTag_owned(boolean tag_owned) {
//        this.tag_owned = tag_owned;
//    }
//
//    public boolean isTag_played() {
//        return tag_played;
//    }
//
//    public void setTag_played(boolean tag_played) {
//        this.tag_played = tag_played;
//    }
//
//    public boolean isTag_wishlist() {
//        return tag_wishlist;
//    }
//
//    public void setTag_wishlist(boolean tag_wishlist) {
//        this.tag_wishlist = tag_wishlist;
//    }
}
