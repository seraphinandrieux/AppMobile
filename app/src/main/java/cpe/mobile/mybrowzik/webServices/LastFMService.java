package cpe.mobile.mybrowzik.webServices;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cpe.mobile.mybrowzik.DBManager.DBOpenHelper;
import cpe.mobile.mybrowzik.MainActivity;
import cpe.mobile.mybrowzik.listeners.MyHttpListener;
import cpe.mobile.mybrowzik.models.AudioFile;


public class LastFMService extends Thread {


    private String urlAPI = "https://ws.audioscrobbler.com";
    private String MY_API_KEY = "33dd1acf621f203b41edc0d7dfde0301";
    private JSONObject responseRequest;
    private AudioFile audioFile;
    private MyHttpListener listener;
    //private String artist;


    DBOpenHelper dbOpenHelper=new DBOpenHelper( MainActivity.getMainActivityContext(), DBOpenHelper.MyBrowZikDBInfos.DATABASE_NAME, null , DBOpenHelper.MyBrowZikDBInfos.DATABASE_VERSION);
    SQLiteDatabase database;


    public LastFMService(AudioFile audioFile,MyHttpListener listener) {
        this.audioFile = audioFile;
        this.listener = listener;

    }

    public void run() {
        try{
            database=dbOpenHelper.getWritableDatabase();
        }catch(SQLiteException e){
            database=dbOpenHelper.getReadableDatabase();
        }

        dbOpenHelper.onUpgrade(database,0,1);

        this.responseRequest = getInfoTrack(this.audioFile.getTitle(), this.audioFile.getArtist());

        //dbOpenHelper.onCreate(database);


        updateEmptyInfoFromAudioFile();
    }

    public void updateEmptyInfoFromAudioFile() {
        listener.setAlbum(getAlbum());

        listener.setAlbumPath(getAlbumPath());

        // we start another thread which will get back a bitmap of the image which is pointed with the albumPath
        ImageAlbumService myImageAlbumService = new ImageAlbumService(getAlbumPath(),listener); // we inside the url + the listener which contains the ImageView to update
        myImageAlbumService.start();



    }


    public JSONObject getInfoTrack(String track, String artist) {
        String tempUrl = urlAPI + "/2.0/?method=track.getInfo&api_key=" + MY_API_KEY + "&artist=" + artist + "&track=" + track + "&format=json";
        InputStream istream;
        JSONObject jsonObject =null;

        try {
            //tempUrl = tempUrl.replace(",","");
            //tempUrl = tempUrl.replace(" ","%20");
            URL url = new URL(tempUrl);
            System.out.println(url);

            HttpURLConnection c;
            try {
                c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoInput(true);
                c.setRequestProperty("Accept", "*/*");
                istream = c.getInputStream();

                InputStreamReader isr = new InputStreamReader(istream);
                BufferedReader br = new BufferedReader(isr);
                String resp = "";
                String data = br.readLine();

                while (data != null) {
                    resp += data;
                    System.out.println(data);
                    data = br.readLine();
                }

                try {

                    jsonObject = new JSONObject(resp);

                } catch (JSONException e) {

                    System.out.println(e);
                }


                istream.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(tempUrl);
            }
        } catch (MalformedURLException e) {
            System.out.println(e);
        }

        createDatabase(database,dbOpenHelper,jsonObject);

        return jsonObject;


    }

    public String getAlbumPath() { // will find the link to the big image
        String lreturn = "";
        System.out.println(this.responseRequest);
        try {
            lreturn = this.responseRequest.getJSONObject("track").getJSONObject("album").getJSONArray("image").getJSONObject(2).getString("#text");
        } catch (JSONException e) {
            System.out.println(e);
        }
        System.out.println("url de album "+ lreturn);
        return lreturn;
    }

    public String getAlbum() { // return album name
        String lreturn = "";
        try {
            lreturn = this.responseRequest.getJSONObject("track").getJSONObject("album").getString("title");
        } catch (JSONException e) {
            System.out.println(e);
        }
        return lreturn;
    }


    public void createDatabase( SQLiteDatabase database, DBOpenHelper dbOpenHelper,JSONObject jsonObject ){

        openDB(database,dbOpenHelper);

        // Insert a new record
        ContentValues musicContentValues = new ContentValues();
        ContentValues artistContentValues = new ContentValues();
        ContentValues albumContentValues = new ContentValues();
        ContentValues genreContentValues = new ContentValues();

        String MusicTable ="Music";
        String ArtistTable ="Artist";
        String AlbumTable = "Album";
        String GenreTable="Genre";
       long musicRowId = insertRecord(musicContentValues,database,MusicTable, jsonObject);
        long artistRowId =insertRecord(artistContentValues,database,ArtistTable,jsonObject);
         long albumRowId =insertRecord(albumContentValues,database,AlbumTable,jsonObject);
        long genreRowId =insertRecord(genreContentValues,database,GenreTable,jsonObject);


        // Update that line
        //rowId = updateRecord(contentValues, rowId);

        // Query that line
        //queryTheDatabase();

        // And then delete it:
        //deleteRecord(rowId,database);

        closeDB(database);


    }

