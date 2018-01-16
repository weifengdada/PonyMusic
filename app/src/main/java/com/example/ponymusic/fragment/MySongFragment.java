package com.example.ponymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ponymusic.MusicUtils;
import com.example.ponymusic.PlayActivity;
import com.example.ponymusic.R;
import com.example.ponymusic.adapter.MyAdapter;
import com.example.ponymusic.bean.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by acer on 2017/12/28.
 */

public class MySongFragment extends Fragment {

    @BindView(R.id.listview)
    ListView listview;
    Unbinder unbinder;
    private List<Song> list;
    private MyAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mysong, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList<>();
        list=MusicUtils.getMusicData(getActivity());
        adapter = new MyAdapter(getActivity(),list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songId = list.get(position).songId;
                String path=list.get(position).path;
                Intent intent=new Intent(getActivity(), PlayActivity.class);
                intent.putExtra("songpath",path);
                intent.putExtra("localMusic", (Serializable) list);
                String song = list.get(position).song;
                String singer = list.get(position).singer;
                intent.putExtra("song",song);
                intent.putExtra("singer",singer);
                intent.putExtra("sduration",list.get(position).duration+"");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
