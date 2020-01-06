package com.bjtu.wanciwang.view.article

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.adapter.RecycleViewAdapter
import com.bjtu.wanciwang.common.GetByURL
import com.bjtu.wanciwang.entity.Article
import kotlinx.android.synthetic.main.activity_scanarticle.*
import org.json.JSONObject

import java.util.ArrayList

class scanarticleActivity : AppCompatActivity() {

    lateinit var recycleViewAdapter: RecycleViewAdapter
    private var viewPager: ViewPager? = null  //轮播图模块
    private var mImg: IntArray? = null
    private var mImg_id: IntArray? = null
    private var mDec: Array<String>? = null
    private var mImgList: ArrayList<ImageView>? = null
    private var ll_dots_container: LinearLayout? = null
    private var loop_dec: TextView? = null
    private var previousSelectedPosition = 0
    internal var isRunning = false
    var urls: ArrayList<String> = ArrayList()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanarticle)
        recyclerView.layoutManager = LinearLayoutManager(this)

        initLoopView()  //实现轮播图

        urls.add("http://tang5618.com:8080/elearn/materials/1/videoframe")
        urls.add("http://tang5618.com/WEB/org.png")
        urls.add("http://tang5618.com/WEB/zyd.jpg")
        urls.add("http://tang5618.com/WEB/teck.png")
        urls.add("http://tang5618.com/WEB/timeline.png")
        urls.add("http://tang5618.com/WEB/function.png")

        val list: ArrayList<Article> = ArrayList()

        val intent = this.intent
        val bundle = intent.extras
        val uid: Int = bundle!!.getInt("uuid")
        val token: String = bundle.getString("token")!!

        val jsonObject =
            JSONObject(
                GetByURL.readParse(
                    "http://122.51.247.44:8080/atc/atclist?uuid=" + uid
                            + "&token=" + token
                )
            )

        if (jsonObject.getInt("code") == 0) {
            val jsonArray = jsonObject.getJSONArray("list")
            var i: Int = 0
            while (i < jsonArray.length()) {
                val j = jsonArray.getJSONObject(i)
                val article: Article = Article(
                    j.getString("name"), j.getInt("time").toString(),
                    j.getString("content"), urls[i]
                )
                list.add(article)
                i += 1
            }

        }

        recycleViewAdapter = RecycleViewAdapter(list, this, Fragment(), 1)
        recyclerView.adapter = recycleViewAdapter

    }

    private fun initLoopView() {
        viewPager = findViewById(R.id.loopviewpager) as ViewPager
        ll_dots_container = findViewById(R.id.ll_dots_loop) as LinearLayout
        loop_dec = findViewById(R.id.loop_dec) as TextView

        // 图片资源id数组
        mImg = intArrayOf(
            R.drawable.test3,
            R.drawable.test3,
            R.drawable.test3,
            R.drawable.test3,
            R.drawable.test3
        )

        // 文本描述
        mDec = arrayOf("Test1", "Test2", "Test3", "Test4", "Test5")

        mImg_id = intArrayOf(
            R.id.pager_img1,
            R.id.pager_img2,
            R.id.pager_img3,
            R.id.pager_img4,
            R.id.pager_img5
        )

        // 初始化要展示的5个ImageView
        mImgList = ArrayList()
        var imageView: ImageView
        var dotView: View
        var layoutParams: LinearLayout.LayoutParams
        for (i in mImg!!.indices) {
            //初始化要显示的图片对象
            imageView = ImageView(this)
            imageView.setBackgroundResource(mImg!![i])
            imageView.id = mImg_id!![i]
            // imageView.setOnClickListener(pagerOnClickListener(applicationContext))
            mImgList!!.add(imageView)
            //加引导点
            dotView = View(this)
            dotView.setBackgroundResource(R.drawable.dot)
            layoutParams = LinearLayout.LayoutParams(10, 10)
            if (i != 0) {
                layoutParams.leftMargin = 10
            }
            //设置默认所有都不可用
            dotView.isEnabled = false
            ll_dots_container!!.addView(dotView, layoutParams)
        }

        ll_dots_container!!.getChildAt(0).isEnabled = true
        loop_dec!!.text = mDec!![0]
        previousSelectedPosition = 0
        //设置适配器
        viewPager!!.setAdapter(LoopviewAdapter(mImgList!!))
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        val m = Integer.MAX_VALUE / 2 % mImgList!!.size
        val currentPosition = Integer.MAX_VALUE / 2 - m
        viewPager!!.setCurrentItem(currentPosition)

        viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override
            fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override
            fun onPageSelected(i: Int) {
                val newPosition = i % mImgList!!.size
                loop_dec!!.text = mDec!![newPosition]
                ll_dots_container!!.getChildAt(previousSelectedPosition).isEnabled = false
                ll_dots_container!!.getChildAt(newPosition).isEnabled = true
                previousSelectedPosition = newPosition
            }

            override
            fun onPageScrollStateChanged(i: Int) {

            }
        })

        // 开启轮询
        object : Thread() {
            override fun run() {
                isRunning = true
                while (isRunning) {
                    try {
                        Thread.sleep(5000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    //下一条
                    runOnUiThread { viewPager!!.setCurrentItem(viewPager!!.getCurrentItem() + 1) }
                }
            }
        }.start()

    }
}

