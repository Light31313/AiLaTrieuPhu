package com.example.whoismillionaireapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "HighScore"
)
public class HighScore implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    int id;
    @ColumnInfo(name = "Name")
    String name;
    @ColumnInfo(name = "Score")
    int score;

    public HighScore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
