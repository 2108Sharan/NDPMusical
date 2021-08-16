package sg.edu.rp.c346.id20011066.ndpsongsmusical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplesongs.db";
    private static final int DATABASE_VERSION = 10;
    private static final String TABLE_SONGS = "songs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "song_title";
    private static final String COLUMN_SINGERS = "song_singers";
    private static final String COLUMN_URL = "song_url";
    private static final String COLUMN_YEAR = "song_year";
    private static final String COLUMN_STARS = "song_stars";;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSQL = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_SINGERS + " TEXT, "
                + COLUMN_URL + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createSongTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }
    public long insertSong(String title, String singers, String url, int year, int stars) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_URL, url);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        return result;
    }
    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> notes = new ArrayList<>();

        String SQL = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TITLE + ", "
                + COLUMN_SINGERS  + ", "
                + COLUMN_URL + ", "
                + COLUMN_YEAR + ", "
                + COLUMN_STARS + " FROM " + TABLE_SONGS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                String url = cursor.getString(3);
                int year = cursor.getInt(4);
                int stars = cursor.getInt(5);
                Song song = new Song(id, title, singers, year, stars, url);
                song.setId(id);
                notes.add(song);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }
    public int updateSong(Song data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_URL, data.getURL());
        values.put(COLUMN_YEAR, data.getYears());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONGS, values, condition, args);
        db.close();
        return result;
    }
    public int deleteSong(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Song> getAllSongsByStars(String keyword) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_URL, COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_STARS + "= ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_SONGS, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                String url = cursor.getString(3);
                int year = cursor.getInt(4);
                int stars = cursor.getInt(5);
                Song song = new Song(id, title, singers, year, stars, url);
                song.setId(id);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
    public ArrayList<String> getYears() {
        ArrayList<String> codes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_YEAR};

        Cursor cursor;
        cursor = db.query(true, TABLE_SONGS, columns, null, null, null, null, null, null);
        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                codes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return codes;
    }
    public ArrayList<Song> getAllSongsByYear(int yearFilter) {
        ArrayList<Song> songs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_URL, COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_YEAR + "= ?";
        String[] args = {String.valueOf(yearFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_SONGS, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                String url = cursor.getString(3);
                int year = cursor.getInt(4);
                int stars = cursor.getInt(5);

                Song newSong = new Song(id, title, singers, year, stars, url);
                songs.add(newSong);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return songs;
    }
}
