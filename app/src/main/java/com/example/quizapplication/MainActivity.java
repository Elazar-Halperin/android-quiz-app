package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.quizapplication.DataBase.DataBaseHelper;
import com.example.quizapplication.Models.QuizModel;
import com.example.quizapplication.RecyclerViewAdapters.QuizRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // access this field from other classes.
    public static final String isAdminUserOrSimple = "USERTYPE";
    FloatingActionButton fab_addQuiz;
    FloatingActionButton fab_changeUser;
    RecyclerView rv_quizContainer;
    DataBaseHelper dataBaseHelper;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<QuizModel> quizModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab_addQuiz = findViewById(R.id.fab_addQuiz);
        fab_changeUser = findViewById(R.id.fab_changeUser);
        rv_quizContainer = findViewById(R.id.rv_quizContainer);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        quizModelList = dataBaseHelper.getAllQuizzes();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_quizContainer.setLayoutManager(layoutManager);
        adapter = new QuizRecyclerViewAdapter(dataBaseHelper.getAllQuizzes());
        rv_quizContainer.setAdapter(adapter);

        // get the type user, 0 - simple, 1 - admin.
        // if userType is admin then show all the things that only admins can see.
        int userType = getIntent().getIntExtra(isAdminUserOrSimple, 0);
        if(userType == 1) {
            fab_addQuiz.setEnabled(true);
            fab_addQuiz.setVisibility(View.VISIBLE);

            fab_addQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                    i.putExtra(isAdminUserOrSimple, getIntent().getIntExtra(isAdminUserOrSimple, 0));
                    i.putExtra(QuizRecyclerViewAdapter.QUIZ_ID, -1);
                    startActivity(i);
                }
            });
            fab_changeUser.setImageDrawable(getDrawable(R.drawable.ic_baseline_admin_panel_settings_24));

        } else {
            fab_changeUser.setImageDrawable(getDrawable(R.drawable.ic_baseline_person_24));
        }

        fab_changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    // unable to return. :)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}