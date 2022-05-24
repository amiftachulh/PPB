package com.library.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "GameLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "game";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DEVELOPER = "developer";
    private static final String COLUMN_PUBLISHER = "publisher";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_REVIEW = "review";

    DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_DEVELOPER + " TEXT, " +
                        COLUMN_PUBLISHER + " TEXT, " +
                        COLUMN_RATING + " TEXT, " +
                        COLUMN_REVIEW + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addGame(String title, String developer, String publisher, String rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DEVELOPER, developer);
        cv.put(COLUMN_PUBLISHER, publisher);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_REVIEW, review);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add game", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Game added", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllGames() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String rowId, String title, String developer, String publisher, String rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DEVELOPER, developer);
        cv.put(COLUMN_PUBLISHER, publisher);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_REVIEW, review);
        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[] {rowId});
        if (result == -1) {
            Toast.makeText(context, "Failed to update game data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully update game data", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] {rowId});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete game", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully delete game", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllGamesData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
