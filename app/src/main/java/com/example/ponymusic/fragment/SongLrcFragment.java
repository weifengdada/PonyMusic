package com.example.ponymusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ponymusic.FirstEvent;
import com.example.ponymusic.R;
import com.example.ponymusic.SongService;
import com.example.ponymusic.bean.LrcSongBean;
import com.example.ponymusic.lrcview.LrcView;
import com.example.ponymusic.lrcview.bean.LrcBean;
import com.example.ponymusic.okhttp.AbstractUiCallBack;
import com.example.ponymusic.okhttp.OkhttpUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by acer on 2018/1/8.
 */

public class SongLrcFragment extends Fragment {
    @BindView(R.id.lrc_view)
    LrcView lrcView;
    Unbinder unbinder;
    private String sid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.songlrc, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirstEvent event) {
        sid = event.getSid();
        OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.song.lry&songid="+sid, new AbstractUiCallBack<LrcSongBean>() {


            @Override
            public void success(LrcSongBean lrcSongBean) {
                if(lrcSongBean!=null){
                    lrcView.setLrc(lrcSongBean.getLrcContent());
                    lrcView.setPlayer(SongService.player);
                    lrcView.init();
                }
            }

            @Override
            public void failure(Exception e) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
