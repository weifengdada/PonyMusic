package com.example.ponymusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ponymusic.adapter.DrawAdapter;
import com.example.ponymusic.fragment.MySongFragment;
import com.example.ponymusic.fragment.OnlineSongFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.btn_menu)
    ImageView btnMenu;
    @BindView(R.id.radio_minesong)
    RadioButton radioMinesong;
    @BindView(R.id.radio_online)
    RadioButton radioOnline;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.content_viewpager)
    ViewPager contentViewpager;
    @BindView(R.id.song_pic)
    ImageView songPic;
    @BindView(R.id.song_name)
    TextView songName;
    @BindView(R.id.song_detail)
    LinearLayout songDetail;
    @BindView(R.id.play_check)
    CheckBox playCheck;
    @BindView(R.id.next_song)
    ImageView nextSong;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.left_drawer)
    RecyclerView leftDrawer;
    @BindView(R.id.goPlay)
    RelativeLayout goPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initDate();
    }

    private void initDate() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrawerLayout();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        leftDrawer.setLayoutManager(manager);
        DrawAdapter drawAdapter = new DrawAdapter(this, drawerLayout);
        leftDrawer.setAdapter(drawAdapter);


        //  drawerLayout.openDrawer(Gravity.LEFT);
        contentViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new MySongFragment();
                        break;
                    case 1:
                        fragment = new OnlineSongFragment();
                        break;
                }
                return fragment;
            }


            @Override
            public int getCount() {
                return 2;
            }
        });
        contentViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void showDrawerLayout() {
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    @OnClick({R.id.radio_minesong, R.id.radio_online})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_minesong:
                contentViewpager.setCurrentItem(0, false);
                break;
            case R.id.radio_online:
                contentViewpager.setCurrentItem(1, false);

                break;
        }
    }

    @OnClick(R.id.goPlay)
    public void onViewClicked() {

        Intent intent=new Intent(HomeActivity.this,PlayActivity.class);
        startActivity(intent);

    }
}
