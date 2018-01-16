package com.example.ponymusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ponymusic.adapter.SongListAdapter;
import com.example.ponymusic.bean.ListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.image_fanhui)
    ImageView imageFanhui;
    @BindView(R.id.list_name)
    TextView listName;
    @BindView(R.id.list_image)
    SimpleDraweeView listImage;
    @BindView(R.id.list_recyclerview)
    RecyclerView listRecyclerview;
    private List<ListBean.SongListBean> songlist;
    private SongListAdapter songListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String liebiao = intent.getStringExtra("liebiao");
        listName.setText(liebiao);
        songlist = (List<ListBean.SongListBean>) intent.getSerializableExtra("songlist");
        String image = intent.getStringExtra("image");

        listImage.setImageURI(image);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listRecyclerview.setLayoutManager(manager);
        songListAdapter = new SongListAdapter(this,songlist);
        listRecyclerview.setAdapter(songListAdapter);
    }


    @OnClick(R.id.image_fanhui)
    public void onViewClicked() {
        finish();
    }
}
