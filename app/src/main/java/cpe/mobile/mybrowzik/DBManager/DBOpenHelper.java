package cpe.mobile.mybrowzik.DBManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;


    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the new database
        db.execSQL(CREATE_MUSIC_TABLE);
        db.execSQL(CREATE_ARTIST_TABLE);
        db.execSQL(CREATE_ALBUM_TABLE);
        db.execSQL(CREATE_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DBOpenHelper", "Mise à jour de la version " + oldVersion
                + " vers la version " + newVersion
                + ", les anciennes données seront détruites ");
        db.execSQL("DROP TABLE IF EXISTS " + MusicTable.MUSIC_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ArtistTable.ARTIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AlbumTable.ALBUM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GenreTable.GENRE_TABLE);

        onCreate(db);
    }

    public static class MyBrowZikDBInfos implements BaseColumns{
        public static final String DATABASE_NAME = "myBrowZikDB.db";
        public static final int DATABASE_VERSION = 1;

    }

    public static class MusicTable implements BaseColumns{
        public static final String MUSIC_TABLE = "Music";
        public static final String KEY_COL_ID ="_id";
        public static final String KEY_COL_TITLE="title";
        public static final String KEY_COL_ARTIST_ID="artist_id";
        public static final String KEY_COL_ALBUM_ID="album_id";
        public static final String KEY_COL_GENRE_ID="genre_id";

        public static final int ID_COLUMN=1;
        public static final int TITLE_COLUMN=2;
        public static final int ARTIST_ID_COLUMN=3;
        public static final int ALBUM_ID_COLUMN=4;
        public static final int GENRE_ID_COLUMN=5;
    }

    public static class ArtistTable implements BaseColumns{
        public static final String ARTIST_TABLE = "Artist";
        public static final String KEY_COL_ID ="_id";
        public static final String KEY_COL_NAME="name";

        public static final int ID_COLUMN=1;
        public static final int NAME_COLUMN=2;

    }

    public static class AlbumTable implements BaseColumns{
        public static final String ALBUM_TABLE = "Album";
        public static final String KEY_COL_ID ="_id";
        public static final String KEY_COL_NAME="name";
        public static final String KEY_COL_IMG_URL="image";
        public static final String KEY_COL_YEAR="year";

        public static final int ID_COLUMN=1;
        public static final int NAME_COLUMN=2;
        public static final int IMG_URL_COLUMN=3;
        public static final int YEAR_COLUMN=4;

    }

    public static class GenreTable implements BaseColumns{
        public static final String GENRE_TABLE = "Genre";
        public static final String KEY_COL_ID ="_id";
        public static final String KEY_COL_NAME="name";

        public static final int ID_COLUMN=1;
        public static final int NAME_COLUMN=2;

    }

    private static final String CREATE_MUSIC_TABLE="create table "+MusicTable.MUSIC_TABLE+"( "
            +MusicTable.KEY_COL_ID+" integer primary key autoincrement, "+MusicTable.KEY_COL_TITLE
            +" TEXT, "+MusicTable.KEY_COL_ARTIST_ID+" INTEGER, "+MusicTable.KEY_COL_ALBUM_ID+" INTEGER, "
            +MusicTable.KEY_COL_GENRE_ID+" INTEGER) ";
    private static final String CREATE_ARTIST_TABLE="create table "+ArtistTable.ARTIST_TABLE+"( "
            +ArtistTable.KEY_COL_ID+" integer primary key autoincrement, "+ArtistTable.KEY_COL_NAME+" TEXT) ";
    private static final String CREATE_ALBUM_TABLE="create table "+AlbumTable.ALBUM_TABLE+"( "
            +AlbumTable.KEY_COL_ID+" integer primary key autoincrement, "+ AlbumTable.KEY_COL_NAME+" TEXT, "
            +AlbumTable.KEY_COL_IMG_URL+" TEXT, "+ AlbumTable.KEY_COL_YEAR+" INTEGER) ";
    private  static final String CREATE_GENRE_TABLE="create table "+GenreTable.GENRE_TABLE+"( "
            +GenreTable.KEY_COL_ID+" integer primary key autoincrement, "+GenreTable.KEY_COL_NAME+" TEXT) ";





}
