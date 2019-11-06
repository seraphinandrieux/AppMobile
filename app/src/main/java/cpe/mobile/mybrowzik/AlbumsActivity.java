package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cpe.mobile.mybrowzik.db.DBOpenHelper;
import cpe.mobile.mybrowzik.fragments.AlbumListFragment;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Album;
import cpe.mobile.mybrowzik.models.DbConstants;
import cpe.mobile.mybrowzik.services.DBService;

public class AlbumsActivity extends AppCompatActivity {

    private List<Album> albumList = new ArrayList<>();
    private Cursor cursor;

    private static DBOpenHelper dbOpenHelper;

    private static DBService dbService;

    private SQLiteDatabase db;

    private static MyDBListener dbListener;

    String[] albumProjection={
            DbConstants.KEY_COL_ALBUM,
            DbConstants.KEY_COL_YEAR,
            DbConstants.KEY_COL_IMAGE_URL
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        openDB();
        cursor=dbListener.getAllTable(DbConstants.ALBUM_TABLE,albumProjection);
        showStartup();

    }

    public void showStartup(){
        openDB();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AlbumListFragment albumListFragment = new AlbumListFragment(albumQueryResult(cursor));
        albumListFragment.setMyDBListener(dbListener);

        transaction.replace(R.id.AlbumList,albumListFragment);
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

    public List<Album> albumQueryResult(Cursor cursor){
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Album album = new Album();
                album.setName(cursor.getString(0));
                album.setYear(cursor.getString(1));
                album.setAlbumImgUrl(cursor.getString(2));
                this.albumList.add(album);
                cursor.moveToNext();
            }
        }
        return albumList;
    }

    public List<Album> getAlbumList(){return albumList;}

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
