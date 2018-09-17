package com.oli.testrelevant;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.oli.testrelevant.helpers.MyReceiver;
import com.oli.testrelevant.sharedPrefferences.SharedPreff;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.slika_splash)
    ImageView slika;
    String token ;
    String yestoken;
    String notoken;
    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Picasso.with(this).load(R.drawable.splash).fit().into(slika);
        splash();
    }
    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
    }
    public void splash(){
        receiver = new MyReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        String network = receiver.isNetworkAvailable(this);
        if (network.equals("You are not connected")){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },10000);
        }else {

            token = SharedPreff.getSessionID(this);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    if (!token.isEmpty()){

                        Intent intent = new Intent(SplashScreenActivity.this,FirstActivity.class);
                        intent.putExtra("token",yestoken);
                        startActivity(intent);
                        finish();

                    }else {

                        Intent intent = new Intent(SplashScreenActivity.this,FirstActivity.class);
                        intent.putExtra("notoken",notoken);
                        startActivity(intent);
                        finish();

                    }}}, 2000);
        }
    }
}
