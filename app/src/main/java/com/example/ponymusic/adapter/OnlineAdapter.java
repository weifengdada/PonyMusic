package com.example.ponymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ponymusic.DetailsActivity;
import com.example.ponymusic.R;
import com.example.ponymusic.bean.ListBean;
import com.example.ponymusic.okhttp.AbstractUiCallBack;
import com.example.ponymusic.okhttp.OkhttpUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 2017/12/29.
 */

public class OnlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    public OnlineAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else if (position == 3) {
            return 1;
        }
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.online_item01, null);
            return new ParentViewHolder(view);
        }
        View view = View.inflate(context, R.layout.online_item02, null);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
      if(holder instanceof ParentViewHolder){
          if(position==0){
              ParentViewHolder viewHolder1 = (ParentViewHolder) holder;
              viewHolder1.parentTitle.setText("主打榜单");
          }else if(position==3){
              ParentViewHolder viewHolder1 = (ParentViewHolder) holder;
              viewHolder1.parentTitle.setText("分类榜单");
          }
      }else if(holder instanceof ChildViewHolder){
          String url =" http://tingapi.ting.baidu.com/v1/restserver/ting";
          ChildViewHolder childViewHolder= (ChildViewHolder) holder;
          if(position==1||position==2){
              OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.billboard.billList&size=10&offset=0&type="+position, new AbstractUiCallBack<ListBean>() {

                  @Override
                  public void success(final ListBean listBean) {
                         if(listBean!=null){
                             final List<ListBean.SongListBean> song_list  = listBean.getSong_list();
                             ((ChildViewHolder) holder).parentImage.setImageURI(listBean.getBillboard().getPic_s260() );
                             ((ChildViewHolder) holder).firstSong.setText("1."+song_list.get(0).getTitle());
                             ((ChildViewHolder) holder).secondSong.setText("2."+song_list.get(1).getTitle());
                             ((ChildViewHolder) holder).thirdSong.setText("3."+song_list.get(2).getTitle());
                             holder.itemView.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     String name = listBean.getBillboard().getName();
                                     Intent intent=new Intent(context,DetailsActivity.class);
                                     intent.putExtra("liebiao",name);
                                     intent.putExtra("image",listBean.getBillboard().getPic_s444());
                                     intent.putExtra("songlist",(Serializable) song_list);
                                     context.startActivity(intent);
                                 }
                             });
                         }

                  }

                  @Override
                  public void failure(Exception e) {

                  }

              });

          } else if(position==4||position==5){


                  OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.billboard.billList&size=10&offset=0&type="+(position+7), new AbstractUiCallBack<ListBean>() {
                      @Override
                      public void success(final ListBean listBean) {
                          if(listBean!=null){
                              final List<ListBean.SongListBean> song_list = listBean.getSong_list();
                              ((ChildViewHolder) holder).parentImage.setImageURI(listBean.getBillboard().getPic_s260() );
                              ((ChildViewHolder) holder).firstSong.setText("1."+song_list.get(0).getTitle());
                              ((ChildViewHolder) holder).secondSong.setText("2."+song_list.get(1).getTitle());
                              ((ChildViewHolder) holder).thirdSong.setText("3."+song_list.get(2).getTitle());
                              holder.itemView.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      String name = listBean.getBillboard().getName();
                                      Intent intent=new Intent(context,DetailsActivity.class);
                                      intent.putExtra("liebiao",name);
                                      intent.putExtra("image",listBean.getBillboard().getPic_s444());
                                      intent.putExtra("songlist",(Serializable) song_list);
                                      context.startActivity(intent);
                                  }
                              });
                          }

                      }

                      @Override
                      public void failure(Exception e) {

                      }

                  });


          }else if(position==6||position==7||position==8||position==9||position==10){

              OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.billboard.billList&size=10&offset=0&type="+(position+15), new AbstractUiCallBack<ListBean>() {

                  @Override
                  public void success(final ListBean listBean) {
                      if(listBean!=null){

                          final List<ListBean.SongListBean> song_list = listBean.getSong_list();
                          ((ChildViewHolder) holder).parentImage.setImageURI(listBean.getBillboard().getPic_s260() );
                          ((ChildViewHolder) holder).firstSong.setText("1."+ song_list.get(0).getTitle());
                          ((ChildViewHolder) holder).secondSong.setText("2."+ song_list.get(1).getTitle());
                          ((ChildViewHolder) holder).thirdSong.setText("3."+ song_list.get(2).getTitle());
                          holder.itemView.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  String name = listBean.getBillboard().getName();
                                  Intent intent=new Intent(context,DetailsActivity.class);
                                  intent.putExtra("image",listBean.getBillboard().getPic_s444());
                                  intent.putExtra("liebiao",name);
                                  intent.putExtra("songlist",(Serializable) song_list);
                                  context.startActivity(intent);
                              }
                          });
                      }

                  }

                  @Override
                  public void failure(Exception e) {

                  }

              });


          }

      }

    }
    @Override
    public int getItemCount() {
        return 10;
    }

    static class ParentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.parent_title)
        TextView parentTitle;

        ParentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    static class ChildViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.parent_image)
        SimpleDraweeView parentImage;
        @BindView(R.id.firstSong)
        TextView firstSong;
        @BindView(R.id.secondSong)
        TextView secondSong;
        @BindView(R.id.thirdSong)
        TextView thirdSong;

        ChildViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
