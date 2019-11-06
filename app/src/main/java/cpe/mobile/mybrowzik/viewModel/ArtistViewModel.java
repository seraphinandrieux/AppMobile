package cpe.mobile.mybrowzik.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.models.Artist;

public class ArtistViewModel extends BaseObservable {
    private Artist artist = new Artist();

    public void setArtist(Artist artist){
        this.artist=artist;
        notifyChange();
    }

    public Artist getArtist(){return artist;}

    @Bindable
    public String getName(){
        return artist.getName();
    }
}
