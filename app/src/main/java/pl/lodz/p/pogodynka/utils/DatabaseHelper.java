package pl.lodz.p.pogodynka.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pietro on 07.06.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favorites.db";
    public static final String TABLE_NAME = "favorites";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_NAME + "(" +
                "id integer primary key autoincrement," +
                "city text unique," +
                "unit text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addFavorite(String city, String unit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", city);
        contentValues.put("unit", unit);
        try {
            db.insertOrThrow(TABLE_NAME, null, contentValues);
        }
        catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }

    public Cursor getFavorites() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"id", "city", "unit"};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }


}
