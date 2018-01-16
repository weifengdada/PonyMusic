package com.example.ponymusic;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by acer on 2017/12/29.
 */

public class IApplication extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
