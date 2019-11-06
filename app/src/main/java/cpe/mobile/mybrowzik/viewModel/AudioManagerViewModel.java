package cpe.mobile.mybrowzik.viewModel;

import android.widget.ProgressBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.models.AudioFile;

public class AudioManagerViewModel extends BaseObservable {

    //TODO Remove it and use only the audioFileManager

    private AudioFile audioFile = new AudioFile();
    private Integer   progress  = 0;



    public void setAudioFile(AudioFile file) {


        audioFile = file;
        notifyChange();
    }


    public AudioFile getAudioFile(){
        return audioFile;
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


    public String getPath() {

        return audioFile.getFilePath();

    }

    @Bindable
    public Integer getProgressMax() {

        return audioFile.getDuration();

    }

    @Bindable
    public Integer getProgress() {

        return audioFile.getDuration();

    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
