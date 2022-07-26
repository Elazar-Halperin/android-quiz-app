package com.example.quizapplication.Models;

public class AnswerModel {
    int id;
    String answer;
    boolean isCorrect;
    int questionId;

    public AnswerModel(int id, String answer, boolean isCorrect, int questionId) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
