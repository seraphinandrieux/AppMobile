package cpe.mobile.mybrowzik.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class PlayerService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    private final Binder binder = new PlayerBinder();
    private boolean musicOnPause = false;
    private MediaPlayer myMediaPlayer = null;
    private static final String ACTION_PLAY = "com.example.action.PLAY";

    @Override
    public void onCreate() {
        super.onCreate();
        myMediaPlayer = new MediaPlayer(); // initialize it here
        myMediaPlayer.setOnPreparedListener(this);
    }

    public void initMediaPlayer(String myPath){
        //myMediaPlayer = new MediaPlayer();


        myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            myMediaPlayer.setDataSource(myPath);
            myMediaPlayer.prepareAsync();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        myMediaPlayer.setOnErrorListener(this);

        //mediaPlayer.setDataSource(this, myUri);


    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        //if (intent.getAction().equals(ACTION_PLAY)) {
             // prepare async to not block main thread
        //}

        return START_STICKY_COMPATIBILITY;
    }

    public void play(String path) throws IOException {
        int currentPos;
        currentPos = myMediaPlayer.getCurrentPosition();
        if(!musicOnPause) {

            initMediaPlayer(path);
        }else{
            myMediaPlayer.start();
            musicOnPause = false;
        }
    }

    public void stop() {
        myMediaPlayer.stop();
        myMediaPlayer.release();
        myMediaPlayer = null;

    }

    public void pause() {
        musicOnPause = true;
        myMediaPlayer.pause();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void onPrepared(MediaPlayer player) {
        myMediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public class PlayerBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }
}
