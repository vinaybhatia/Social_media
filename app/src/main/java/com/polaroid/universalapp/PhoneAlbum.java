package com.polaroid.universalapp;

import android.graphics.drawable.Drawable;

import java.util.Vector;

public class PhoneAlbum {


    private long id;
    private String name;
    private String coverUri;
    private Drawable drawableCoverUri;
    private Vector<PhonePhoto> albumPhotos;
    private int count;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getCoverUri() {
        return coverUri;
    }

    public void setCoverUri( String albumCoverUri ) {
        this.coverUri = albumCoverUri;
    }

    public Drawable getDrawableCoverUri() {
        return drawableCoverUri;
    }

    public void setCoverUri( Drawable albumCoverUri ) {
        this.drawableCoverUri = albumCoverUri;
    }

    public Vector< PhonePhoto > getAlbumPhotos() {
        if ( albumPhotos == null ) {
            albumPhotos = new Vector<>();
        }
        return albumPhotos;
    }

    public void setAlbumPhotos( Vector< PhonePhoto > albumPhotos ) {
        this.albumPhotos = albumPhotos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}