    private long updateRecord(ContentValues contentValues,SQLiteDatabase database, long rowId,String table,JSONObject jsonObject){

        contentValues.clear();
        switch(table){
            case "Album":
                try{
                    contentValues.put(DBOpenHelper.AlbumTable.KEY_COL_NAME,jsonObject.getJSONObject("track").getJSONObject("album").getString("title"));
                    contentValues.put(DBOpenHelper.AlbumTable.KEY_COL_IMG_URL,jsonObject.getJSONObject("track").getJSONObject("album").getJSONArray("image").getJSONObject(2).getString("#text"));
                }catch(Exception e){
                    e.printStackTrace();
                }
                rowId=database.update(DBOpenHelper.AlbumTable.ALBUM_TABLE,contentValues,DBOpenHelper.AlbumTable.KEY_COL_ID+"="+ rowId,null);
                if(rowId==-1){
                    System.out.println("error when updating row");
                }else{
                    System.out.println("Row updated!");
                }
                return rowId;
            case "Genre":
                try{
                    contentValues.put(DBOpenHelper.GenreTable.KEY_COL_NAME,jsonObject.getJSONObject("track").getJSONObject("toptags").getJSONArray("tag").getJSONObject(0).getString("name"));

                }catch (Exception e){
                    e.printStackTrace();
                }
                rowId= database.update(DBOpenHelper.GenreTable.GENRE_TABLE,contentValues,DBOpenHelper.GenreTable.KEY_COL_ID+"="+rowId,null);
                if(rowId==-1){
                    System.out.println("error when updating row");
                }else{
                    System.out.println("Row updated!");
                }
                return rowId;
        }
        return rowId;
    }

    public void queryTheDatabase(){

    }


    public long insertRecord(ContentValues contentValues, SQLiteDatabase database, String table,JSONObject jsonObject){
        long rowId;
        switch(table){
            case "Music":
                contentValues.put(DBOpenHelper.MusicTable.KEY_COL_TITLE,audioFile.getTitle());
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
                contentValues.put(DBOpenHelper.ArtistTable.KEY_COL_NAME,audioFile.getArtist());
                rowId = database.insert(DBOpenHelper.ArtistTable.ARTIST_TABLE,null,contentValues);
                // Test to see if the insertion was ok
                if (rowId == -1) {
                    System.out.println("error when creating a row");
                } else {
                    System.out.println("Row created and stored in DB");
                }
                return rowId;

            case "Album":
                try{
                    contentValues.put(DBOpenHelper.AlbumTable.KEY_COL_NAME,jsonObject.getJSONObject("track").getJSONObject("album").getString("title"));
                    contentValues.put(DBOpenHelper.AlbumTable.KEY_COL_IMG_URL,jsonObject.getJSONObject("track").getJSONObject("album").getJSONArray("image").getJSONObject(2).getString("#text"));
                }catch(Exception e){
                    e.printStackTrace();
                }

                rowId = database.insert(DBOpenHelper.AlbumTable.ALBUM_TABLE,null,contentValues);
                if (rowId == -1) {
                    System.out.println("error when creating a row");
                } else {
                    System.out.println("Row created and stored in DB");
                }
                return rowId;

            case "Genre":
                try{
                    contentValues.put(DBOpenHelper.GenreTable.KEY_COL_NAME,jsonObject.getJSONObject("track").getJSONObject("toptags").getJSONArray("tag").getJSONObject(0).getString("name"));

                }catch (Exception e){
                    e.printStackTrace();
                }
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

    private void deleteRecord(long rowId,SQLiteDatabase database,String table) {
        switch (table){
            case "Music":
                rowId = database.delete(DBOpenHelper.MusicTable.MUSIC_TABLE,
                        DBOpenHelper.MusicTable.KEY_COL_ID + "=" + rowId, null);
                if (rowId == -1) {
                    System.out.println("error on deleting a row");
                } else {
                    System.out.println("Row deleted");
                }
                break;
            case "Artist":
                rowId = database.delete(DBOpenHelper.ArtistTable.ARTIST_TABLE,
                        DBOpenHelper.ArtistTable.KEY_COL_ID + "=" + rowId, null);
                if (rowId == -1) {
                    System.out.println("error on deleting a row");
                } else {
                    System.out.println("Row deleted");
                }
                break;
            case "Album":
                rowId = database.delete(DBOpenHelper.AlbumTable.ALBUM_TABLE,
                        DBOpenHelper.AlbumTable.KEY_COL_ID + "=" + rowId, null);
                if (rowId == -1) {
                    System.out.println("error on deleting a row");
                } else {
                    System.out.println("Row deleted");
                }
                break;
            case "Genre":
                rowId = database.delete(DBOpenHelper.GenreTable.GENRE_TABLE,
                        DBOpenHelper.GenreTable.KEY_COL_ID + "=" + rowId, null);
                if (rowId == -1) {
                    System.out.println("error on deleting a row");
                } else {
                    System.out.println("Row deleted");
                }
                break;

        }



    }





}







