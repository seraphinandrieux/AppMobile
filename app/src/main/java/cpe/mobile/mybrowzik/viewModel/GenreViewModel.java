package cpe.mobile.mybrowzik.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cpe.mobile.mybrowzik.models.Genre;

public class GenreViewModel extends BaseObservable {

    private Genre genre = new Genre();

    public void setGenre(Genre genre){
        this.genre=genre;
        notifyChange();
    }

    public Genre getGenre(){return genre;}

    @Bindable
    public String getName(){
        return genre.getName();
    }
}
