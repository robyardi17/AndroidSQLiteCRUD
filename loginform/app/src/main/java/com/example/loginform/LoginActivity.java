package com.example.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.loginform.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editTextAlamat, editTextNomor;
    Button loginBtn;
    SharedPreferences sharedPreferences;
    DatabaseHelper sqliteHelper;
    String dataShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = LoginActivity.this.getSharedPreferences("username", Context.MODE_PRIVATE);
        dataShared = sharedPreferences.getString("uname", "");
        sqliteHelper = new DatabaseHelper(this);

        if (!dataShared.isEmpty()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Username", dataShared);
            startActivity(intent);
        }

        editTextNomor = findViewById(R.id.nomortelp);
        editTextPassword = findViewById(R.id.password);
        editTextUsername = findViewById(R.id.username);
        editTextAlamat = findViewById(R.id.alamat);
        loginBtn = findViewById(R.id.submit);
        initCreateNewAccount();

        //set click event of login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = editTextUsername.getText().toString().trim();
                String Password = editTextPassword.getText().toString().trim();

                if (Username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else if(Password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else {
                    if (sqliteHelper.checkUser(Username, Password)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("uname", Username);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Sukses login", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Username atau Password salah!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initCreateNewAccount() {
        ImageButton imageButton = findViewById(R.id.register);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}