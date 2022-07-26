package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizapplication.DataBase.DataBaseHelper;
import com.example.quizapplication.Models.QuestionModel;
import com.example.quizapplication.Models.QuizModel;
import com.example.quizapplication.RecyclerViewAdapters.QuestionsRecyclerViewAdapter;
import com.example.quizapplication.RecyclerViewAdapters.QuizRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv_countDown;
    EditText et_quizTopic;
    Button btn_addQuestion_Admin;
    FloatingActionButton fab_returnToQuiz;
    RecyclerView rv_questionsHolder;
    DataBaseHelper dataBaseHelper;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<QuestionModel> questionModelList;
    int quizId;
    int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        // initialize all the views.
        fab_returnToQuiz = findViewById(R.id.fab_returnToQuiz);
        rv_questionsHolder = findViewById(R.id.rv_questionsHolder);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        et_quizTopic = findViewById(R.id.et_quizTopic);
        tv_countDown = findViewById(R.id.tv_countDown);
        btn_addQuestion_Admin = findViewById(R.id.btn_addQuestion);
        quizId = getIntent().getIntExtra(QuizRecyclerViewAdapter.QUIZ_ID, -1);
        userType = getIntent().getIntExtra(MainActivity.isAdminUserOrSimple, 0);

        //set the question list
        questionModelList = dataBaseHelper.getQuestionsByQuizId(new QuizModel(quizId, "none", "10", 0, 0));

        // set recyclerView adapter
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_questionsHolder.setLayoutManager(layoutManager);
        adapter = new QuestionsRecyclerViewAdapter(questionModelList);
        rv_questionsHolder.setAdapter(adapter);

        // set on click listeners
        fab_returnToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_addQuestion_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateOrEditQuestionDialog();
            }
        });

        // admin section. make visible and enabled every feature that admin have.
        if (userType == 1) {
            btn_addQuestion_Admin.setVisibility(View.VISIBLE);
            btn_addQuestion_Admin.setEnabled(true);
        }
    }

    private void showCreateOrEditQuestionDialog() {
        // initialize

        AlertDialog.Builder popWindow = new AlertDialog.Builder(QuestionsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View createOrEdit = inflater.inflate(R.layout.add_question_pupupwindow, null);

        popWindow.setView(createOrEdit);
        popWindow.show();
        Log.d("database", "here");
    }
}