package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;
import cpe.mobile.mybrowzik.fragments.AudioManagerFragment;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;

/*
Question a poser :



 */
public  class MainActivity  extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private List<AudioFile> myMusicList = new ArrayList<>();


    final static int MY_PERMISSION_REQUEST_CODE=1; // valeur arbitraire
    final static String MY_PERMISSION_NAME=Manifest.permission.READ_EXTERNAL_STORAGE;




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

    //const MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = "non";

    public void getPermission(){
        // Here, thisActivity is the current activity
        makeActionWithPermission();
    }



    public List<AudioFile> getPhoneMusic(){

        //requestPermissions(android.app.Activity, String[], int);

        //Context  context = (Context)this;
        //Uri uri = Environment.GetExternalStoragePublicDirectory("DCIM").AbsolutePath
        //Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; // La carte SD
        /*Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI; // La carte SD
        System.out.println("salut");
        System.out.println(uri + "/Music");
        String[]  projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST}; //chemin du fichier, titre, artist
        System.out.println(projection);
        Cursor cursor = this.getContentResolver().query(uri,projection,null,null,null);
        System.out.println(cursor);*/
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ;
        String[]  projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.YEAR}; //chemin du fichier, titre, artist
        System.out.println(projection);



        Cursor musicCursor = musicResolver.query(musicUri, projection, null, null, null);

        List<AudioFile> myAudioList = new ArrayList<>();;

            if(musicCursor!=null && musicCursor.moveToFirst()){
                //get columns



                //add songs to list
                do {
                    //long thisId = musicCursor.getLong(idColumn);
                    //String testTitre = musicCursor.title;
                    String thisTitle = musicCursor.getString(1);
                    String thisArtist = musicCursor.getString(2);
                    String thisAlbum = musicCursor.getString(3);
                    String thisFilePath = musicCursor.getString(0);
                    int thisYear = musicCursor.getInt(5);
                    System.out.println(musicCursor.getInt(4));
                    int thisDuration = (musicCursor.getInt(4)/1000);


                    myAudioList.add(new AudioFile(thisTitle, thisArtist, thisAlbum, thisYear, thisDuration, thisFilePath));
                }
                while (musicCursor.moveToNext());
            }
            return myAudioList;
    }

    public void showStartup() {
        getPermission();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment(myMusicList);

        AudioManagerFragment fragmentManager = new AudioManagerFragment();

        transaction.replace(R.id.musicListLayout,fragment);

        transaction.replace(R.id.musicManagerLayout,fragmentManager);

        MyListener listener = new MyListener() {
            @Override
            public void onSelectMusic(AudioFile audioFile) {
                fragmentManager.updateCurrentMusic(audioFile);
            }

            @Override
            public void onNextMusic() {

                fragmentManager.changeCurrentMusic(fragment.getAudioList(),(+1));
            }

            @Override
            public void onPreviousMusic() {
                fragmentManager.changeCurrentMusic(fragment.getAudioList(),(-1));
            }
        };


        fragment.setMyListener(listener);
        fragmentManager.setMyListener(listener);

        //transaction.add(fragmentManager);
        transaction.commit();
        //fragment.setMyListener(listener);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        showStartup();



    }


}
