package com.bjtu.wanciwang.view.logo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bjtu.wanciwang.R;
import com.bjtu.wanciwang.common.MySharedPreferences;
import com.bjtu.wanciwang.view.login.LogInActivity;
import com.bjtu.wanciwang.view.main.MainActivity;
import com.bjtu.wanciwang.view.slide.SlideActivity;


public class LogoActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_logo);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data", MODE_PRIVATE);
        final MySharedPreferences mySharedPreferences = MySharedPreferences.getSharedPreferences(sharedPreferences);
        mySharedPreferences.setIsFirstInOne();

        final LinearLayout tv_lin = (LinearLayout) findViewById(R.id.text_lin);//要显示的字体
        final LinearLayout tv_hide_lin = (LinearLayout) findViewById(R.id.text_hide_lin);//所谓的布
        ImageView logo = (ImageView) findViewById(R.id.image);//图片
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        logo.startAnimation(animation);//开始执行动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //第一个动画执行完后执行第二个动画就是那个字体显示那部分
                animation = AnimationUtils.loadAnimation(LogoActivity.this, R.anim.text_splash_position);
                tv_lin.startAnimation(animation);
                animation = AnimationUtils.loadAnimation(LogoActivity.this, R.anim.text_canvas);
                tv_hide_lin.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mySharedPreferences.getIsFirstIn()) {
                            mySharedPreferences.setIsFirstInTwo();
                            Intent intent = new Intent(LogoActivity.this, SlideActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
                            finish();
                        } else {
                            if (mySharedPreferences.getIsFirstLogIn()) {
                                Intent intent = new Intent(LogoActivity.this, LogInActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
                                finish();
                            } else {
                                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
                                finish();

                            }
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
