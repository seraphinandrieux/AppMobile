package cpe.mobile.mybrowzik.listeners;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

import cpe.mobile.mybrowzik.models.AudioFile;

public interface MyDBListener extends Serializable {

    public void updateMyDB(String title,String artist,String album,String genre,Integer Year,String imageAlbumUrl);

    public String getAlbumFromID(Integer id);

    public String getGenreFromID(Integer id);

    public Integer getYearFromAlbumID(Integer id);

    public String getArtistFromID(Integer id);

    public String getImageAlbumFromAlbumID(Integer idAlbum);

    public Integer getAlbumIDFromTitle(String title);

    public Integer getArtistIDFromTitle(String title);

    public Integer getGenreIDFromTitle(String title);

    public boolean isInMyDB(String title);

    public Cursor getAllTable(String table, String[] projection);

}
