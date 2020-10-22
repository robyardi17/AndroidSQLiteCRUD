package com.example.loginform;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginform.Database.DatabaseHelper;
import com.example.loginform.Model.ModelUser;

public class DetailUser extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword, etAlamat, etNomor, etID;
    Button buttonSimpan, buttonDelete;
    SharedPreferences sharedPreferences;
    DatabaseHelper sqliteHelper;
    String idFromIntent, userFromIntent, pwFromIntent, alamatFromIntent, nomorFromIntent, namaShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        sqliteHelper = new DatabaseHelper(this);
        sharedPreferences = DetailUser.this.getSharedPreferences("username", Context.MODE_PRIVATE);
        namaShare = sharedPreferences.getString("uname", "");

        etID = findViewById(R.id.idUser);
        etNomor = findViewById(R.id.nomortelp);
        etPassword = findViewById(R.id.password);
        etUsername = findViewById(R.id.username);
        etAlamat = findViewById(R.id.alamat);
        buttonSimpan = findViewById(R.id.simpan);
        buttonDelete = findViewById(R.id.hapus);

        buttonDelete.setOnClickListener(this);
        buttonSimpan.setOnClickListener(this);

        userFromIntent = getIntent().getStringExtra("Username");
        pwFromIntent = getIntent().getStringExtra("Pw");
        alamatFromIntent = getIntent().getStringExtra("Alamat");
        nomorFromIntent = getIntent().getStringExtra("Nomor");
        idFromIntent = getIntent().getStringExtra("id");
        etID.setHint(idFromIntent);
        etUsername.setText(userFromIntent);
        etNomor.setText(nomorFromIntent);
        etAlamat.setText(alamatFromIntent);
        etPassword.setText(pwFromIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hapus:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String UserNameHapus = etUsername.getText().toString().trim();

                                Integer idInthapus = Integer.parseInt(idFromIntent);

                                if (sqliteHelper.checkUser(userFromIntent)) {
                                    sqliteHelper.deleteUser(new ModelUser(null, null, null, null, idInthapus));
                                    Toast.makeText(DetailUser.this, "Sukses Hapus User " + userFromIntent, Toast.LENGTH_LONG).show();
                                    if (userFromIntent.equals(namaShare)) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        Intent intent = new Intent(DetailUser.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(DetailUser.this, MainActivity.class);
                                        startActivity(intent);
                                    }

                                } else {
                                    Toast.makeText(DetailUser.this, "User "+ UserNameHapus + " gagal di hapus, coba lagi", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.simpan:
                System.out.println("simpan");
                String UserName = etUsername.getText().toString().trim();
                String Nomor = etNomor.getText().toString().trim();
                String Password = etPassword.getText().toString().trim();
                String Alamat = etAlamat.getText().toString().trim();

                Integer idInt = Integer.parseInt(idFromIntent);

                if (UserName.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(DetailUser.this, "Username/Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    if (Password.length() > 5) {
                        if (sqliteHelper.checkUser(userFromIntent)) {
                            sqliteHelper.updateUser(new ModelUser(UserName, Password, Alamat, Nomor, idInt));
                            Toast.makeText(DetailUser.this, "Sukses Update User " + userFromIntent, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DetailUser.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DetailUser.this, "User "+ UserName + " gagal update, coba lagi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(DetailUser.this, "Password terlalu pendek!", Toast.LENGTH_LONG).show();
                    }
                }
            break;
        }
    }
}