package com.library.game;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView emptyLibrary;
    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    DatabaseHandler db;
    ArrayList<String> id, title, developer, publisher, rating, review;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        db = new DatabaseHandler(MainActivity.this);
        id = new ArrayList<>();
        title = new ArrayList<>();
        developer = new ArrayList<>();
        publisher = new ArrayList<>();
        rating = new ArrayList<>();
        review = new ArrayList<>();

        storeGamesInArray();

        customAdapter = new CustomAdapter(MainActivity.this, this, id, title, developer, publisher, rating, review);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeGamesInArray() {
        Cursor cursor = db.readAllGames();
        if (cursor.getCount() != 0) {
            emptyLibrary = findViewById(R.id.emptyLibrary);
            emptyLibrary.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                developer.add(cursor.getString(2));
                publisher.add(cursor.getString(3));
                rating.add(cursor.getString(4));
                review.add(cursor.getString(5));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
			case R.id.deleteAll:
				confirmDialog();
				return true;
			case R.id.about:
				Intent intent = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure want to delete all the games data?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            db = new DatabaseHandler(MainActivity.this);
            db.deleteAllGamesData();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            dialog.dismiss();
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}