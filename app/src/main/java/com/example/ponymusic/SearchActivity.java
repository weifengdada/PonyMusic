package com.example.ponymusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ponymusic.adapter.SearchAdapter;
import com.example.ponymusic.bean.SearchSongBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {


    @BindView(R.id.fanhui_home)
    ImageView fanhuiHome;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.search_song)
    ImageView searchSong;
    @BindView(R.id.search_list)
    RecyclerView searchList;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fanhui_home, R.id.search_song})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui_home:
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));

                break;
            case R.id.search_song:
                s = editSearch.getText().toString();
                if (s.equals("歌曲名,歌手名")) {

                } else {
                    String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.catalogSug&query="+s;
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpInterceptor()).build();
                    client.newCall(request).enqueue(new Callback() {

                        private List<SearchSongBean.SongBean> list;

                        @Override
                        public void onFailure(Call call, IOException e) {

                            System.out.println("e = " + e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String ss = response.body().string();
                            Gson gson = new Gson();
                            final SearchSongBean searchSongBean = gson.fromJson(ss, SearchSongBean.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list = searchSongBean.getSong();
                                    searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                                    SearchAdapter adapter = new SearchAdapter(SearchActivity.this, list);
                                    searchList.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });


                        }
                    });


                }
                break;
        }
    }
}