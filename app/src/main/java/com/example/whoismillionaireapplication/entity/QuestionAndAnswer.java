package com.example.whoismillionaireapplication.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "Question"
)
public class QuestionAndAnswer implements Serializable {
    @ColumnInfo(name = "question")
    private String question;

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "casea")
    private String caseA;

    @ColumnInfo(name = "caseb")
    private String caseB;

    @ColumnInfo(name = "casec")
    private String caseC;

    @ColumnInfo(name = "cased")
    private String caseD;

    @ColumnInfo(name = "truecase")
    private int trueCase;

    public QuestionAndAnswer(){}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCaseA() {
        return caseA;
    }

    public void setCaseA(String caseA) {
        this.caseA = caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public void setCaseB(String caseB) {
        this.caseB = caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public void setCaseC(String caseC) {
        this.caseC = caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    public void setCaseD(String caseD) {
        this.caseD = caseD;
    }

    public int getTrueCase() {
        return trueCase;
    }

    public void setTrueCase(int trueCase) {
        this.trueCase = trueCase;
    }
}
