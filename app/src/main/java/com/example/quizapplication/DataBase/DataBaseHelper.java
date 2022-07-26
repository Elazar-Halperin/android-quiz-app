package com.example.quizapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizapplication.Models.AdminModel;
import com.example.quizapplication.Models.AnswerModel;
import com.example.quizapplication.Models.QuestionModel;
import com.example.quizapplication.Models.QuizModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME_QUIZ = "quiz";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOPIC = "topic";
    public static final String TABLE_NAME_ANSWER = "answer";
    public static final String COLUMN_ANSWER_STRING = TABLE_NAME_ANSWER + "String";
    public static final String COLUMN_IS_CORRECT = "isCorrect";
    public static final String TABLE_NAME_QUESTION = "question";
    public static final String COLUMN_QUESTION_ID = TABLE_NAME_QUESTION + "ID";
    public static final String COLUMN_QUIZ_ID = "quizID";
    public static final String COLUMN_QUESTION_STRING = TABLE_NAME_QUESTION + "String";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SCORE = "score";
    public static final String TABLE_NAME_ADMIN = "admin";
    public static final String COLUMN_TIME = "time";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "QuizApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String admin_table = "CREATE TABLE " + TABLE_NAME_ADMIN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT NOT NULL,"
                + COLUMN_PASSWORD + " TEXT NOT NULL)";

        String table1 = "CREATE TABLE " + TABLE_NAME_QUIZ + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TOPIC + " TEXT NOT NULL, "
                + COLUMN_DATE + " TEXT NOT NULL,"
                + COLUMN_SCORE + " REAL,"
                + COLUMN_TIME + " TEXT DEFAULT '5:00' NOT NULL)";

        String table2 = "CREATE TABLE " + TABLE_NAME_QUESTION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUESTION_STRING + " TEXT NOT NULL, "
                + COLUMN_QUIZ_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_QUIZ_ID + ") REFERENCES quiz(" + COLUMN_ID + "))";

        String table3 = "CREATE TABLE " + TABLE_NAME_ANSWER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ANSWER_STRING + " TEXT NOT NULL, "
                + COLUMN_IS_CORRECT + " BOOL NOT NULL, "
                + COLUMN_QUESTION_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_QUESTION_ID + ") REFERENCES " + TABLE_NAME_QUESTION + "(" + COLUMN_ID + "))";

        db.execSQL(admin_table);
        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertAdmin(AdminModel adminModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, adminModel.getUsername());
        cv.put(COLUMN_PASSWORD, adminModel.getPassword());

        boolean inserted = db.insert(TABLE_NAME_ADMIN, null, cv) > 0;
        db.close();

        return inserted;
    }

    /**
     * function to add a quiz to quiz table
     * @param insertedQuizModel
     * @return true if inserted correctly, otherwise returns false
     */
    public boolean insertQuiz(QuizModel insertedQuizModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TOPIC, insertedQuizModel.getTopic());
        cv.put(COLUMN_DATE, insertedQuizModel.getDate());
        // trying to insert if inserted correctly then 'inserted' will be equal to true otherwise false
        boolean inserted = db.insert(TABLE_NAME_QUIZ, null, cv) > 0;
        db.close();

        return inserted;
    }

    /**
     * function to add a question to question table
     * @param insertedQuestionModel
     * @return true if inserted correctly, otherwise returns false
     */
    public boolean insertQuestion(QuestionModel insertedQuestionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUESTION_STRING, insertedQuestionModel.getQuestion());
        cv.put(COLUMN_QUIZ_ID, insertedQuestionModel.getQuizId());

        // trying to insert if inserted correctly then 'inserted' will be equal to true otherwise false
        boolean inserted = db.insert(TABLE_NAME_QUESTION, null, cv) > 0;
        db.close();

        return inserted;
    }

    /**
     * function to add an answer to answer table
     * @param insertedAnswerModel
     * @return true if inserted correctly, otherwise returns false
     */
    public boolean insertAnswer(AnswerModel insertedAnswerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ANSWER_STRING, insertedAnswerModel.getAnswer());
        cv.put(COLUMN_IS_CORRECT, insertedAnswerModel.isCorrect());
        cv.put(COLUMN_QUESTION_ID, insertedAnswerModel.getQuestionId());

        // trying to insert if inserted correctly then 'inserted' will be equal to true otherwise false
        boolean inserted = db.insert(TABLE_NAME_ANSWER, null, cv) > 0;
        db.close();

        return inserted;
    }

    /**
     * function to delete a row in the answer table.
     * @param deleteAnswer
     * @return true if row was deleted correctly, otherwise returns false
     */
    public boolean deleteAnswer(AnswerModel deleteAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();

        // trying to delete if deleted correctly then 'deleted' will be equal to true otherwise false
        boolean deleted = db.delete(TABLE_NAME_ANSWER, COLUMN_ID + " = " + deleteAnswer.getId(), null) > 0;
        db.close();

        return deleted;
    }

    /**
     * function to delete a row in the question table.
     * @param deleteQuestion
     * @return true if row was deleted correctly, otherwise returns false
     */
    public boolean deleteQuestion(QuestionModel deleteQuestion) {
        SQLiteDatabase db = this.getWritableDatabase();

        // trying to delete if deleted correctly then 'deleted' will be equal to true otherwise false
        boolean deleted = db.delete(TABLE_NAME_QUESTION, COLUMN_ID + " = " + deleteQuestion.getId(), null) > 0;

        // after we deleted the question we also want to delete the answers to this specific question.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ANSWER +" WHERE " + COLUMN_QUESTION_ID + "=" + deleteQuestion.getId(), null);
        int index = 0;
        while(cursor.moveToNext()) {
            deleteAnswer(deleteQuestion.getAnswerModelList().get(index));
            index++;
        }
        cursor.close();
        db.close();

        return deleted;
    }

    /**
     * function to delete a row in the quiz table.
     * @param deleteQuiz
     * @return true if row was deleted correctly, otherwise returns false
     */
    public boolean deleteQuiz(QuizModel deleteQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        // trying to delete if deleted correctly then 'deleted' will be equal to true otherwise false
        boolean deleted = db.delete(TABLE_NAME_QUIZ, COLUMN_ID + "=" + deleteQuiz.getId(), null) > 0;

        // after we deleted the quiz we also want to delete the question and the  answers that belongs to  this specific quiz.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTION + " WHERE " + COLUMN_QUIZ_ID + " = " + deleteQuiz.getId(), null);

        int index = 0;
        List<QuestionModel> list = deleteQuiz.getQuestionModelList();
        while(cursor.moveToNext()) {
            deleteQuestion(list.get(index));
            index++;
        }
        cursor.close();
        db.close();

        return deleted;
    }

    /**
     * function to get all the answers that belongs a specific question by using his id attribute.
     * @param questionModel
     * @return list of question's answer.
     */
    public List<AnswerModel> getAnswerByQuestionId(@NonNull QuestionModel questionModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<AnswerModel> answerModelList = new ArrayList<>();
        Log.d("database", "Question " + questionModel.getId());
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ANSWER + " WHERE " + COLUMN_QUESTION_ID + " = " + questionModel.getId(), null);
        int index = 0;
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String answer = cursor.getString(1);
            boolean isCorrect = cursor.getInt(2) > 0;
            int questionId = cursor.getInt(3);
            answerModelList.add(new AnswerModel(id, answer, isCorrect, questionId));
            index++;
        }
        questionModel.setAnswerModelList(answerModelList);
        Log.d("database", "index: " + index);
        cursor.close();
        db.close();

        return answerModelList;
    }

    /**
     * function to get all the questions of the specific quiz
     * @param quizModel
     * @return list of all the question that belongs to the quiz
     */
    public List<QuestionModel> getQuestionsByQuizId(QuizModel quizModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<QuestionModel> questionModelList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTION + " WHERE " + COLUMN_QUIZ_ID + " = " + quizModel.getId(), null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String question = cursor.getString(1);
            int quizId = cursor.getInt(2);

            QuestionModel questionModel = new QuestionModel(id, question, quizId);
            questionModelList.add(questionModel);
            questionModel.setAnswerModelList(getAnswerByQuestionId(questionModel));
        }

        cursor.close();
        db.close();

        return questionModelList;
    }

    /**
     * function to get all the quizzes.
     * @return list of all the quizzes the currently stored in the database.
     */
    public List<QuizModel> getAllQuizzes() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<QuizModel> quizModelList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUIZ, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String topic = cursor.getString(1);
            String date = cursor.getString(2);

            quizModelList.add(new QuizModel(id, topic, date));
        }

        cursor.close();
        db.close();

        return quizModelList;
    }

    public boolean isUserAdminExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ADMIN, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean loginToTheAdmin(AdminModel adminModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = "'" + adminModel.getUsername() + "'";
        String password = "'" + adminModel.getPassword() + "'";
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ADMIN + " WHERE "
                                        + COLUMN_USERNAME + "=" + username
                                        + " AND " + COLUMN_PASSWORD + "=" + password,
                                        null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }
}