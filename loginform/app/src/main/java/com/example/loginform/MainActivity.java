package com.example.loginform;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.Adapter.UserRecycleAdapter;
import com.example.loginform.Database.DatabaseHelper;
import com.example.loginform.Model.ModelUser;
import com.example.loginform.Model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    List<ModelUser> modelUsers;
    UserRecycleAdapter userRecycleAdapter;
    DatabaseHelper sqliteHelper;
    SharedPreferences sharedPreferences;
    TextView textViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewUsers);
        textViewUser = findViewById(R.id.user);
        sharedPreferences = MainActivity.this.getSharedPreferences("username", Context.MODE_PRIVATE);
        modelUsers = new ArrayList<>();
        sqliteHelper = new DatabaseHelper(MainActivity.this);

        String namaShare = sharedPreferences.getString("uname", "");
        textViewUser.setText("User : " + namaShare);

//        getDataFromSQLite();
        Log.e("TAG", "onCreate: " + sqliteHelper.getAllUser() );
        modelUsers = sqliteHelper.getAllUser();

        userRecycleAdapter = new UserRecycleAdapter(this, modelUsers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(userRecycleAdapter);



        List<ProductModel> listProduct = new ArrayList<>();
        for (int i=0; i<2; i++) {
            ProductModel model = new ProductModel();
            model.setId_product(1001+i);
            model.setProduct("Poduct "+i);

            listProduct.add(model);
            sqliteHelper.addProduct(model);
        }
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}