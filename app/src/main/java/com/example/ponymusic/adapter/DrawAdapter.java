package com.example.ponymusic.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ponymusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 2017/12/29.
 */

public class DrawAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private static final int WEATHER = 0;
    private static final int DATA = 1;
    DrawerLayout drawerLayout;
    public DrawAdapter(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout=drawerLayout;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == WEATHER) {
            View view = LayoutInflater.from(context).inflate(R.layout.weather, parent, false);
            return new WeatherViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.function_layout, parent, false);
            return new FunctionViewHolder(view);
        }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(holder instanceof WeatherViewHolder){
               ((WeatherViewHolder) holder).weather.setText("北京天气不错");
           }
            if(holder instanceof FunctionViewHolder){
                final String []data ={"功能设置","夜间模式","定时停止播放","退出应用","关于波尼音乐"};
                ArrayAdapter<String> array=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,data);
                ((FunctionViewHolder) holder).listFunction.setAdapter(array);
                ((FunctionViewHolder) holder).listFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {

                            drawerLayout.openDrawer(Gravity.LEFT);
                            Toast.makeText(context, data[position], Toast.LENGTH_SHORT).show();

                        } else {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                            Toast.makeText(context, data[position], Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather)
        TextView weather;

        WeatherViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FunctionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_function)
        ListView listFunction;

        FunctionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
