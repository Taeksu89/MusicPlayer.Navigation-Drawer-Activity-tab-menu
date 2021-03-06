package com.taeksukim.android.soundplayer.domain;

import android.net.Uri;

import com.taeksukim.android.soundplayer.util.TimeUtil;

/**
 * Created by TaeksuKim on 2017. 2. 28..
 */

public class Sound extends Common{

    //Sound info.
    public int id;
    public Uri music_uri;
    public String title;
    public int artist_id;
    public String artist;
    public String artist_key;
    public int album_id;
    public Uri album_image_uri;
    public int genre_id;
    public int duration;
    public String is_music;
    public String composer;
    public String content_type;
    public String year;

    // add info.
    public int order;
    public boolean favor;


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public int getDuration() {

        return duration;
    }

    @Override
    public String getDurationText() {
        return TimeUtil.covertMiliToTime(getDuration());
    }

    @Override
    public Uri getImageUri() {
        return album_image_uri;
    }
}
