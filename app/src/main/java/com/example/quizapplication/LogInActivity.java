package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapplication.DataBase.DataBaseHelper;
import com.example.quizapplication.Models.AdminModel;
import com.example.quizapplication.R;

public class LogInActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    EditText et_usernameSubmit;
    EditText et_passwordSubmit;
    Button btn_signAsAdmin;
    Button btn_continueAsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        et_usernameSubmit = findViewById(R.id.et_usernameSubmit);
        et_passwordSubmit = findViewById(R.id.et_passwordSubmit);
        btn_signAsAdmin = findViewById(R.id.btn_signAsAdmin);
        btn_continueAsUser = findViewById(R.id.btn_continueAsUser);

        btn_signAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_usernameSubmit.getText().toString();
                String password = et_passwordSubmit.getText().toString();

                if(checkIfEmptyFields(username, password)) {
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                AdminModel adminModel = new AdminModel(0, username, password);
                boolean exists = dataBaseHelper.loginToTheAdmin(adminModel);
                if(!exists) {
                    Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(exists) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    // sending the code for admin user '1'.
                    i.putExtra(MainActivity.isAdminUserOrSimple, 1);
                    startActivity(i);
                }
            }
        });

        btn_continueAsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.isAdminUserOrSimple, 0);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean checkIfEmptyFields(String username, String password) {
        return username.isEmpty() || password.isEmpty();
    }
}