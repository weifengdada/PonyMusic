package com.example.ponymusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ponymusic.bean.DetailsBean;
import com.example.ponymusic.bean.ListBean;
import com.example.ponymusic.bean.Song;
import com.example.ponymusic.fragment.SongAnimationFragment;
import com.example.ponymusic.fragment.SongLrcFragment;
import com.example.ponymusic.okhttp.AbstractUiCallBack;
import com.example.ponymusic.okhttp.OkhttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends AppCompatActivity {


    @BindView(R.id.play_hide)
    ImageView playHide;
    @BindView(R.id.play_name)
    TextView playName;
    @BindView(R.id.play_author)
    TextView playAuthor;
    @BindView(R.id.play_viewpager)
    ViewPager playViewpager;
    @BindView(R.id.prev_song)
    ImageView prevSong;
    @BindView(R.id.song_play)
    ImageView songPlay;
    @BindView(R.id.song_next)
    ImageView songNext;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    private ServiceConnection connection;
    private SongService.MyBinder binder;
    private Intent service;
    private String songid;
    private String file_link;
    private String songpath;
    private boolean flag = false;
    private List<Song> localMusic;
    private int currIndex = -1;
    private String path;
    private List<ListBean.SongListBean> ssonglist;
    private String title;
    private String author;
    private String singer;
    private String song;
    private int file_duration;
    private String sduration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        author = intent.getStringExtra("author");
        title = intent.getStringExtra("title");
        singer = intent.getStringExtra("singer");
        song = intent.getStringExtra("song");
        sduration = intent.getStringExtra("sduration");
        songpath = intent.getStringExtra("songpath");
        ssonglist = (List<ListBean.SongListBean>) intent.getSerializableExtra("ssonglist");
        localMusic = (List<Song>) intent.getSerializableExtra("localMusic");
        songid = intent.getStringExtra("songid");
        if (songid != null) {
            EventBus.getDefault().postSticky(new FirstEvent(songid));
        }
        if (songid != null) {
            OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.song.play&songid=" + songid, new AbstractUiCallBack<DetailsBean>() {
                @Override
                public void success(DetailsBean detailsBean) {
                    if (detailsBean != null) {
                        file_link = detailsBean.getBitrate().getFile_link();
                        file_duration = detailsBean.getBitrate().getFile_duration();
                        endTime.setText(file_duration/60 % 60+":"+file_duration % 60);
                    }
                }
                @Override
                public void failure(Exception e) {

                }
            });
        } else {
            file_link = songpath;


            if(sduration!=null){
                int stime = Integer.parseInt(sduration);
                endTime.setText(stime/60/1000 % 60+":"+stime/1000 % 60);
            }

        }
        service = new Intent(PlayActivity.this, SongService.class);
        // 连接服务的对象
        connection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                // Activity与服务断开时调用的方法
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                // 连接的方法
                binder = (SongService.MyBinder) service;
            }
        };
        // 直接启动
        PlayActivity.this.startService(service);
        // 绑定启动
        PlayActivity.this.bindService(service, connection, BIND_AUTO_CREATE);


        playViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new SongAnimationFragment();


                        break;
                    case 1:
                        fragment = new SongLrcFragment();
                        break;

                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //数值的改变
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(SongService.player!=null&fromUser) {
                    SongService.player.seekTo(progress * file_duration*1000 / 100);
                }
            }
            //开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //停止拖动
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.play_hide, R.id.prev_song, R.id.song_play, R.id.song_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_hide:
                finish();
                break;
            case R.id.prev_song:

                if ((currIndex - 1) >= 0) {
                    currIndex--;
                    path = localMusic.get(currIndex).path;
                    binder.bindplay(path);
                } else {
                    currIndex = localMusic.size() - 1;
                    binder.bindplay(path);
                }
                playName.setText(localMusic.get(currIndex).song);
                playAuthor.setText(localMusic.get(currIndex).singer);
                break;
            case R.id.song_play:
                if (SongService.player.isPlaying()) {
                    SongService.player.pause();
                    flag = true;
                } else {
                    if (flag) {
                        SongService.player.start();
                    } else {
                        binder.bindplay(file_link);
                        flag = true;
                    }
                }
                if (songid != null) {
                    playName.setText(title);
                    playAuthor.setText(author);
                } else {
                    playAuthor.setText(singer);
                    playName.setText(song);

                }

                break;
            case R.id.song_next:
                if (currIndex + 1 < localMusic.size()) {
                    currIndex++;
                    path = localMusic.get(currIndex).path;
                    binder.bindplay(path);
                } else {
                    currIndex = -1;
                    binder.bindplay(path);
                }
                playName.setText(localMusic.get(currIndex).song);
                playAuthor.setText(localMusic.get(currIndex).singer);
                break;
        }
    }
}
