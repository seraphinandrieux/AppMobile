package cpe.mobile.mybrowzik.listeners;

import cpe.mobile.mybrowzik.models.AudioFile;

public interface MyListener {
    public void onSelectMusic(AudioFile audioFile);

    public void onNextMusic();

    public void onPreviousMusic();
}
