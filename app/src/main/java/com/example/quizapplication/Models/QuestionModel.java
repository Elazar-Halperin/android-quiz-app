package com.example.quizapplication.Models;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
    int id;
    String question;
    int quizId;
    List<AnswerModel> answerModelList;

    public QuestionModel(int id, String question, int quizId) {
        this.id = id;
        this.question = question;
        this.answerModelList = new ArrayList<>();
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<AnswerModel> getAnswerModelList() {
        return answerModelList;
    }

    public void setAnswerModelList(List<AnswerModel> answerModelList) {
        this.answerModelList = answerModelList;
    }
}
