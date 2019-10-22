package cpe.mobile.mybrowzik.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.AudioFile;

public class ControlMusicViewModel extends BaseObservable {
    private AudioFile audioFile = new AudioFile();

    public void setAudioFile(AudioFile file) {
        audioFile = file;
        notifyChange();
    }

    public AudioFile getAudioFile(){
        return audioFile;
    }

    /**
     *
     * @Bindable indique qu'un champ peut etre
     * mis a jour
     */
    @Bindable
    public String getArtist() {

        return audioFile.getArtist();

    }

    @Bindable
    public String getTitle() {

        return audioFile.getTitle();

    }

    @Bindable
    public String getAlbum() {

        return audioFile.getAlbum();

    }

    @Bindable
    public String getDuration() {

        return audioFile.getDurationText();

    }
}
