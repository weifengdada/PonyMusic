package com.example.ponymusic.bean;

import java.io.Serializable;

/**
 * Created by acer on 2017/12/29.
 */
public class Song implements Serializable{
    /**
     * 歌手
     */
    public String songId;


    public String singer;
    /**
     * 歌曲名
     */
    public String song;
    /**
     * 歌曲的地址
     */
    public String path;
    /**
     * 歌曲长度
     */
    public int duration;
    /**
     * 歌曲的大小
     */
    public long size;



}
