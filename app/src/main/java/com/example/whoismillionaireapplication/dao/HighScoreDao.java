package com.example.whoismillionaireapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whoismillionaireapplication.entity.HighScore;

import java.util.List;

@Dao
public interface HighScoreDao {
    @Insert
    void create(HighScore highScore);

    @Update
    void update(HighScore highScore);

    @Delete
    void delete(HighScore highScore);

    @Query("Delete from HighScore where _id = :id")
    void deleteById(int id);
}
