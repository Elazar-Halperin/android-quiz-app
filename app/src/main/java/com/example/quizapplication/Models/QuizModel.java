package com.example.quizapplication.Models;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class QuizModel {
    int id;
    String topic;
    String date;
    List<QuestionModel> questionModelList;
    public QuizModel(int id, String name, String date) {
        this.id = id;
        this.topic = name;
        this.date = date;
        this.questionModelList = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public List<QuestionModel> getQuestionModelList() {
        return questionModelList;
    }
    public void setQuestionModelList(List<QuestionModel> questionModelList) {
        this.questionModelList = questionModelList;
    }
}
