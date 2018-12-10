package mobile.bny.bandroidapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import mobile.bny.android_tools.BSharedPreferences;
import mobile.bny.android_tools.Utils;


public class SplashActivity extends AppCompatActivity {
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //save display metrics for later use
        BSharedPreferences.putMetrics(this);


        new Handler().postDelayed(() -> Utils.startNextActivity(SplashActivity.this, MainActivity.class, true), 1500);
    }

}
