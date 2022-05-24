package com.library.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    EditText titleAdd, developerAdd, publisherAdd, reviewAdd;
    Spinner ratingAdd;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleAdd = findViewById(R.id.titleAdd);
        developerAdd = findViewById(R.id.developerAdd);
        publisherAdd = findViewById(R.id.publisherAdd);
        ratingAdd = findViewById(R.id.ratingAdd);
        reviewAdd = findViewById(R.id.reviewAdd);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            DatabaseHandler db = new DatabaseHandler(AddActivity.this);
            db.addGame(
                    titleAdd.getText().toString().trim(),
                    developerAdd.getText().toString().trim(),
                    publisherAdd.getText().toString().trim(),
                    ratingAdd.getSelectedItem().toString().trim(),
                    reviewAdd.getText().toString().trim()
            );
        });
    }
}