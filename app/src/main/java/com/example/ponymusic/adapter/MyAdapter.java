package com.example.ponymusic.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ponymusic.MusicUtils;
import com.example.ponymusic.R;
import com.example.ponymusic.bean.Song;
import com.example.ponymusic.fragment.MySongFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by acer on 2017/12/29.
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<Song> list;
    public MyAdapter(Context context, List<Song> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            //引入布局
            view = View.inflate(context, R.layout.item_music_listview, null);
            //实例化对象
            holder.image=view.findViewById(R.id.item_mymusic_postion);
            holder.song = (TextView) view.findViewById(R.id.item_mymusic_song);
            holder.singer = (TextView) view.findViewById(R.id.item_mymusic_singer);
            holder.duration = (TextView) view.findViewById(R.id.item_mymusic_duration);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //给控件赋值
        holder.image.setImageURI(Uri.parse("file://"+getAlbumArt(list.get(i).songId)));
        holder.song.setText(list.get(i).song.toString());
        holder.singer.setText(list.get(i).singer.toString());
        //时间需要转换一下
        int duration = list.get(i).duration;
        String time = MusicUtils.formatTime(duration);
        holder.duration.setText(time);

        return view;
    }
    class ViewHolder{
        ImageView image;
        TextView song;//歌曲名
        TextView singer;//歌手
        TextView duration;//时长

    }
    private String getAlbumArt(String album_id) {

        String mUriAlbums = "content://media/external/audio/albums";

        String[] projection = new String[]{"album_art"};

        Cursor cur = context.getContentResolver().query(

                Uri.parse(mUriAlbums + "/" + Integer.toString(Integer.parseInt(album_id))),

                projection, null, null, null);

        String album_art = null;

        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {

            cur.moveToNext();

            album_art = cur.getString(0);

        }

        cur.close();

        cur = null;

        return album_art;

    }

}
