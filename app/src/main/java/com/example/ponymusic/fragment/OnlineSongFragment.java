package com.example.ponymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ponymusic.DetailsActivity;
import com.example.ponymusic.R;
import com.example.ponymusic.adapter.OnlineAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by acer on 2017/12/28.
 */

public class OnlineSongFragment extends Fragment {

    @BindView(R.id.online_songlist)
    RecyclerView onlineSonglist;
    Unbinder unbinder;
    private OnlineAdapter onlineAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.onlinesong, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        onlineAdapter = new OnlineAdapter(getActivity());
        onlineSonglist.setLayoutManager(manager);
        onlineSonglist.setAdapter(onlineAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
