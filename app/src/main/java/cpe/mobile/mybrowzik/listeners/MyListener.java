package cpe.mobile.mybrowzik.listeners;

import cpe.mobile.mybrowzik.models.AudioFile;


public interface MyListener {
    public void onSelectMusic(AudioFile audioFile);

    public void onNextMusic();

    public void onPreviousMusic();

    public void onPlayMusic(String filePath);

    public void onPauseMusic();

    public Integer getProgress();

}
