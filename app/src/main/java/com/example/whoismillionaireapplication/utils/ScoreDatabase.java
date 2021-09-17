package com.example.whoismillionaireapplication.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.whoismillionaireapplication.dao.HighScoreDao;
import com.example.whoismillionaireapplication.entity.HighScore;

@Database(entities = {HighScore.class}, version = 1)
public abstract class ScoreDatabase extends RoomDatabase {
    private static ScoreDatabase scoreDatabase;

    public static ScoreDatabase getInstance(Context context) {
        if (scoreDatabase == null) {
            scoreDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ScoreDatabase.class,
                    "HighScore")
                    .allowMainThreadQueries().build();
        }
        return scoreDatabase;
    }

    public abstract HighScoreDao highScoreDao();
}
