package cpe.mobile.mybrowzik.listener;

import cpe.mobile.mybrowzik.AudioFile;

public interface MyListener {
    public  void onStartMusic();
    public  void onStopMusic();
    public  void onNextMusic();
    public  void onPreviousMusic();
    public  void onSelectMusic(AudioFile file);
}
