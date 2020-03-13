package com.example.final_project_boardgamez.GameData;

import java.io.Serializable;

public class Game implements Serializable {
    public int year_published;
    public int min_players;
    public int max_players;
    public int min_playtime;
    public int max_playtime;
    public int min_age;

    public String name;
    public String description;
    public String image_url;
    public String game_tag;
}
