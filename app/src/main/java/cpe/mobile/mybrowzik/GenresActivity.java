package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cpe.mobile.mybrowzik.db.DBOpenHelper;
import cpe.mobile.mybrowzik.fragments.GenreListFragment;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Artist;
import cpe.mobile.mybrowzik.models.DbConstants;
import cpe.mobile.mybrowzik.models.Genre;
import cpe.mobile.mybrowzik.services.DBService;

public class GenresActivity extends AppCompatActivity {

    private List<Genre> genreList = new ArrayList<>();

    private Cursor cursor;

    private static DBOpenHelper dbOpenHelper;

    private static DBService dbService;

    private SQLiteDatabase db;

    private static MyDBListener dbListener;

    String[] genreProjection={
            DbConstants.KEY_COL_GENRE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        openDB();
        cursor=dbListener.getAllTable(DbConstants.GENRE_TABLE,genreProjection);
        showStartup();
    }

    public void showStartup(){
        openDB();
        FragmentManager manager =getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        GenreListFragment genreListFragment = new GenreListFragment(genresQueryResult(cursor));

        transaction.replace(R.id.GenreList,genreListFragment);
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

    public List<Genre> genresQueryResult(Cursor cursor){
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Genre genre = new Genre();
                genre.setName(cursor.getString(0));
                this.genreList.add(genre);
                cursor.moveToNext();
            }
        }
        return genreList;
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
