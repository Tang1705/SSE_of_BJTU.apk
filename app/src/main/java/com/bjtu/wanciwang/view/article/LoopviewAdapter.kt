package com.bjtu.wanciwang.view.article

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter

import java.util.ArrayList

internal class LoopviewAdapter(private val imageViewList: ArrayList<ImageView>) : PagerAdapter() {

    //返回一个无限大的值，可以 无限循环!!!!!

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }


    // 1. 返回要显示的条目内容, 创建条目
    @NonNull
    override
    fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        // container: 容器: ViewPager
        // position: 当前要显示条目的位置 0 -> 4
        //newPosition = position % 5
        val newPosition = position % imageViewList.size
        val img = imageViewList[newPosition]
        // a. 把View对象添加到container中
        container.addView(img)
        // b. 把View对象返回给框架, 适配器
        return img
    }
    override
    fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    override
    fun isViewFromObject(@NonNull view: View, @NonNull o: Any): Boolean {
        return view === o
    }
}