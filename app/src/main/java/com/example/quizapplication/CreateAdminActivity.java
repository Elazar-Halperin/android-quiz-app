package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapplication.DataBase.DataBaseHelper;
import com.example.quizapplication.Models.AdminModel;

public class CreateAdminActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    EditText et_username;
    EditText et_password;
    EditText et_confirmPassword;
    Button btn_createAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        btn_createAdmin = findViewById(R.id.btn_createAdmin);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        btn_createAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String confirmPassword = et_confirmPassword.getText().toString();
                if(checkIfTheEditTextsAreEmpty(username, password, confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "One of the fields is empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "passwords fields are not the same", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean inserted = dataBaseHelper.insertAdmin(new AdminModel(0, username, password));
                if(inserted) {
                    Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Error while inserting.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkIfTheEditTextsAreEmpty(String username, String password, String confirmPassword) {
        return username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
    }
}