package com.example.loginform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginform.Database.DatabaseHelper;
import com.example.loginform.Model.ModelUser;

import java.sql.Struct;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etAlamat, etNomor;
    Button buttonSimpan;

    DatabaseHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqliteHelper = new DatabaseHelper(this);
        etNomor = findViewById(R.id.nomortelp);
        etPassword = findViewById(R.id.password);
        etUsername = findViewById(R.id.username);
        etAlamat = findViewById(R.id.alamat);
        buttonSimpan = findViewById(R.id.daftar);
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = etUsername.getText().toString();
                String Nomor = etNomor.getText().toString();
                String Password = etPassword.getText().toString();
                String Alamat = etAlamat.getText().toString();

                if (UserName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Username tidak boleh kosong", Toast.LENGTH_LONG).show();
                }

                if (Password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else {
                    if (Password.length() > 5) {
                        if (!sqliteHelper.checkUser(UserName)) {
                            sqliteHelper.addUser(new ModelUser(UserName, Password, Alamat, Nomor));
                            Toast.makeText(RegisterActivity.this, "User sukses terdaftar! Silahkan Login ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "User "+ UserName + "sudah terdaftar, coba lagi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password terlalu pendek!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}