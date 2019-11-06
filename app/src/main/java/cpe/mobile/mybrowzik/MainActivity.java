package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.db.DBOpenHelper;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;
import cpe.mobile.mybrowzik.fragments.AudioManagerFragment;

import cpe.mobile.mybrowzik.fragments.ChangeViewFragment;
import cpe.mobile.mybrowzik.listeners.ChangeViewListener;

import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.models.DbConstants;
import cpe.mobile.mybrowzik.models.DbRequestType;
import cpe.mobile.mybrowzik.services.DBService;
import cpe.mobile.mybrowzik.services.PlayerService;


/*
Question a poser :
je veux faire un onclick sur mon item, c'est ou dans l'adapter ou avant l'appel de l'adapter, et ou ca car je vois pas mon objet
mon listener plus dans mon fragment ? si oui dois-je instancier dans celui ci mon viewholder ? qu'est ce que vraiment mon view holder ? 




 */






/*
Question a poser :



 */
public  class MainActivity  extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<AudioFile> myMusicList = new ArrayList<>();
    public SQLiteDatabase db;
    public DBOpenHelper dbOpenHelper;


    private static MyDBListener myDBListener;

    private DBService dbService;

    final static int MY_PERMISSION_REQUEST_CODE=1; // valeur arbitraire
    final static String MY_PERMISSION_NAME=Manifest.permission.READ_EXTERNAL_STORAGE;

    PlayerService mService;
    boolean mBound = false;


    private static Context context;




    //----------------------------------------------------Main functions-----------------------------------------------------------------------------

    public void showStartup() {
        makeActionWithPermission();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment(myMusicList);

        AudioManagerFragment fragmentManager = new AudioManagerFragment();

        ChangeViewFragment changeViewFragment = new ChangeViewFragment();

        transaction.replace(R.id.musicListLayout,fragment);

        transaction.replace(R.id.musicManagerLayout,fragmentManager);

        transaction.replace(R.id.changeViewLayout,changeViewFragment);



        MyListener listener = initListener(fragment,fragmentManager);
        fragment.setMyListener(listener);


        dbOpenHelper = new DBOpenHelper(this,DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);

        openDB();

        dbOpenHelper.onUpgrade(db,0,1);

        dbService=new DBService(db);

        myDBListener = initMyDBListener(dbService);



        MyDBListener myDBListener = initMyDBListener(new DBService(db));

        fragment.setMyDBListener(myDBListener);
        fragmentManager.setMyListener(listener);

        ChangeViewListener changeViewListener =initChangeViewListener();
        changeViewFragment.setListener(changeViewListener);




        transaction.commit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MainActivity.context = getApplicationContext();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        

        showStartup();



    }

    public static Context getAppContext() {
        return MainActivity.context;
    }



//---------------------------------------------------Permission functions -------------------------------------------------------------------------

    public void makeActionWithPermission(){
        // A-t-on la permission de le faire ?
        if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION_NAME) != PackageManager.PERMISSION_GRANTED) {
            // Si "non", alors il faut demander la ou les permissions

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{MY_PERMISSION_NAME},
                    MY_PERMISSION_REQUEST_CODE);

            // Finir ici le traitement : ne pas bloquer.
            // On attend la réponse via onRequestPermissionsResult
        } else {
            Toast.makeText(MainActivity.this,"acces to external files : Authorized", Toast.LENGTH_LONG).show();
            myMusicList = getPhoneMusic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,"Réponse de l'utilisateur : oui", Toast.LENGTH_LONG).show();

                    // On relance la méthode de réalisation de l'action. Cette fois, on a l'autorisation.
                    makeActionWithPermission();

                } else {
                    Toast.makeText(MainActivity.this,"Réponse de l'utilisateur : non", Toast.LENGTH_LONG).show();
                    // On abandonne notre super fonctionnalité.
                }
                return;
            }
        }
    }


