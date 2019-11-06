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

    private final  Binder   binder          = new PlayerBinder();
    private boolean         musicOnPause    = false;
    private MediaPlayer     myMediaPlayer   = null;

    @Override
    public void onCreate() {

        super.onCreate();

        myMediaPlayer = new MediaPlayer(); // initialize it here

        myMediaPlayer.setOnPreparedListener(this);
        myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void initMediaPlayer(String myPath){

        try {
            myMediaPlayer.setDataSource(myPath);
            myMediaPlayer.prepareAsync();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        myMediaPlayer.setOnErrorListener(this);



    }


    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY_COMPATIBILITY;
    }

    public void play() throws IOException {

            myMediaPlayer.start();


    }



    public void stop() {

        myMediaPlayer.stop();
        myMediaPlayer.reset();


    }

    public void pause() {
        musicOnPause = true;
        myMediaPlayer.pause();
    }

    public Integer getProgress(){
        return myMediaPlayer.getCurrentPosition();
    }

    public Integer getProgressMax(){
        return myMediaPlayer.getDuration();
    }

    public boolean isPlaying(){
        return myMediaPlayer.isPlaying();
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
