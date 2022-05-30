package com.library.game;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText titleUpdate, developerUpdate, publisherUpdate, reviewUpdate;
    Spinner ratingUpdate;
    Button updateBtn, deleteBtn;
    String id, title, developer, publisher, rating, review;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        titleUpdate = findViewById(R.id.titleUpdate);
        developerUpdate = findViewById(R.id.developerUpdate);
        publisherUpdate = findViewById(R.id.publisherUpdate);
        ratingUpdate = findViewById(R.id.ratingUpdate);
        reviewUpdate = findViewById(R.id.reviewUpdate);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        // Get and set the data from the previous activity
        getAndSetIntentData();

        // Set Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        db = new DatabaseHandler(UpdateActivity.this);
        updateBtn.setOnClickListener(view -> {
            if (titleUpdate.getText().toString().isEmpty()) {
                Toast.makeText(UpdateActivity.this, "Please fill the game title", Toast.LENGTH_SHORT).show();
            } else {
                title = titleUpdate.getText().toString().trim();
                developer = developerUpdate.getText().toString().trim();
                publisher = publisherUpdate.getText().toString().trim();
                rating = ratingUpdate.getSelectedItem().toString().trim();
                review = reviewUpdate.getText().toString().trim();
                db.updateData(id, title, developer, publisher, rating, review);
            }
        });

        deleteBtn.setOnClickListener(view -> confirmDialog());
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("developer") && getIntent().hasExtra("publisher") && getIntent().hasExtra("rating") && getIntent().hasExtra("review")) {
            // Get data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            developer = getIntent().getStringExtra("developer");
            publisher = getIntent().getStringExtra("publisher");
            rating = getIntent().getStringExtra("rating");
            review = getIntent().getStringExtra("review");

            // Rating variable is a string, so we need to convert it to int
            int ratingInt = rating.charAt(0) - '1';

            // Set data to intent
            titleUpdate.setText(title);
            developerUpdate.setText(developer);
            publisherUpdate.setText(publisher);
            ratingUpdate.setSelection(ratingInt);
            reviewUpdate.setText(review);
        } else {
            Toast.makeText(this, "No game found", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure want to delete " + title + "?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            db = new DatabaseHandler(UpdateActivity.this);
            db.deleteOneRow(id);
            dialog.dismiss();
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}