package cpe.mobile.mybrowzik.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.models.AudioFile;

public class AudioFileViewModel extends BaseObservable {

    private AudioFile audioFile = new AudioFile();

    public void setAudioFile(AudioFile file) {
        audioFile = file;
        notifyChange();
    }

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
