package com.example.final_project_boardgamez.GameData;

import java.io.Serializable;

public class GameInfo implements Serializable {     //Hard Coded data for testing

    public int yearpublished = 2001;
    public int minplayers = 2;
    public int maxplayers = 10;
    public int playingtime = 25;
    public int age = 5;

    public String name = "Random Game Name";
    public String description = "This is a random game description hardcoded into the GameInfo class";


}
