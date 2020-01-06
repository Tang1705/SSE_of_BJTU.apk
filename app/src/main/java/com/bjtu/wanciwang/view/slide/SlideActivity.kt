package com.bjtu.wanciwang.view.slide

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.adapter.ViewPagerAdapter
import com.bjtu.wanciwang.view.login.LogInActivity
import kotlinx.android.synthetic.main.activity_slide.*
import kotlinx.android.synthetic.main.fragment_words_recite.*
import java.util.*

class SlideActivity : Activity() {
    private val data: MutableList<View> =
        ArrayList()

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_slide)
        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        initWidgets()
    }

    //初始化控件
    private fun initWidgets() {
        data.add(layoutInflater.inflate(R.layout.viewpagerlayout, null))
        data.add(layoutInflater.inflate(R.layout.viewpagerlayout, null))
        data.add(layoutInflater.inflate(R.layout.viewpagerlayout, null))

        el_splash_pager3!!.background.alpha = 0
        el_splash_pager2!!.background.alpha = 0
        btn_guide_enter!!.visibility = View.INVISIBLE
        tv_guide_skip!!.visibility = View.VISIBLE
        splash_vp!!.adapter = ViewPagerAdapter(data)
        btn_guide_enter!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SlideActivity, LogInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.out_alpha, R.anim.out_alpha)
            finish()
        })
        tv_guide_skip!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SlideActivity, LogInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.out_alpha, R.anim.out_alpha)
            finish()
        })
        splash_vp!!.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val f = positionOffsetPixels / 4
                if (position == 0) {
                    el_splash_pager2!!.getBackground().alpha = f
                    if (f >= 255) {
                        el_splash_pager2!!.getBackground().alpha = 255
                    }
                } else if (position == 1) {
                    el_splash_pager3!!.getBackground().alpha = f
                    if (f >= 255) {
                        el_splash_pager3!!.getBackground().alpha = 255
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                splash_iv_v1!!.setImageResource(R.drawable.bga_banner_point_disabled)
                splash_iv_v2!!.setImageResource(R.drawable.bga_banner_point_disabled)
                splash_iv_v3!!.setImageResource(R.drawable.bga_banner_point_disabled)
                if (position == 2) {
                    splash_iv_v3!!.setImageResource(R.drawable.bga_banner_point_enabled)
                    tv_guide_skip!!.visibility = View.INVISIBLE
                    btn_guide_enter!!.setVisibility(View.VISIBLE)
                } else {
                    if (position == 0) {
                        splash_iv_v1!!.setImageResource(R.drawable.bga_banner_point_enabled)
                    } else if (position == 1) {
                        splash_iv_v2!!.setImageResource(R.drawable.bga_banner_point_enabled)
                    }
                    tv_guide_skip!!.visibility = View.VISIBLE
                    btn_guide_enter!!.setVisibility(View.INVISIBLE)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}