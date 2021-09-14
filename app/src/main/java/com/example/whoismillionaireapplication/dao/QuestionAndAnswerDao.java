package com.example.whoismillionaireapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whoismillionaireapplication.entity.QuestionAndAnswer;

import java.util.List;

@Dao
public interface QuestionAndAnswerDao {
    @Query("Select * from question")
    List<QuestionAndAnswer> getAll();

    @Query("Select * from question where _id = :id")
    QuestionAndAnswer getById(int id);

    @Query("Select * from question where question like :question")
    QuestionAndAnswer getByQuestion(String question);

    @Query("Select * from question where level = :level")
    List<QuestionAndAnswer> getByLevel(int level);

    @Insert
    void create(QuestionAndAnswer questionAndAnswer);

    @Update
    void update(QuestionAndAnswer questionAndAnswer);

    @Delete
    void delete(QuestionAndAnswer questionAndAnswer);

    @Query("Delete from question where _id = :id")
    void deleteById(int id);

}
