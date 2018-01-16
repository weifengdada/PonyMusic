package com.example.ponymusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by acer on 2018/1/3.
 */

public class SongService extends Service{

    private boolean flag = false;
    public static MediaPlayer player;
    MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public void bindplay(String path) {
            if (flag) {
                player.start();
            } else {
                player.reset();
                try {
                    player.setDataSource(path);
                    player.prepareAsync();
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer mp) {
                            player.start();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                flag = false;
            }
        }
    }

    public void bindpause() {
        player.pause();
        flag = true;
    }

}
