package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizapplication.DataBase.DataBaseHelper;

public class CheckIfLoggedActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_if_logged);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        // check if the user have an admin user
        // if not transition him into creating one
        // if he does have then get him to the activity where he can chose to log with admin or not
        if(dataBaseHelper.isUserAdminExists()) {
            Intent i = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(getApplicationContext(), CreateAdminActivity.class);
            startActivity(i);
        }
    }
}