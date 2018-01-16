package com.example.ponymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ponymusic.PlayActivity;
import com.example.ponymusic.R;
import com.example.ponymusic.bean.ListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 2018/1/2.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {
    Context context;
    List<ListBean.SongListBean> songlist;
    public SongListAdapter(Context context, List<ListBean.SongListBean> songlist) {
        this.context = context;
        this.songlist = songlist;
    }
    @Override
    public SongListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.songlist, null);
        return new SongListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SongListViewHolder holder, final int position) {
        holder.songImage.setImageURI(songlist.get(position).getPic_small());
        holder.songAuthor.setText(songlist.get(position).getAuthor());
        holder.songNam.setText(songlist.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String song_id = songlist.get(position).getSong_id();
                String author = songlist.get(position).getAuthor();
                String title = songlist.get(position).getTitle();
                Intent intent=new Intent(context,PlayActivity.class);
                intent.putExtra("ssonglist", (Serializable) songlist);
                intent.putExtra("songid",song_id);
                intent.putExtra("title",title);
                intent.putExtra("author",author);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return songlist.size();
    }

    static class SongListViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.song_image)
        SimpleDraweeView songImage;
        @BindView(R.id.song_nam)
        TextView songNam;
        @BindView(R.id.song_author)
        TextView songAuthor;

        SongListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
