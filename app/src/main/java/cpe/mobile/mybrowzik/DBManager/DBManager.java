package cpe.mobile.mybrowzik.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import org.json.JSONObject;

public class DBManager {

    /*private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase database;

    public  DBManager(DBOpenHelper dbOpenHelper, SQLiteDatabase db){
        this.dbOpenHelper=dbOpenHelper;
        this.database=db;
    }*/

    public void createDatabase(Context context,SQLiteDatabase database,DBOpenHelper dbOpenHelper){
        dbOpenHelper = new DBOpenHelper(context, DBOpenHelper.MyBrowZikDBInfos.DATABASE_NAME, null , DBOpenHelper.MyBrowZikDBInfos.DATABASE_VERSION);
        openDB(database,dbOpenHelper);

        // Insert a new record
        ContentValues contentValues = new ContentValues();
        String MusicTable ="Music";
        String ArtistTable ="Artist";
        String AlbumTable = "Album";
        String GenreTable="Genre";
        long rowId = insertRecord(contentValues,database,MusicTable);

        // Update that line
        //rowId = updateRecord(contentValues, rowId);

        // Query that line
        queryTheDatabase();

        // And then delete it:
        //deleteRecord(rowId,database);


    }

    private long updateRecord(ContentValues contentValues, long rowId){
        return 0;
    }

    public void queryTheDatabase(){

    }


    public long insertRecord(ContentValues contentValues, SQLiteDatabase database, String table){
        long rowId;
        switch(table){
            case "Music":
                contentValues.put(DBOpenHelper.MusicTable.KEY_COL_TITLE,"title_ex"/*ce qu'on va recup du webservice*/);
                contentValues.put(DBOpenHelper.MusicTable.KEY_COL_ARTIST_ID,0);
                contentValues.put(DBOpenHelper.MusicTable.KEY_COL_ALBUM_ID,0);
                contentValues.put(DBOpenHelper.MusicTable.KEY_COL_GENRE_ID,0);
                rowId =database.insert(DBOpenHelper.MusicTable.MUSIC_TABLE,null,contentValues);
                // Test to see if the insertion was ok
                if (rowId == -1) {
                    System.out.println("error when creating a row");
                } else {
                    System.out.println("Row created and stored in DB");
                }
                return rowId;

            case "Artist":
                contentValues.put(DBOpenHelper.ArtistTable.KEY_COL_NAME,"");
                rowId = database.insert(DBOpenHelper.ArtistTable.ARTIST_TABLE,null,contentValues);
                // Test to see if the insertion was ok
                if (rowId == -1) {
                    System.out.println("error when creating a row");
                } else {
                    System.out.println("Row created and stored in DB");
                }
                return rowId;

            case "Album":
                contentValues.put(DBOpenHelper.AlbumTable.KEY_COL_NAME,"");
                contentValues.put(DBOpenHelper.AlbumTable.KEY_COL_IMG_URL,"");
                rowId = database.insert(DBOpenHelper.AlbumTable.ALBUM_TABLE,null,contentValues);
                if (rowId == -1) {
                    System.out.println("error when creating a row");
                } else {
                    System.out.println("Row created and stored in DB");
                }
                return rowId;

            case "Genre":
                contentValues.put(DBOpenHelper.GenreTable.KEY_COL_NAME,"");
                rowId= database.insert(DBOpenHelper.GenreTable.GENRE_TABLE,null,contentValues);
                if (rowId == -1) {
                    System.out.println("error when creating a row");
                } else {
                    System.out.println("Row created and stored in DB");
                }
                return rowId;
            default:
                return -1;

        }

    }

    public void openDB(SQLiteDatabase database,DBOpenHelper dbOpenHelper) throws SQLiteException {
        try{
            database = dbOpenHelper.getWritableDatabase();
        }catch (SQLiteException ex){
            database = dbOpenHelper.getReadableDatabase();
        }
    }

    public void closeDB(SQLiteDatabase database){
        database.close();
    }

    private void deleteRecord(long rowId,SQLiteDatabase database) {
        rowId = database.delete(DBOpenHelper.MusicTable.MUSIC_TABLE,
                DBOpenHelper.MusicTable.KEY_COL_ID + "=" + rowId, null);
        if (rowId == -1) {
            System.out.println("error on deleting a row");
        } else {
            System.out.println("Row deleted");
        }
    }

}
