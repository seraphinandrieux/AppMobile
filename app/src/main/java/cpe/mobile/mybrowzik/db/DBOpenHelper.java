package cpe.mobile.mybrowzik.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import cpe.mobile.mybrowzik.models.DbConstants;

public class DBOpenHelper extends SQLiteOpenHelper {

   
    /**
     * @goals This class aims to show the constant to use for the DBOpenHelper
     */


    /**
     * The static string to create the database.
     */
    private static final String MUSIC_TABLE_CREATE = "create table "
            + DbConstants.MUSIC_TABLE + "(" + DbConstants.KEY_COL_ID
            + " integer primary key autoincrement, " + DbConstants.KEY_COL_TITLE
            + " TEXT, " + DbConstants.KEY_COL_ARTIST_ID + " INTEGER, "
            + DbConstants.KEY_COL_ALBUM_ID + " INTEGER, "
            + DbConstants.KEY_COL_GENRE_ID + " INTEGER) ";

    private static final String ARTIST_TABLE_CREATE = "create table "
            + DbConstants.ARTIST_TABLE + "(" + DbConstants.KEY_COL_ID
            + " integer primary key autoincrement, " + DbConstants.KEY_COL_ARTIST
            + " TEXT) ";

    private static final String ALBUM_TABLE_CREATE = "create table "
            + DbConstants.ALBUM_TABLE + "(" + DbConstants.KEY_COL_ID
            + " integer primary key autoincrement, " + DbConstants.KEY_COL_ALBUM
            + " TEXT, " + DbConstants.KEY_COL_IMAGE_URL + " TEXT, "
            + DbConstants.KEY_COL_YEAR + " INTEGER) ";

    private static final String GENRE_TABLE_CREATE = "create table "
            + DbConstants.GENRE_TABLE + "(" + DbConstants.KEY_COL_ID
            + " integer primary key autoincrement, " + DbConstants.KEY_COL_GENRE
            + " TEXT) " ;


    /**
     * @param context
     *            = the context of the caller
     * @param name
     *            = Database's name
     * @param factory
     *            = null
     * @param version
     *            = Database's version
     */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the new database using the SQL string Database_create
        db.execSQL(MUSIC_TABLE_CREATE);
        db.execSQL(ALBUM_TABLE_CREATE);
        db.execSQL(ARTIST_TABLE_CREATE);
        db.execSQL(GENRE_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DBOpenHelper", "Mise à jour de la version " + oldVersion
                + " vers la version " + newVersion
                + ", les anciennes données seront détruites ");
        // Drop the old database

        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.GENRE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.ALBUM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.ARTIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.MUSIC_TABLE);

        // Create the new one
        onCreate(db);
        // or do a smartest stuff
    }

}

