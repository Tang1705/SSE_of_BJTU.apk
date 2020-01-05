package com.bjtu.wanciwang.view.slide;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;


import com.bjtu.wanciwang.R;
import com.bjtu.wanciwang.adapter.ViewPagerAdapter;
import com.bjtu.wanciwang.view.login.LogInActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends Activity {
    private List<View> datas = new ArrayList<>();
    private ViewPager vp;
    private Button button;
    private TextView textView;
    private RelativeLayout rl1, rl2, rl3;
    private ImageView iv_1, iv_2, iv_3;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_slide);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initWidgets();


    }

    //初始化控件
    private void initWidgets() {
        datas.add(getLayoutInflater().inflate(R.layout.viewpagerlayout, null));
        datas.add(getLayoutInflater().inflate(R.layout.viewpagerlayout, null));
        datas.add(getLayoutInflater().inflate(R.layout.viewpagerlayout, null));
        vp = findViewById(R.id.splash_vp);
        button = findViewById(R.id.btn_guide_enter);
        textView = findViewById(R.id.tv_guide_skip);
        rl1 = findViewById(R.id.el_splash_pager1);
        rl2 = findViewById(R.id.el_splash_pager2);
        rl3 = findViewById(R.id.el_splash_pager3);
        iv_1 = findViewById(R.id.splash_iv_v1);
        iv_2 = findViewById(R.id.splash_iv_v2);
        iv_3 = findViewById(R.id.splash_iv_v3);
        rl3.getBackground().setAlpha(0);
        rl2.getBackground().setAlpha(0);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        vp.setAdapter(new ViewPagerAdapter(datas));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideActivity.this, LogInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.out_alpha, R.anim.out_alpha);
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideActivity.this, LogInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.out_alpha, R.anim.out_alpha);
                finish();
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int f = positionOffsetPixels / 4;
                if (position == 0) {
                    rl2.getBackground().setAlpha(f);
                    if (f >= 255) {
                        rl2.getBackground().setAlpha(255);
                    }
                } else if (position == 1) {
                    rl3.getBackground().setAlpha(f);
                    if (f >= 255) {
                        rl3.getBackground().setAlpha(255);
                    }
                }

            }

            @Override
            public void onPageSelected(int position) {
                iv_1.setImageResource(R.drawable.bga_banner_point_disabled);
                iv_2.setImageResource(R.drawable.bga_banner_point_disabled);
                iv_3.setImageResource(R.drawable.bga_banner_point_disabled);
                if (position == 2) {
                    iv_3.setImageResource(R.drawable.bga_banner_point_enabled);
                    textView.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.VISIBLE);
                } else {
                    if (position == 0) {
                        iv_1.setImageResource(R.drawable.bga_banner_point_enabled);
                    } else if (position == 1) {
                        iv_2.setImageResource(R.drawable.bga_banner_point_enabled);
                    }
                    textView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
