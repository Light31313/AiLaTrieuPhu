package com.example.whoismillionaireapplication.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.whoismillionaireapplication.dao.HighScoreDao;
import com.example.whoismillionaireapplication.dao.QuestionAndAnswerDao;
import com.example.whoismillionaireapplication.entity.HighScore;
import com.example.whoismillionaireapplication.entity.QuestionAndAnswer;

@Database(entities = {QuestionAndAnswer.class, HighScore.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    /*public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "WhoIsMillionaire.db")
                    .allowMainThreadQueries().build();
        }
        return appDatabase;
    }*/


    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "WhoIsMillionaire.db").
                    createFromAsset("Question.db").
                    fallbackToDestructiveMigration().
                    allowMainThreadQueries().build();
        }
        return appDatabase;
    }

    public abstract QuestionAndAnswerDao questionAndAnswerDao();
    public abstract HighScoreDao highScoreDao();
}
