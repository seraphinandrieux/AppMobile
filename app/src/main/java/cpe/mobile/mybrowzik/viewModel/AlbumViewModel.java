package cpe.mobile.mybrowzik.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.models.Album;

public class AlbumViewModel extends BaseObservable {
    private Album album = new Album();

    public void setAlbum(Album album){
        this.album=album;
        notifyChange();
    }

    public Album getAlbum(){return album;}

    @Bindable
    public String getName(){
        return album.getName();
    }

    @Bindable
    public String getAlbumImgUrl(){
        return album.getAlbumImgUrl();
    }

    @Bindable
    public String getYear(){
        return album.getYear();
    }

}
