package com.example.ponymusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_tiaoguo)
    TextView splashTiaoguo;
    private int time=5;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                if(time>0){
                    time--;
                    splashTiaoguo.setText("还剩"+time+"跳转");
                    handler.sendEmptyMessageDelayed(0,1000);
                }else{
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    finish();
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        handler.sendEmptyMessageDelayed(0,1000);

    }

    @OnClick(R.id.splash_tiaoguo)
    public void onViewClicked() {

         startActivity(new Intent(this,HomeActivity.class));
         finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

    }
}
