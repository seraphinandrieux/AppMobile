package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;
import cpe.mobile.mybrowzik.fragments.AudioManagerFragment;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.services.PlayerService;

/*
Question a poser :



 */
public  class MainActivity  extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private List<AudioFile> myMusicList = new ArrayList<>();


    final static int MY_PERMISSION_REQUEST_CODE=1; // valeur arbitraire
    final static String MY_PERMISSION_NAME=Manifest.permission.READ_EXTERNAL_STORAGE;

    PlayerService mService;
    boolean mBound = false;

//----------------------------------------------------Main functions-----------------------------------------------------------------------------
    public void showStartup() {
        makeActionWithPermission();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment(myMusicList);

        AudioManagerFragment fragmentManager = new AudioManagerFragment();

        transaction.replace(R.id.musicListLayout,fragment);

        transaction.replace(R.id.musicManagerLayout,fragmentManager);

        MyListener listener = initListener(fragment,fragmentManager);


        fragment.setMyListener(listener);
        fragmentManager.setMyListener(listener);


        transaction.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        showStartup();



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


    public MyListener initListener(AudioFileListFragment pfragmentList,AudioManagerFragment pFragmentManager){
        MyListener listener = new MyListener() {
            @Override
            public void onSelectMusic(AudioFile audioFile) {
                pFragmentManager.updateCurrentMusic(audioFile);
            }

            @Override
            public void onNextMusic() {

                pFragmentManager.changeCurrentMusic(pfragmentList.getAudioList(), (+1));
            }

            @Override
            public void onPreviousMusic() {
                pFragmentManager.changeCurrentMusic(pfragmentList.getAudioList(), (-1));
            }

            @Override
            public void onPlayMusic(String filePath) {
                try {
                    mService.play(filePath);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }

            @Override
            public void onPauseMusic() {

                mService.pause();


            }
        };
        return listener ;
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


}