//-----------------------------------------------------Listener functions------------------------------------------------------------

    public ChangeViewListener initChangeViewListener(){
        Intent artistActivity = new Intent(MainActivity.this, ArtistsActivity.class);
        Intent albumActivity = new Intent(MainActivity.this, AlbumsActivity.class);
        Intent genreActivity = new Intent(MainActivity.this, GenresActivity.class);



        ChangeViewListener listener = new ChangeViewListener() {
            @Override
            public void onArtists() {

                startActivity(artistActivity);
                ArtistsActivity.setMyDBListener(myDBListener);
                ArtistsActivity.setDBService(dbService);
                ArtistsActivity.setDBOpenHelper(dbOpenHelper);
            }

            @Override
            public void onAlbums() {

                startActivity(albumActivity);
                AlbumsActivity.setMyDBListener(myDBListener);
                AlbumsActivity.setDBService(dbService);
                AlbumsActivity.setDBOpenHelper(dbOpenHelper);
            }

            @Override
            public void onGenre() {

                startActivity(genreActivity);
                GenresActivity.setMyDBListener(myDBListener);
                GenresActivity.setDBService(dbService);
                GenresActivity.setDBOpenHelper(dbOpenHelper);

            }
        };
        return listener;
    }


    public MyListener initListener(AudioFileListFragment pfragmentList,AudioManagerFragment pFragmentManager){
        MyListener listener = new MyListener() {
            @Override
            public void onSelectMusic(AudioFile audioFile) {
                pFragmentManager.updateCurrentMusic(audioFile);
                mService.initMediaPlayer(audioFile.getFilePath());
            }

            @Override
            public void onNextMusic() {

                pFragmentManager.changeCurrentMusic(pfragmentList.getAudioList(), (+1));
                try {
                    mService.stop();
                    mService.initMediaPlayer(pFragmentManager.getCurrentMusic().getFilePath());
                    //mService.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onPreviousMusic() {
                pFragmentManager.changeCurrentMusic(pfragmentList.getAudioList(), (-1));
                try {
                    mService.stop();
                    mService.initMediaPlayer(pFragmentManager.getCurrentMusic().getFilePath());
                    //mService.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onPlayMusic(String filePath) {
                try {
                    mService.play();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }

            @Override
            public void onPauseMusic() {

                mService.pause();


            }

            @Override
            public Integer getProgress() {

                return mService.getProgress();


            }
        };
        return listener ;
    }

    //----------------------------------------Listener DB + DB debug functions----------------------------------------------


    public static MyDBListener getDBListenerInstance(Context cxt){
        if(myDBListener==null){

        }
        return myDBListener;
    }


    public MyDBListener initMyDBListener(DBService myDbService){
        MyDBListener myDBListener = new MyDBListener() {
            @Override
            public void updateMyDB(String title, String artist, String palbum, String genre, Integer year, String imageAlbumUrl) {

                ArrayList<String> myArrayList = new ArrayList<String>();
                myArrayList.add(palbum);

                //System.out.println("Mon nouveau string " +GetStringArray(myArrayList)[0]);


                System.out.println("params" +title+ artist+ palbum+ genre+ year+ imageAlbumUrl );
                if(!myDbService.checkIfExist(DbConstants.ALBUM_TABLE,GetStringArray(myArrayList),DbConstants.KEY_COL_ALBUM)){
                    System.out.println("je insert un album");
                    myDbService.executeRequest(DbRequestType.INSERT,DbConstants.ALBUM_TABLE,null,new String[]{palbum,imageAlbumUrl,year.toString()});
                    System.out.println("fin de l'insert");
                }
                if(!myDbService.checkIfExist(DbConstants.GENRE_TABLE,new String[]{genre},DbConstants.KEY_COL_GENRE)){
                    System.out.println("je insert un genre");
                    myDbService.executeRequest(DbRequestType.INSERT,DbConstants.GENRE_TABLE,null,new String[]{genre});
                }
                if(!myDbService.checkIfExist(DbConstants.ARTIST_TABLE,new String[]{artist},DbConstants.KEY_COL_ARTIST)){
                    myDbService.executeRequest(DbRequestType.INSERT,DbConstants.ARTIST_TABLE,null,new String[]{artist});
                }
                if(!myDbService.checkIfExist(DbConstants.MUSIC_TABLE,new String[]{title},DbConstants.KEY_COL_TITLE)){
                    Integer artistID,albumID,genreID;
                    artistID = myDbService.getIDfromTable(DbConstants.ARTIST_TABLE,DbConstants.KEY_COL_ARTIST+"=?",new String[]{artist});
                    albumID  = myDbService.getIDfromTable(DbConstants.ALBUM_TABLE,DbConstants.KEY_COL_ALBUM+"=?",new String[]{palbum});
                    genreID  = myDbService.getIDfromTable(DbConstants.GENRE_TABLE,DbConstants.KEY_COL_GENRE+"=?",new String[]{genre});
                    myDbService.executeRequest(DbRequestType.INSERT,DbConstants.MUSIC_TABLE,null,new String[]{title,artistID.toString(),albumID.toString(),genreID.toString()});
                }
            }


            @Override
            public String getAlbumFromID(Integer id){

                return myDbService.getListfromTable(DbConstants.ALBUM_TABLE,DbConstants.KEY_COL_ID,new String[]{id.toString()},DbConstants.KEY_COL_ALBUM).get(0).toString();

            }
            @Override
            public String getGenreFromID(Integer id){
                return myDbService.getListfromTable(DbConstants.GENRE_TABLE,DbConstants.KEY_COL_ID,new String[]{id.toString()},DbConstants.KEY_COL_GENRE).get(0).toString();

            }
            @Override
            public String getArtistFromID(Integer id){
                return myDbService.getListfromTable(DbConstants.ARTIST_TABLE,DbConstants.KEY_COL_ID,new String[]{id.toString()},DbConstants.KEY_COL_ARTIST).get(0).toString();

            }
            @Override
            public String getImageAlbumFromAlbumID(Integer id){

                return myDbService.getListfromTable(DbConstants.ALBUM_TABLE,DbConstants.KEY_COL_ID,new String[]{id.toString()},DbConstants.KEY_COL_IMAGE_URL).get(0).toString();

            }

            @Override
            public Integer getYearFromAlbumID(Integer id){
                return Integer.parseInt(myDbService.getListfromTable(DbConstants.ALBUM_TABLE,DbConstants.KEY_COL_ID,new String[]{id.toString()},DbConstants.KEY_COL_YEAR).get(0).toString());

            }
            @Override
            public Integer getAlbumIDFromTitle(String title){
                return Integer.parseInt(myDbService.getListfromTable(DbConstants.MUSIC_TABLE,DbConstants.KEY_COL_TITLE,new String[]{title},DbConstants.KEY_COL_ALBUM_ID).get(0).toString());

            }
            @Override
            public Integer getArtistIDFromTitle(String title){
                return Integer.parseInt(myDbService.getListfromTable(DbConstants.MUSIC_TABLE,DbConstants.KEY_COL_TITLE,new String[]{title},DbConstants.KEY_COL_ARTIST_ID).get(0).toString());
                }
            @Override
            public Integer getGenreIDFromTitle(String title){
                return Integer.parseInt(myDbService.getListfromTable(DbConstants.MUSIC_TABLE,DbConstants.KEY_COL_TITLE,new String[]{title},DbConstants.KEY_COL_GENRE_ID).get(0).toString());

            }
            @Override
            public boolean isInMyDB(String title){
                boolean lreturn=false;
                if(myDbService.checkIfExist(DbConstants.MUSIC_TABLE,new String[]{title},DbConstants.KEY_COL_TITLE)){
                    lreturn = true;
                }
                return lreturn;
            }





            @Override
            public Cursor getAllTable(String table,String[] projection) {
                Cursor cursor;
                cursor = myDbService.getDb().query(true,table,projection,null,null,null,null,null,null);
                return cursor;
            }
        };

        return myDBListener;
    }

    public void getDbInfo(){
        DBService dbService = new DBService(db);
        System.out.println("on va rentrer dans le check de la DB");
        System.out.println(dbService.getListfromTable(DbConstants.ALBUM_TABLE,null,null,DbConstants.KEY_COL_IMAGE_URL));
        System.out.println(dbService.getListfromTable(DbConstants.MUSIC_TABLE,null,null,DbConstants.KEY_COL_TITLE));
        System.out.println(dbService.getListfromTable(DbConstants.GENRE_TABLE,null,null,DbConstants.KEY_COL_GENRE));
        System.out.println("fin du check");
    }

    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }
    //---------------------------------------Music functions-----------------------------------------------



    public List<AudioFile> getPhoneMusic(){

        List<AudioFile> myAudioList = new ArrayList<>();
        ContentResolver musicResolver = getContentResolver();

        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ; //define path inside the phone

        // La projection va filtrer ce que le curseur va recuperer du resolver et va le stocker en colonne.
        // Exemple col 0 on contient data, la 1 le titre etc ....
        String[]  projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.YEAR}; //chemin du fichier, titre, artist

        // on pointe sur le resolver en filtrant avec la projection

        Cursor musicCursor = musicResolver.query(musicUri, projection, null, null, null);



        if(musicCursor!=null && musicCursor.moveToFirst()){
             //add songs to list
            do {

                String thisTitle = musicCursor.getString(1);
                String thisArtist = musicCursor.getString(2);
                String thisAlbum = musicCursor.getString(3);
                String thisFilePath = musicCursor.getString(0);
                int thisYear = musicCursor.getInt(5);
                int thisDuration = (int)(musicCursor.getLong(4)/1000);


                myAudioList.add(new AudioFile(thisTitle, thisArtist, thisAlbum, thisYear, thisDuration, thisFilePath));
            }
            while (musicCursor.moveToNext());
        }


        onStart();
        return myAudioList;

    }


//--------------------------------------------Services functions---------------------------------------


    @Override
    protected void onStart() {
        super.onStart();
        // Bind to PlayerService
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, connection, this.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.PlayerBinder binder = (PlayerService.PlayerBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        openDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //closeDB();
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


    public SQLiteDatabase getDb() {
        return db;
    }


}
