package br.com.fidelizacao.fidelizacao.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.fidelizacao.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static Context context;
    private long backPressedTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        context = this;

        startAnimations();
    }

    private void startAnimations() {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.reset();

        ImageView iv_background = (ImageView) findViewById(R.id.iv_background);

        if (iv_background != null) {
            iv_background.clearAnimation();
            iv_background.startAnimation(animation);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.fade_out);
                ActivityCompat.startActivity(context, new Intent(context, LoginActivity.class), activityOptionsCompat.toBundle());
                SplashScreenActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if ((time - backPressedTime) > 2000) {
            backPressedTime = time;
            Toast.makeText(context, getString(R.string.backpressed), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
