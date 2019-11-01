package cpe.mobile.mybrowzik.viewModel;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.listeners.MyHttpListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.webServices.LastFMService;

public class AudioFileViewModel extends BaseObservable {


    //LastFMService fmService = new LastFMService();



    private AudioFile audioFile = new AudioFile();

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

    @Bindable
    public String getAlbumPath() {

        return audioFile.getAlbumPath();

    }


    @Bindable
    public String getDuration() {

        return audioFile.getDurationText();

    }
}
