package com.library.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText titleAdd, developerAdd, publisherAdd, reviewAdd;
    Spinner ratingAdd;
    Button addBtn, resetBtn;

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
        addBtn.setOnClickListener(view -> add());
        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(view -> reset());
    }

    void add() {
        if (titleAdd.getText().toString().isEmpty()) {
            Toast.makeText(AddActivity.this, "Please fill the game title", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHandler db = new DatabaseHandler(AddActivity.this);
            db.addGame(
                    titleAdd.getText().toString().trim(),
                    developerAdd.getText().toString().trim(),
                    publisherAdd.getText().toString().trim(),
                    ratingAdd.getSelectedItem().toString().trim(),
                    reviewAdd.getText().toString().trim()
            );
        }
    }

    void reset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle("Reset");
        builder.setMessage("Are you sure you want to reset?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            titleAdd.setText("");
            developerAdd.setText("");
            publisherAdd.setText("");
            ratingAdd.setSelection(0);
            reviewAdd.setText("");
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}