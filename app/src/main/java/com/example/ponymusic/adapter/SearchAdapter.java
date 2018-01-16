package com.example.ponymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ponymusic.PlayActivity;
import com.example.ponymusic.R;
import com.example.ponymusic.bean.SearchSongBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 2017/12/29.
 */

public class SearchAdapter extends RecyclerView.Adapter <SearchAdapter.MyViewHolder>{
    Context context;
    List<SearchSongBean.SongBean> list;

    public SearchAdapter(Context context, List<SearchSongBean.SongBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_search, null);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {
          holder.searchSongname.setText(list.get(position).getSongname());
          holder.searchAuthor.setText(list.get(position).getArtistname());
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String title = list.get(position).getSongname();
                  String author = list.get(position).getArtistname();
                  String songid = list.get(position).getSongid();

                  Intent intent=new Intent(context,PlayActivity.class);
                  intent.putExtra("songid", songid);
                  intent.putExtra("title",title);
                  intent.putExtra("author",author);
               //   intent.putExtra("time",);
                  context.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.searchSongname)
        TextView searchSongname;
        @BindView(R.id.searchAuthor)
        TextView searchAuthor;
        @BindView(R.id.gongneng)
        ImageView gongneng;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
