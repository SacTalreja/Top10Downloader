package com.sactalreja.top10downloader;

/**
 * Created by sachintalreja on 7/25/16.
 */
public class Application {

    private String name;
    private String artist;
    private String releaseDate;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Name:"+getName() +"\n" +"Release Date:"+getReleaseDate()+"\n"+"Artist"+getArtist();
    }
}
