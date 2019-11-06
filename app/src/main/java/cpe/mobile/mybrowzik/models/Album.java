package cpe.mobile.mybrowzik.models;

public class Album {
    String name;
    String albumImgUrl;
    String year;

    public Album(String name, String albumImgUrl, String year) {
        this.name = name;
        this.albumImgUrl = albumImgUrl;
        this.year = year;
    }

    public Album() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbumImgUrl() {
        return albumImgUrl;
    }

    public void setAlbumImgUrl(String albumImgUrl) {
        this.albumImgUrl = albumImgUrl;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
