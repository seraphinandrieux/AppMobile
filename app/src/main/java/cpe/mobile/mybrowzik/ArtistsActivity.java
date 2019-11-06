package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.db.DBOpenHelper;
import cpe.mobile.mybrowzik.fragments.ArtistListFragment;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Artist;
import cpe.mobile.mybrowzik.models.DbConstants;
import cpe.mobile.mybrowzik.services.DBService;

public class ArtistsActivity extends AppCompatActivity {



    private List<Artist> artistList = new ArrayList<>();
    private  Cursor cursor;

    private static DBOpenHelper dbOpenHelper;

    private static DBService dbService;

    private SQLiteDatabase db;

    private static MyDBListener dbListener;

    private String[] artistProjection={
            DbConstants.KEY_COL_ARTIST
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        openDB();

        cursor=dbListener.getAllTable(DbConstants.ARTIST_TABLE,artistProjection);

        showStartup();
    }

    public void showStartup(){
        openDB();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ArtistListFragment artistListFragment = new ArtistListFragment(artistsQueryResult(cursor));
        artistListFragment.setMyDBListener(dbListener);


        transaction.replace(R.id.artistList,artistListFragment);
        transaction.commit();


    }

    public static void setMyDBListener(MyDBListener myDBListener) {
        dbListener = myDBListener;
    }

    public static void setDBService(DBService myDBService) {
        dbService = myDBService;
    }

    public static void setDBOpenHelper(DBOpenHelper myDBOpenHelper){
        dbOpenHelper = myDBOpenHelper;
    }



    public List<Artist> artistsQueryResult(Cursor cursor){


        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Artist artist =new Artist();
                artist.setName(cursor.getString(0));
                this.artistList.add(artist);
                cursor.moveToNext();
            }
        }
        return artistList;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    /**
     * * Open the database* *
     *
     * @throws SQLiteException
     */
    public void openDB() throws SQLiteException {
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    /** *Close Database */
    public void closeDB() {
        db.close();
    }


}
